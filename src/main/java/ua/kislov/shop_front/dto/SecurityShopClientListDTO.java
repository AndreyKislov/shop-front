package ua.kislov.shop_front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kislov.shop_front.models.SecurityShopClient;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityShopClientListDTO {
    private List<SecurityShopClient> list;
    private int pageTotal;
}
