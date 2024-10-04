package team7.inplace.security.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.security.config.JwtProperties;

class JwtUtilTest {

    String testSecretKey = "testKeyTestKeyTestKeyTestKeyTestKeyTestKey";
    JwtProperties jwtProperties;
    JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties(testSecretKey, 60000L,
            180000L);
        jwtUtil = new JwtUtil(jwtProperties);
    }

    @Test
    void createTokenTest() {
        String username = "testMan";
        Long userId = 3L;
        String accessToken = jwtUtil.createAccessToken(username, userId);
        assertThat(accessToken).isNotNull();
    }

    @Test
    void createRefreshTokenTest() {
        String username = "testMan";
        Long userId = 3L;
        String refreshToken = jwtUtil.createRefreshToken(username, userId);
        assertThat(refreshToken).isNotNull();
    }

    @Test
    void getInfoTests() {
        String username = "testMan";
        Long userId = 3L;
        String accessToken = jwtUtil.createAccessToken(username, userId);
        String refreshToken = jwtUtil.createRefreshToken(username, userId);

        assertAll(
            () -> assertThat(jwtUtil.getUsername(accessToken)).isEqualTo(username),
            () -> assertThat(jwtUtil.getId(accessToken)).isEqualTo(userId),
            () -> assertThat(jwtUtil.getTokenType(accessToken)).isEqualTo("accessToken"),
            () -> assertThat(jwtUtil.getUsername(refreshToken)).isEqualTo(username),
            () -> assertThat(jwtUtil.getId(refreshToken)).isEqualTo(userId),
            () -> assertThat(jwtUtil.getTokenType(refreshToken)).isEqualTo("refreshToken")
        );
    }

    @Test
    void 토큰이_만료된_경우() {
        Date expiredDate = new Date(System.currentTimeMillis() - 3600 * 1000);
        SecretKey secretKey = new SecretKeySpec(
            jwtProperties.secret().getBytes(StandardCharsets.UTF_8),
            SIG.HS256.key().build().getAlgorithm());
        String expiredToken = Jwts.builder()
            .expiration(expiredDate)
            .signWith(secretKey)
            .compact();

        assertThatExceptionOfType(InplaceException.class).isThrownBy(
            () -> jwtUtil.validateExpired(expiredToken)
        );
    }
}
