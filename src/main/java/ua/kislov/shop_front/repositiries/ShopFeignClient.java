package ua.kislov.shop_front.repositiries;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kislov.shop_front.config.feign_config.FeignConfigForShop;
import ua.kislov.shop_front.models.ShopClient;

@Lazy
@FeignClient(
        name = "shop-back",
        url = "${shop_back.url}",
        configuration = FeignConfigForShop.class)
public interface ShopFeignClient {

    @PostMapping("/additionalInformation")
    ResponseEntity<?> createShopCart(ShopClient shopClient);
}
