package ua.kislov.shop_front.services.interfaces;

import ua.kislov.shop_front.dto.OrderDTO;
import ua.kislov.shop_front.dto.ProductQuantityDTO;

import java.util.List;

public interface OrderInterface {
    void makeOrder(long id);
}
