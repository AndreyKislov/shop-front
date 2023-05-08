package ua.kislov.shop_front.controllers;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ua.kislov.shop_front.dto.ProductListDTO;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.services.interfaces.AdminServiceInterface;
import ua.kislov.shop_front.validators.ProductValidate;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceInterface adminService;
    private final ModelMapper mapper;
    private final ProductValidate productValidate;

    @Autowired
    public AdminController(AdminServiceInterface adminService, ModelMapper mapper, ProductValidate productValidate) {
        this.adminService = adminService;
        this.mapper = mapper;
        this.productValidate = productValidate;
    }

    @GetMapping("/users")
    public String users(@RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                        @RequestParam(value = "size", required = false,
                                defaultValue = "3") int size,
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

        File file = new File("G:\\download\\" + image.getOriginalFilename());
        if (!image.isEmpty()) {
            String filename = image.getOriginalFilename();
            if (filename != null && (!filename.endsWith(".jpg") || !filename.endsWith(".png")) && file.exists()) {
                bindingResult.rejectValue("url", "", "Invalid file format " +
                        "or image with that name is exists");
                return "admin/newProduct";
            }
        } else {
            bindingResult.rejectValue("url", "", "Image is empty");
            return "admin/newProduct";
        }
        productValidate.validate(product, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/newProduct";
        image.transferTo(file);
        product.setUrl(file.getPath());
        adminService.saveNewProduct(product);
        return "redirect:/admin/create_product";
    }


    private SecurityShopClient fromDTO(SecurityShopClientDTO dto) {
        return mapper.map(dto, SecurityShopClient.class);
    }
}
