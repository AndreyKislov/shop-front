package ua.kislov.shop_front.models;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityShopClient {
    private long clientId;
    @NotEmpty(message = "Field can't be empty")
    @Length(min = 2, max = 20, message = "Name should be normal size")
    private String username;

    @NotEmpty(message = "Field can't be empty")
    @Length(min = 5, message = "Password should be normal size (>5)")
    private String password;

    private String role;

    @Override
    public String toString() {
        return "SecurityShopClient{" +
                "clientId=" + clientId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
