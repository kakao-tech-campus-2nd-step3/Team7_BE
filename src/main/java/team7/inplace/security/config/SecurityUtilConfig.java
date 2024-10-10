package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.util.CookieUtil;
import team7.inplace.security.util.JwtUtil;

@Configuration
public class SecurityUtilConfig {

    @Bean
    public JwtUtil jwtUtil(JwtProperties jwtProperties) {
        return new JwtUtil(jwtProperties);
    }

    @Bean
    public CookieUtil cookieUtil() {
        return new CookieUtil();
    }
}
