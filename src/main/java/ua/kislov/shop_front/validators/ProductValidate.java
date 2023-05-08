package ua.kislov.shop_front.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.services.AdminService;

@Component
public class ProductValidate implements Validator {

    private final AdminService adminService;

    @Autowired
    public ProductValidate(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (adminService.productIsExists(product.getName())) {
            errors.rejectValue("name", "", "Product with this name is already exists");
        }
    }
}
