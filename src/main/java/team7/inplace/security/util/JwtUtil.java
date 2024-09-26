package team7.inplace.security.util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import team7.inplace.security.config.JwtProperties;

public class JwtUtil {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    private final Long accessTokenExpiredTime;
    private final Long refreshTokenExpiredTime;

    public JwtUtil(JwtProperties jwtProperties) {
        this.secretKey = new SecretKeySpec(jwtProperties.secret().getBytes(StandardCharsets.UTF_8),
            SIG.HS256.key().build().getAlgorithm());
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
        this.accessTokenExpiredTime = jwtProperties.accessTokenExpiredTime();
        this.refreshTokenExpiredTime = jwtProperties.refreshTokenExpiredTime();
    }

    public String createAccessToken(String username) {
        return createToken(username, accessTokenExpiredTime);
    }

    public String createRefreshToken(String username) {
        return createToken(username, refreshTokenExpiredTime);
    }

    private String createToken(String username, Long expiredTime) {
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
