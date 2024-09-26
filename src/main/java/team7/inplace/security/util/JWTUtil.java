package team7.inplace.security.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    private static final Long expiredTime = 300L;

    public JWTUtil(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
            SIG.HS256.key().build().getAlgorithm());
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
    }

    public String createToken(String username) {
        return Jwts.builder()
            .claim("username", username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredTime))
            .signWith(secretKey)
            .compact();
    }

    public String getUsername(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public Boolean isExpired(String token) {
        return jwtParser.parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
