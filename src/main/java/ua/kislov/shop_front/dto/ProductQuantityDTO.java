package ua.kislov.shop_front.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.kislov.shop_front.models.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDTO {
    private Product product;
    private int quantity;
}
