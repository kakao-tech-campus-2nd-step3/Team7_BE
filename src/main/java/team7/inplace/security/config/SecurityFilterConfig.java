package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import team7.inplace.security.filter.ExceptionHandlingFilter;

@Component
@Configuration
public class SecurityFilterConfig {

    /* Bean으로 등록하여 SecurityConfig에서 주입 받아 사용할 경우 h2-console 사용 불가
    @Bean
    public AuthorizationFilter authorizationFilter(JwtUtil jwtUtil) {
        return new AuthorizationFilter(jwtUtil);
    }
    */

    @Bean
    public ExceptionHandlingFilter exceptionHandlingFilter() {
        return new ExceptionHandlingFilter();
    }
}
