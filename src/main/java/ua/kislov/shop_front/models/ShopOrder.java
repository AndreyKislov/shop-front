package ua.kislov.shop_front.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ShopOrder {
    private long orderId;
    private ShopClient shopClient;
    private List<OrderProduct> orderProductsList;

    public int getTotalCost(){
        if(!orderProductsList.isEmpty()){
            return orderProductsList.stream().mapToInt(p-> p.getProduct().getCost()).sum();
        }
        return 0;
    }
}
