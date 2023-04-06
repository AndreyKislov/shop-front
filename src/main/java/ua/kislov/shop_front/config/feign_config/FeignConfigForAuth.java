package ua.kislov.shop_front.config.feign_config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kislov.shop_front.security.jwt.JwtUtil;

@Configuration
public class FeignConfigForAuth {

    private final JwtUtil jwtUtil;

    @Autowired
    public FeignConfigForAuth(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RequestInterceptor requestInterceptorForAuth() {
        String token = jwtUtil.generateToken();
        return template ->
            template.header("Authorization", "Bearer " + token);
    }
}
