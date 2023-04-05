package ua.kislov.shop_front.models;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShopClient {


    private long clientId;

    @NotEmpty(message = "Field can't be empty")
    @Length(min = 2, max = 20, message = "Name should be normal size")
    private String username;

    @NotEmpty(message = "Field can't be empty")
    @Length(min = 5, message = "Password should be normal size (>5)")
    private String password;

    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopClient that = (ShopClient) o;

        if (clientId != that.clientId) return false;
        if (!username.equals(that.username)) return false;
        return password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = (int) (clientId ^ (clientId >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
