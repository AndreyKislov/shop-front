package ua.kislov.shop_front.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopClient {
    private long id;
    @NotEmpty(message = "Field can't be empty")
    @Length(min = 2, max = 20, message = "Name should be normal size")
    private String clientName;
    @NotEmpty(message = "Field can't be empty")
    @Length(min = 2, max = 20, message = "Surname should be normal size")
    private String surname;
    @NotEmpty
    @Email
    private String email;
    @Pattern(regexp = "^\\+380\\d{9}$")
    private String number;
    private List<Product> shoppingCart;
}
