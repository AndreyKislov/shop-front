package ua.kislov.shop_front.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.security.repositories.SecurityFeignClient;

import java.beans.Encoder;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RegistrationService {

    private final SecurityFeignClient securityFeignClient;
    private final PasswordEncoder encoder;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    public RegistrationService(SecurityFeignClient securityFeignClient, PasswordEncoder encoder) {
        this.securityFeignClient = securityFeignClient;
        this.encoder = encoder;
    }

    public Boolean existsByUsername(String username) {
        ResponseEntity<String> responseEntity = securityFeignClient.existsByUsername(username);
        return Boolean.parseBoolean(responseEntity.getBody());
    }


    public void save(SecurityShopClient securityShopClient) {
        securityShopClient.setPassword(encoder.encode(securityShopClient.getPassword()));
        System.out.println(securityShopClient.getPassword());
        securityShopClient.setRole("ROLE_USER");
        securityFeignClient.save(securityShopClient);
    }
}
