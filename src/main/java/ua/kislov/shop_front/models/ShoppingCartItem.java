package ua.kislov.shop_front.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingCartItem {
    private Product product;
    private int quantity;
}
