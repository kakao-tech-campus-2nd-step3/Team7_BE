package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.entryPoint.LoginAuthenticationEntryPoint;

@Configuration
public class SecurityEntryPointConfig {

    @Bean
    public LoginAuthenticationEntryPoint loginAuthenticationEntryPoint() {
        return new LoginAuthenticationEntryPoint();
    }
}
