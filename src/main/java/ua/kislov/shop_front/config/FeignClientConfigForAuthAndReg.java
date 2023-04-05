package ua.kislov.shop_front.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kislov.shop_front.security.jwt.JwtUtil;

@Configuration
public class FeignClientConfigForAuthAndReg {

    private final JwtUtil jwtUtil;

    @Autowired
    public FeignClientConfigForAuthAndReg(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        String token = jwtUtil.generateToken();
        return template ->
            template.header("Authorization", "Bearer " + token);
    }
}
