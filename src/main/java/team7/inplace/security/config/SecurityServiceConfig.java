package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import team7.inplace.security.application.CustomOAuth2UserService;
import team7.inplace.user.application.UserService;

@Configuration
public class SecurityServiceConfig {

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService(
        DefaultOAuth2UserService defaultOAuth2UserService,
        UserService userService
    ) {
        return new CustomOAuth2UserService(defaultOAuth2UserService, userService);
    }
}
