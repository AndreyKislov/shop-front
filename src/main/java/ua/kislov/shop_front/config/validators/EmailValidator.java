package ua.kislov.shop_front.config.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.services.ClientService;

@Component
public class EmailValidator implements Validator {

    private final ClientService service;

    @Autowired
    public EmailValidator(ClientService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ShopClient.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ShopClient shopClient = (ShopClient) target;
        if (service.existsByEmail(shopClient.getEmail())) {
            errors.rejectValue("email", "", "Email is already exists. " +
                    "Try to authenticate with this email");
        }
    }
}
