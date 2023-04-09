package ua.kislov.shop_front.security.repositories;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kislov.shop_front.config.feign_config.FeignConfig;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.models.SecurityShopClient;

@FeignClient(
        name = "auth-reg-service",
        url = "${auth-reg-service.url}",
        configuration = FeignConfig.class)
public interface SecurityFeignClient {
    @GetMapping("/auth/exists-client")
    ResponseEntity<String> existsByUsername(@RequestParam("username") String username);

    @GetMapping("/auth/security-client")
    ResponseEntity<SecurityShopClientDTO> findByUsername(@RequestParam("username") String username);

    @PostMapping("/auth/security-client")
    ResponseEntity<SecurityShopClientDTO> save(@RequestBody SecurityShopClient securityShopClient);
}
