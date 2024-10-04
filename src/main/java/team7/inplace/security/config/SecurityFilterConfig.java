package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import team7.inplace.security.filter.AuthorizationFilter;
import team7.inplace.security.filter.ExceptionHandlingFilter;
import team7.inplace.security.util.JwtUtil;

@Component
@Configuration
public class SecurityFilterConfig {

    @Bean
    public AuthorizationFilter authorizationFilter(JwtUtil jwtUtil) {
        return new AuthorizationFilter(jwtUtil);
    }

    @Bean
    public ExceptionHandlingFilter exceptionHandlingFilter() {
        return new ExceptionHandlingFilter();
    }
}
