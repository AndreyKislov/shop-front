package ua.kislov.shop_front.security.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.services.RegistrationService;

@Component
public class RegistrationValidator implements Validator {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ShopClient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShopClient shopClient = (ShopClient) target;
        if (registrationService.existsByUsername(shopClient.getPassword())) {
            errors.rejectValue("username", "", "Username is already exists");
        }

    }
}
