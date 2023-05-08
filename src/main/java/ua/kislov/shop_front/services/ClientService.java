package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.repositiries.ShopFeign;
import ua.kislov.shop_front.security.details.ClientDetails;

import java.util.List;

@Service
@Lazy
public class ClientService {
    private final ShopFeign shopFeign;

    @Autowired
    public ClientService(ShopFeign shopFeign) {
        this.shopFeign = shopFeign;
    }

    public Boolean existsById(long id){
        return shopFeign.existsById(id).getBody();
    }

    public Boolean existsByEmail(String email){
        return shopFeign.existsByEmail(email).getBody();
    }

    public void sendAdditionalInformation(ShopClient shopClient){
        shopFeign.createShopCart(shopClient);
    }

    public List<ProductQuantityDTO> findCartById(long id){
        return shopFeign.getShopCart(id).getBody();
    }

    public long getCurrentClientId(){
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDetails.securityShopClient().getClientId();
    }
}
