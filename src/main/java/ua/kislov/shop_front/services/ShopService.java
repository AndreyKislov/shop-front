package ua.kislov.shop_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.repositiries.ShopFeignClient;

@Service
@Lazy
public class ShopService {
    private final ShopFeignClient shopFeignClient;

    @Autowired
    public ShopService(ShopFeignClient shopFeignClient) {
        this.shopFeignClient = shopFeignClient;
    }

    public void sendAdditionalInformation(ShopClient shopClient){
        shopFeignClient.createShopCart(shopClient);
    }
}
