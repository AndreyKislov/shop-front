package ua.kislov.shop_front.security.repositories;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.kislov.shop_front.config.feign_config.FeignConfigForAuth;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.models.SecurityShopClient;

@FeignClient(
        name = "auth-reg-service",
        url = "${auth-reg-service.url}",
        configuration = FeignConfigForAuth.class)
@RequestMapping("/auth")
public interface SecurityFeignClient {
    @GetMapping("/exists-client")
    ResponseEntity<String> existsByUsername(String username);

    @GetMapping("/security-client")
    ResponseEntity<SecurityShopClientDTO> findByUsername(String username);

    @PostMapping("/security-client")
    ResponseEntity<SecurityShopClientDTO> save(SecurityShopClient securityShopClient);
}
