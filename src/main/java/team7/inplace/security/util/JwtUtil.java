package team7.inplace.security.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
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

    public String createAccessToken(String username, Long userId, String roles) {
        return createToken(username, userId, roles, "accessToken", accessTokenExpiredTime);
    }

    public String createRefreshToken(String username, Long userId, String roles) {
        return createToken(username, userId, roles, "refreshToken", refreshTokenExpiredTime);
    }

    private String createToken(String username, Long userId, String roles, String tokenType,
        Long expiredTime) {
        return Jwts.builder()
            .claim("username", username)
            .claim("id", userId)
            .claim("roles", roles)
            .claim("tokenType", tokenType)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredTime))
            .signWith(secretKey)
            .compact();
    }

    public String getUsername(String token) throws InplaceException {
        try {
            return jwtParser.parseSignedClaims(token).getPayload().get("username", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }
    }

    public boolean isRefreshToken(String token) throws InplaceException {
        try {
            return jwtParser.parseSignedClaims(token).getPayload().get("tokenType", String.class)
                .equals("refreshToken");
        } catch (JwtException | IllegalArgumentException e) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }
    }

    public Long getId(String token) throws InplaceException {
        try {
            return jwtParser.parseSignedClaims(token).getPayload().get("id", Long.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }
    }

    public String getRoles(String token) throws InplaceException {
        try {
            return jwtParser.parseSignedClaims(token).getPayload().get("roles", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }
    }

    public void validateExpired(String token) throws InplaceException {
        try {
            jwtParser.parseSignedClaims(token).getPayload().getExpiration();
        } catch (ExpiredJwtException e) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }
    }
}
