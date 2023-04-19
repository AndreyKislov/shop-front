package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.repositiries.ClientFeign;

@Service
@Lazy
public class ClientService {
    private final ClientFeign clientFeign;

    @Autowired
    public ClientService(ClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

    public Boolean existsById(long id){
        return clientFeign.existsById(id).getBody();
    }

    public Boolean existsByEmail(String email){
        return clientFeign.existsByEmail(email).getBody();
    }

    public void sendAdditionalInformation(ShopClient shopClient){
        clientFeign.createShopCart(shopClient);
    }
}
