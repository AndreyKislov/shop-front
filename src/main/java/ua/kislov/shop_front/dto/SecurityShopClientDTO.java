package ua.kislov.shop_front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityShopClientDTO {
    private long id;
    private String username;
    private String password;
    private String role;

    public SecurityShopClientDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
