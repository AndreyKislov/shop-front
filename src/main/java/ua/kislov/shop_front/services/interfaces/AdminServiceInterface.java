package ua.kislov.shop_front.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import ua.kislov.shop_front.dto.OrdersDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.Product;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.models.ShopOrder;

public interface AdminServiceInterface {
    SecurityShopClientListDTO findSecurityShopClientByAll(int page, int size, String sort);

    SecurityShopClient findSecurityShopClientById(long id);

    ShopClient findShopClientById(long id);

    void deleteUserAndClient(long id);

    Boolean productIsExists(String name);

    void saveNewProduct(Product product);

    OrdersDTO getOrders() throws JsonProcessingException;

    ShopOrder getOrder(long id);

    void completedOrder(long id);

}
