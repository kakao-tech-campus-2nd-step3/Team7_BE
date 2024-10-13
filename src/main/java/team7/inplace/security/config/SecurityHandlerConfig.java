package team7.inplace.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.handler.CustomFailureHandler;
import team7.inplace.security.handler.CustomSuccessHandler;
import team7.inplace.security.util.CookieUtil;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.token.application.RefreshTokenService;
import team7.inplace.user.application.UserService;

@Configuration
public class SecurityHandlerConfig {

    @Bean
    public CustomSuccessHandler customSuccessHandler(
        JwtUtil jwtUtil,
        UserService userService,
        RefreshTokenService refreshTokenService,
        CookieUtil cookieUtil
    ) {
        return new CustomSuccessHandler(jwtUtil, userService, refreshTokenService, cookieUtil);
    }

    @Bean
    public CustomFailureHandler customFailureHandler(ObjectMapper objectMapper) {
        return new CustomFailureHandler(objectMapper);
    }
}
