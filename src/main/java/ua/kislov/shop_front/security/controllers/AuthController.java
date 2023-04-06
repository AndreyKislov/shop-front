package ua.kislov.shop_front.security.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.security.services.RegistrationService;
import ua.kislov.shop_front.security.validators.RegistrationValidator;
import ua.kislov.shop_front.services.ShopService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService service;
    private final RegistrationValidator validator;

    @Autowired
    public AuthController(RegistrationService service, RegistrationValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @GetMapping("/login")
    public String getAuthForm(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String getRegForm(@ModelAttribute("securityPerson") SecurityShopClient securityShopClient){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("securityPerson")@Valid SecurityShopClient securityShopClient,
                               BindingResult bindingResult){
        validator.validate(securityShopClient, bindingResult);
        if(bindingResult.hasErrors())
            return "auth/registration";
        service.save(securityShopClient);
        return "redirect:/shop/additionalInformation";
    }
}
