package ua.kislov.shop_front.services.interfaces;

import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.models.ShopClient;

import java.util.List;

public interface ClientInterface {
    Boolean existsById(long id);

    Boolean existsByEmail(String email);

    void sendAdditionalInformation(ShopClient shopClient);

    List<ProductQuantityDTO> findCartById(long id);

    long getCurrentClientId();
}
