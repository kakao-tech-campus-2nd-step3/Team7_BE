package team7.inplace.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.util.CookieUtil;
import team7.inplace.security.util.JwtUtil;

@Configuration
public class SecurityUtilConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JwtUtil jwtUtil(JwtProperties jwtProperties) {
        return new JwtUtil(jwtProperties);
    }

    @Bean
    public CookieUtil cookieUtil() {
        return new CookieUtil();
    }
}
