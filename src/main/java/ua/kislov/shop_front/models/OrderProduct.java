package ua.kislov.shop_front.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    private long orderProductId;
    private int quantity;
    private Product product;
    private ShopOrder shopOrder;
}
