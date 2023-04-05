package ua.kislov.shop_front.security.repositories;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kislov.shop_front.config.FeignClientConfigForAuthAndReg;
import ua.kislov.shop_front.dto.ShopClientDTO;
import ua.kislov.shop_front.models.ShopClient;

import java.net.http.HttpResponse;

@FeignClient(
        name = "auth-reg-service",
        url = "${auth-reg-service.url}",
        configuration = FeignClientConfigForAuthAndReg.class)
public interface SecurityFeignClient {
    @GetMapping("/exists-client")
    Boolean existsByUsername(String username);
    @GetMapping("/client")
    ShopClientDTO findByUsername(String username);

    @PostMapping("/client")
    HttpResponse<Void> save(ShopClient shopClient);
}
