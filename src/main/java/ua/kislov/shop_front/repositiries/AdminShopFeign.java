package ua.kislov.shop_front.repositiries;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.models.Product;

@FeignClient(
        name = "shop-back-admin",
        url = "${shop_back.url}",
        configuration = FeignConfig.class)
public interface AdminShopFeign {
    @GetMapping("/admin/{id}")
    ResponseEntity<Product> product(@PathVariable("id") long id);
}
