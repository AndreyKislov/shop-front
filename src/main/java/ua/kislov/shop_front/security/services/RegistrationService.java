package ua.kislov.shop_front.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ua.kislov.shop_front.dto.SecurityShopClientDTO;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.security.repositories.SecurityFeignClient;

import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RegistrationService {

    private final SecurityFeignClient securityFeignClient;

    @Autowired
    public RegistrationService(SecurityFeignClient securityFeignClient) {
        this.securityFeignClient = securityFeignClient;
    }

    public Boolean existsByUsername(String username) {
        ResponseEntity<String> responseEntity = securityFeignClient.existsByUsername(username);
        if(responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))){
            return true;
        } else if (responseEntity.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
            return false;
        }
        throw new RuntimeException(responseEntity.getBody());
    }


    public void save(SecurityShopClient securityShopClient) {
        securityShopClient.setPassword(securityShopClient.getPassword());
        securityShopClient.setRole("ROLE_USER");
        ResponseEntity<SecurityShopClientDTO> responseEntity = securityFeignClient.save(securityShopClient);
        SecurityShopClientDTO securityShopClientDTO = responseEntity.getBody();

        List<GrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(securityShopClientDTO.getRole()));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                securityShopClientDTO.getUsername(),
                securityShopClientDTO.getPassword(),
                authorityList
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
