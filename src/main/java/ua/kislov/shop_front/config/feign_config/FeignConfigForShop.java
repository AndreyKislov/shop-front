package ua.kislov.shop_front.config.feign_config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.kislov.shop_front.security.jwt.JwtUtil;

@Configuration
@Lazy
public class FeignConfigForShop {
    private final JwtUtil jwtUtil;

    @Autowired
    public FeignConfigForShop(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public RequestInterceptor requestInterceptorForShop() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtUtil.generateToken(authentication);
        return template ->
                template.header("Authorization", "Bearer " + token);
    }
}

