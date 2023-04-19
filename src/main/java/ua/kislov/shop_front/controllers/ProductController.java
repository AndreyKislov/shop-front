package ua.kislov.shop_front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.services.ProductService;

@Controller
@RequestMapping("/catalog")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String catalog(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                 @RequestParam(value = "size", required = false,
                                                         defaultValue = "10") int size,
                                                 @RequestParam(value = "sortBy", required = false,
                                                         defaultValue = "name") String sortBy, Model model) {
       model.addAttribute("products", productService.catalog(page, size, sortBy));
       return "shop/catalog";
    }

    @GetMapping("/{id}")
    public String product(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productService.product(id));
        return "shop/product";
    }
}
