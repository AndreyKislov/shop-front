package ua.kislov.shop_front.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ua.kislov.shop_front.dto.OrdersDTO;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.models.ShopOrder;
import ua.kislov.shop_front.services.interfaces.AdminInterface;
import ua.kislov.shop_front.services.interfaces.ProductInterface;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminInterface adminService;

    private final ProductInterface productService;
    private final ModelMapper mapper;

    @Autowired
    public AdminController(AdminInterface adminService, ProductInterface productService, ModelMapper mapper) {
        this.adminService = adminService;
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping("/main")
    public String mainPage(){
        return "admin/main";
    }

    @GetMapping("/users")
    public String users(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(value = "size", required = false,
                                defaultValue = "10") int size,
                        @RequestParam(value = "sortBy", required = false,
                                defaultValue = "username") String sortBy,
                        Model model) {
        SecurityShopClientListDTO listDTO = adminService.findSecurityShopClientByAll(pageNumber, size, sortBy);
        int totalPages = listDTO.getPageTotal();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("users", listDTO.getList());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String user(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", adminService.findSecurityShopClientById(id));
        try {
            model.addAttribute("client", adminService.findShopClientById(id));
        } catch (FeignException e) {
            System.out.println(e.getMessage());
        }
        return "admin/user";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") long id, HttpServletRequest request) {
        adminService.deleteUserAndClient(id);
        String pageParamNumber = request.getParameter("page");
        String pageParamSize = request.getParameter("size");
        String pageParamSortBy = request.getParameter("sortBy");
        int currentNumber = 0;
        int currentSize = 3;
        String sortBy = "username";
        if (pageParamNumber != null) {
            currentNumber = Integer.parseInt(pageParamNumber);
        }
        if (pageParamSize != null) {
            currentSize = Integer.parseInt(pageParamSize);
        }
        if (pageParamSortBy != null) {
            sortBy = pageParamSortBy;
        }


        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/admin/users")
                .queryParam("page", currentNumber).queryParam("size", currentSize)
                .queryParam("sortBy", sortBy);

        return "redirect:" + builder.build().toUriString();
    }

    @GetMapping("/create_product")
    public String getFormForCreate(@ModelAttribute("product") Product product) {
        return "admin/newProduct";
    }

    @PostMapping("/create_product")
    public String createProduct(@ModelAttribute Product product, BindingResult bindingResult,
                                @RequestParam("image") MultipartFile image) throws IOException {
        if (bindingResult.hasErrors())
            return "admin/newProduct";
        if (!image.isEmpty()) {
            byte[] byteImage = image.getBytes();
            product.setByteImage(byteImage);
        } else {
            bindingResult.rejectValue("byteImage", "", "Image is empty");
            return "admin/newProduct";
        }
        adminService.saveNewProduct(product);
        return "redirect:/admin/create_product";
    }

    @GetMapping("/{id}/edit_product")
    public String getEditProduct(@PathVariable("id") long id, Model model){
         model.addAttribute("product", productService.getProduct(id));
         return "admin/editProduct";
    }
    @PatchMapping("/edit_product/{id}")
    public String editProduct(@PathVariable("id")long id,
                              @ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult, @RequestParam("image") MultipartFile image) throws IOException {
        if(image.isEmpty())
            bindingResult.rejectValue("byteImage", "", "Image is empty");
        if(bindingResult.hasErrors())
            return "admin/editProduct";
        byte[] byteImage = image.getBytes();
        product.setByteImage(byteImage);
        product.setId(id);
        adminService.saveNewProduct(product);
        return "redirect:/catalog";
    }
    @DeleteMapping("/{id}/delete_product")
    public String deleteProduct(@PathVariable("id")long id){
        adminService.deleteProduct(id);
        return "redirect:/catalog";
    }

    @GetMapping("/orders")
    public String orders(Model model) throws JsonProcessingException {
        OrdersDTO ordersDTO = adminService.getOrders();
        model.addAttribute("orders", ordersDTO.getListOrder());
        return "admin/orders";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") long id, Model model) {
        ShopOrder shopOrder = adminService.getOrder(id);
        model.addAttribute("order", shopOrder);
        return "admin/order";
    }

    @PatchMapping("/order/{id}")
    public String order(@PathVariable("id") long id) {
        adminService.completedOrder(id);
        return "redirect:/admin/orders";
    }


    private SecurityShopClient fromDTO(SecurityShopClientDTO dto) {
        return mapper.map(dto, SecurityShopClient.class);
    }
}
