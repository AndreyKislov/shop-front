package ua.kislov.shop_front.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret-string}")
    private String secretString;


    public String generateToken(Authentication authentication) {
        if (authentication != null) {
            Date issuedAt = new Date();
            Date expiry = Date.from(ZonedDateTime.now().plusSeconds(60).toInstant());
            return JWT
                    .create()
                    .withSubject("User details")
                    .withClaim("user", authentication.getName())
                    .withClaim("role", getAuthoritiesFromGrantedAuthority(authentication))
                    .withClaim("password", "null")
                    .withIssuer("Kislov")
                    .withIssuedAt(issuedAt)
                    .withExpiresAt(expiry)
                    .sign(Algorithm.HMAC256(secretString));
        }
        return null;
    }

    public String generateToken() {
        Date issuedAt = new Date();
        Date expiry = Date.from(ZonedDateTime.now().plusSeconds(60).toInstant());

        return JWT
                .create()
                .withSubject("User details")
                .withIssuer("Kislov")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(secretString));
    }

    private List<String> getAuthoritiesFromGrantedAuthority(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
