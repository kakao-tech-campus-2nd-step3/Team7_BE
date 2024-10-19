package team7.inplace.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.filter.AuthorizationFilter;
import team7.inplace.security.filter.ExceptionHandlingFilter;
import team7.inplace.security.util.JwtUtil;

@Configuration
public class SecurityFilterConfig {

    @Bean
    public AuthorizationFilter authorizationFilter(JwtUtil jwtUtil) {
        return new AuthorizationFilter(jwtUtil);
    }

    @Bean
    public ExceptionHandlingFilter exceptionHandlingFilter(ObjectMapper objectMapper) {
        return new ExceptionHandlingFilter(objectMapper);
    }
}
