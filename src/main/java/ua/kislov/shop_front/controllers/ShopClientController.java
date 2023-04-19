package ua.kislov.shop_front.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kislov.shop_front.config.validators.EmailValidator;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.services.ClientService;

@Controller
@RequestMapping("/shop")
public class ShopClientController {
    private final ClientService clientService;
    private final EmailValidator emailValidator;

    @Autowired
    public ShopClientController(ClientService clientService, EmailValidator emailValidator) {
        this.clientService = clientService;
        this.emailValidator = emailValidator;
    }

    @GetMapping("/additionalInformation")
    public String getFormAdditionalInformation(@ModelAttribute("shopClient") ShopClient shopClient) {
        return "auth/additionalInformation";
    }

    @PostMapping("/additionalInformation")
    public String createAdditionalInformation(@ModelAttribute("shopClient") @Valid ShopClient shopClient,
                                              BindingResult bindingResult) {
        System.out.println("from contr");
        if (bindingResult.hasErrors())
            return "auth/additionalInformation";
        emailValidator.validate(shopClient, bindingResult);
        if (bindingResult.hasErrors())
            return "auth/additionalInformation";
        shopClient.setId(getCurrentId());
        clientService.sendAdditionalInformation(shopClient);
        return "redirect:/catalog";
    }

    @GetMapping("/checkPage")
    public String check() {
        if (clientService.existsById(getCurrentId()))
            return "redirect:/catalog";
        return "redirect:/shop/additionalInformation";
    }

    private long getCurrentId(){
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDetails.securityShopClient().getClientId();
    }
}
