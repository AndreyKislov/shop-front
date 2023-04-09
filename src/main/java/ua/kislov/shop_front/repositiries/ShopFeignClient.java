package ua.kislov.shop_front.repositiries;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.models.ShopClient;

@Lazy
@FeignClient(
        name = "shop-back",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface ShopFeignClient {

    @GetMapping("/shop/exists-client")
    ResponseEntity<String> existsById(@RequestParam("id") long id);

    @PostMapping("/additionalInformation")
    ResponseEntity<?> createShopCart(ShopClient shopClient);
}
