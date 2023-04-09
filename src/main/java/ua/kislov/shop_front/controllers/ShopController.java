package ua.kislov.shop_front.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.services.ShopService;

@Controller
@RequestMapping("/shop")
@Lazy
public class ShopController {
    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/additionalInformation")
    public String getFormAdditionalInformation(@ModelAttribute("shopClient") ShopClient shopClient) {
        return "auth/additionalInformation";
    }

    @PostMapping("/additionalInformation")
    public String createAdditionalInformation(@ModelAttribute("shopClient") @Valid ShopClient shopClient,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "auth/additionalInformation";
        shopService.sendAdditionalInformation(shopClient);
        return "redirect:/catalog";
    }

    @GetMapping("/checkPage")
    public String catalog() {
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = clientDetails.securityShopClient().getClientId();
        if(shopService.existsById(id))
            return "redirect:/shop/additionalInformation";
        return "redirect:/shop/catalog";
    }
}
