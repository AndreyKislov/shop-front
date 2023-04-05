package ua.kislov.shop_front.security.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.services.RegistrationService;
import ua.kislov.shop_front.security.validators.RegistrationValidator;

@Controller
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
    public String getRegForm(@ModelAttribute("securityPerson") ShopClient shopClient){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("securityPerson")@Valid ShopClient shopClient,
                               BindingResult bindingResult){
        validator.validate(shopClient, bindingResult);
        if(bindingResult.hasErrors())
            return "auth/registration";
        service.save(shopClient);
        return "redirect:/auth/login";
    }
}
