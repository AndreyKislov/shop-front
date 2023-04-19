package ua.kislov.shop_front.security.config;

import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.kislov.shop_front.security.details.ClientDetails;
import ua.kislov.shop_front.security.services.SecurityClientDetailsService;

import java.util.logging.Logger;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final SecurityClientDetailsService securityClientDetailsService;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthProvider(SecurityClientDetailsService securityClientDetailsService, PasswordEncoder encoder) {
        this.securityClientDetailsService = securityClientDetailsService;
        this.encoder = encoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        //получаем по username со стороннего сервиса (аутентификация и регистрация)
        ClientDetails details;
        try {
            details = (ClientDetails) securityClientDetailsService.loadUserByUsername(username);
        } catch (RetryableException e) {
            throw new AuthenticationServiceException("Service is not available");
        } catch (FeignException.NotFound e) {
            throw new AuthenticationServiceException("User is not found");
        }catch (FeignException e){
            System.out.println(e.fillInStackTrace());
            throw new AuthenticationServiceException("Some problem");
        }
        String password = authentication.getCredentials().toString();
        if (!encoder.matches(password, details.getPassword()))
            throw new BadCredentialsException("Password is not correct!");
        logger.info("User is authenticated " +  details.securityShopClient());
        return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
