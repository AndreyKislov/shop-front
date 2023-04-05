package ua.kislov.shop_front.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.models.ShopClient;
import ua.kislov.shop_front.security.repositories.SecurityFeignClient;

@Service
public class RegistrationService {

    private final SecurityFeignClient securityFeignClient;

    @Autowired
    public RegistrationService(SecurityFeignClient securityFeignClient) {
        this.securityFeignClient = securityFeignClient;
    }

    public Boolean existsByUsername(String username) {
        return securityFeignClient.existsByUsername(username);
    }


    public void save(ShopClient shopClient) {
        shopClient.setPassword(shopClient.getPassword());
        shopClient.setRole("ROLE_USER");
        securityFeignClient.save(shopClient);
    }
}
