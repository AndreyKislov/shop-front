package ua.kislov.shop_front.config.feign_config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.kislov.shop_front.security.jwt.JwtUtil;

@Configuration
public class FeignConfig {
    private final JwtUtil jwtUtil;

    @Autowired
    public FeignConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String token;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() != "anonymousUser") {
                token = jwtUtil.generateToken(auth);
            } else {
                token = jwtUtil.generateToken();
            }
            template.header("Authorization", "Bearer " + token);
        };
    }
}
