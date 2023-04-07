package ua.kislov.shop_front.security.config;

import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.security.services.SecurityClientDetailsService;

import java.util.Collections;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final SecurityClientDetailsService securityClientDetailsService;

    @Autowired
    public AuthProvider(SecurityClientDetailsService securityClientDetailsService) {
        this.securityClientDetailsService = securityClientDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        //получаем по username со стороннего сервиса (аутентификация и регистрация)
        ClientDetails details;
        try{
             details = (ClientDetails) securityClientDetailsService.loadUserByUsername(username);
            System.out.println(details);
        }catch (RetryableException e){
            throw new AuthenticationServiceException("Exception service is not available");
        }

        String password = authentication.getCredentials().toString();

        if(!password.equals(details.getPassword()))
            throw new BadCredentialsException("Password is not correct!");
        return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
