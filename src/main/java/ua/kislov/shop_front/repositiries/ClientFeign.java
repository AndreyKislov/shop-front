package ua.kislov.shop_front.repositiries;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.models.ShopClient;

@FeignClient(
        name = "shop-back-reg-new-cart",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface ClientFeign {

    @GetMapping("/client/exists-id")
    ResponseEntity<Boolean> existsById(@RequestParam("id") long id);

    @GetMapping("/client/exists-email")
    ResponseEntity<Boolean> existsByEmail(@RequestParam("email") String email);

    @PostMapping("/client/additionalInformation")
    ResponseEntity<?> createShopCart(@RequestBody ShopClient shopClient);
}
