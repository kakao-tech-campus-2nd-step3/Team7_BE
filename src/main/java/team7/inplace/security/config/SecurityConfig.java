package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team7.inplace.security.application.CustomOAuth2UserService;
import team7.inplace.security.filter.JwtFilter;
import team7.inplace.security.handler.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtFilter jwtFilter;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
        CustomSuccessHandler customSuccessHandler, JwtFilter jwtFilter) {
        this.customOauth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        //h2-console 접속 가능
        http.headers((headers) -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/h2-console/**").permitAll());

        //http 설정
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            //authentication Service, Handler 설정
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndPointConfig) -> userInfoEndPointConfig
                    .userService(customOauth2UserService)).successHandler(customSuccessHandler))

            //authentication Filter 설정
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            //authentication 경로 설정
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/hello").authenticated()
                .anyRequest().permitAll())

            //session 설정
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
