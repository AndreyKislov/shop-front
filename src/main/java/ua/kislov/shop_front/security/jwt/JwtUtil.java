package ua.kislov.shop_front.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ua.kislov.shop_front.models.SecurityShopClient;
import ua.kislov.shop_front.security.details.ClientDetails;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;


@Component
public class JwtUtil {
    @Value("${jwt.secret-string}")
    private String secretString;

    public String generateToken() {
        return JWT
                .create()
                .withSubject("User details")
                .withIssuer("Kislov")
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusSeconds(60).toInstant()))
                .sign(Algorithm.HMAC256(secretString));
    }

    public String generateToken(Authentication authentication) {
        ClientDetails clientDetails = (ClientDetails) authentication.getPrincipal();
        SecurityShopClient securityShopClient = clientDetails.securityShopClient();
        return JWT
                .create()
                .withSubject("User details")
                .withClaim("role", securityShopClient.getRole())
                .withClaim("user", securityShopClient.getUsername())
                .withIssuer("Kislov")
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(ZonedDateTime.now().plusSeconds(60).toInstant()))
                .sign(Algorithm.HMAC256(secretString));
    }
}
