package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.OrderDTO;
import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.repositiries.ShopFeign;
import ua.kislov.shop_front.services.interfaces.OrderInterface;

import java.util.List;

@Service
public class OrderService implements OrderInterface {
    private final ShopFeign shopFeign;

    @Autowired
    public OrderService(ShopFeign shopFeign) {
        this.shopFeign = shopFeign;
    }
    @Override
    public void makeOrder(long id) {
        shopFeign.makeOrder(id);
    }
}
