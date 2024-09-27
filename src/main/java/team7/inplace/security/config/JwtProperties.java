package team7.inplace.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(
    String secret,
    Long accessTokenExpiredTime,
    Long refreshTokenExpiredTime
) {

}
