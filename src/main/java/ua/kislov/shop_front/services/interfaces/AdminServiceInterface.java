package ua.kislov.shop_front.services.interfaces;

import org.springframework.data.domain.Page;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.dto.SecurityShopClientListDTO;
import ua.kislov.shop_front.models.SecurityShopClient;

import java.util.List;

public interface AdminServiceInterface {
    SecurityShopClientListDTO findByAll(int page, int size, String sort);
    SecurityShopClient findById(long id);
}
