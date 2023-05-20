package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.ProductQuantityDTO;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.repositiries.ShopFeign;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.services.interfaces.ClientInterface;

import java.util.List;

@Service
@Lazy
public class ClientService implements ClientInterface {
    private final ShopFeign shopFeign;

    @Autowired
    public ClientService(ShopFeign shopFeign) {
        this.shopFeign = shopFeign;
    }

    @Override
    public Boolean existsById(long id){
        return shopFeign.existsById(id).getBody();
    }

    @Override
    public Boolean existsByEmail(String email){
        return shopFeign.existsByEmail(email).getBody();
    }

    @Override
    public void sendAdditionalInformation(ShopClient shopClient){
        shopFeign.createShopCart(shopClient);
    }

    @Override
    public List<ProductQuantityDTO> findCartById(long id){
        return shopFeign.getShopCart(id).getBody();
    }

    @Override
    public long getCurrentClientId(){
        ClientDetails clientDetails = (ClientDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientDetails.securityShopClient().getClientId();
    }
}
