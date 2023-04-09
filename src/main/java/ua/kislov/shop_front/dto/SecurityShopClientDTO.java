package ua.kislov.shop_front.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecurityShopClientDTO {
    private long clientId;
    private String username;
    private String password;
    private String role;


}
