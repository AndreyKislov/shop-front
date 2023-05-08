package ua.kislov.shop_front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kislov.shop_front.dto.CartItemsDTO;
import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.dto.ProductListDTO;
import ua.kislov.shop_front.dto.UpdateQuantityDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.services.ClientService;
import ua.kislov.shop_front.services.ProductService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/catalog")
//@SessionAttributes("catalogParam")
public class ProductController {
    private final ProductService productService;
    private final ClientService clientService;

    @Autowired
    public ProductController(ProductService productService, ClientService clientService) {
        this.productService = productService;
        this.clientService = clientService;
    }

    @GetMapping()
    public String catalog(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          @RequestParam(value = "size", required = false,
                                  defaultValue = "10") int size,
                          @RequestParam(value = "sortBy", required = false,
                                  defaultValue = "name") String sortBy, Model model) {
        ProductListDTO listDTO = productService.productFindAll(page, size, sortBy);
        List<Product> productList = listDTO.getProductList();
        int totalPages = listDTO.getTotalPage();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("catalog", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        return "shop/catalog";
    }

    @GetMapping("/{id}")
    public String product(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "shop/product";
    }

    @PostMapping("/{id}")
    public String addToCart(@PathVariable("id") long productId) {
        productService.addToCart(clientService.getCurrentClientId(), productId);
        return "redirect:/catalog";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        List<ProductQuantityDTO> cart = clientService.findCartById(clientService.getCurrentClientId());
        cart = cart.stream().sorted(Comparator.comparing(p -> p.getProduct().getName())).collect(Collectors.toList());
        if (!cart.isEmpty()) {
            model.addAttribute("clientId", clientService.getCurrentClientId());
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("cart", null);
        }
        return "shop/cart";
    }

    @PatchMapping("/cart/quantity")
    public String updateQuantity(@RequestParam("quantity") int quantity,
                                 @RequestParam("productId") long productId) {
        long clientId = clientService.getCurrentClientId();
        UpdateQuantityDTO updateQuantityDTO = new UpdateQuantityDTO(clientId, productId, quantity);
        productService.updateQuantity(updateQuantityDTO);
        return "redirect:/catalog/cart";
    }

    @DeleteMapping("/cart/delete")
    public String deleteProductFromCart(@RequestParam("productIdForDelete") long productId){
        CartItemsDTO dto = new CartItemsDTO(productId, clientService.getCurrentClientId());
        productService.deleteFromCart(dto);
        return "redirect:/catalog/cart";
    }
}
