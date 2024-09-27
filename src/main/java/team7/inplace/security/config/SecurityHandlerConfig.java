package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.handler.CustomSuccessHandler;
import team7.inplace.security.util.JwtUtil;

@Configuration
public class SecurityHandlerConfig {

    @Bean
    public CustomSuccessHandler customSuccessHandler(JwtUtil jwtUtil) {
        return new CustomSuccessHandler(jwtUtil);
    }
}
