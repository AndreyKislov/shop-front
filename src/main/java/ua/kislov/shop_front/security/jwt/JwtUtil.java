package ua.kislov.shop_front.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret-string}")
    private String secretString;

    public String generateToken(String username){
        Date issuedAt = new Date();
        Date expiry = Date.from(ZonedDateTime.now().plusSeconds(60).toInstant());

        return JWT
                .create()
                .withSubject("User details")
                .withClaim("user", username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(secretString));
    }
    public String generateToken(){
        Date issuedAt = new Date();
        Date expiry = Date.from(ZonedDateTime.now().plusSeconds(60).toInstant());

        return JWT
                .create()
                .withSubject("User details")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(secretString));
    }
}
