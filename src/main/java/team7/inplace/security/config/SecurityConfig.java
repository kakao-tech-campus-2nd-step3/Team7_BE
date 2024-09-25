package team7.inplace.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import team7.inplace.security.application.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //h2-console 접속 가능
        http.headers((headers) -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/h2-console/**").permitAll());

        //http 설정
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndPointConfig) -> userInfoEndPointConfig
                    .userService(customOauth2UserService)).defaultSuccessUrl("/"))
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/hello").authenticated()
                .anyRequest().permitAll())
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
