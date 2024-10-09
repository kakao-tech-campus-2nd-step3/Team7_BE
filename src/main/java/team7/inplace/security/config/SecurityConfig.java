package team7.inplace.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import team7.inplace.security.application.CustomOAuth2UserService;
import team7.inplace.security.filter.AuthorizationFilter;
import team7.inplace.security.filter.ExceptionHandlingFilter;
import team7.inplace.security.handler.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOauth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final ExceptionHandlingFilter exceptionHandlingFilter;
    private final AuthorizationFilter authorizationFilter;

    public SecurityConfig(
        CustomOAuth2UserService customOAuth2UserService,
        CustomSuccessHandler customSuccessHandler,
        ExceptionHandlingFilter exceptionHandlingFilter,
        AuthorizationFilter authorizationFilter
    ) {
        this.customOauth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.exceptionHandlingFilter = exceptionHandlingFilter;
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

        //http 설정
        http.csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            //authentication Service, Handler 설정
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndPointConfig) -> userInfoEndPointConfig
                    .userService(customOauth2UserService)).successHandler(customSuccessHandler))

            //authentication Filter 설정
            .addFilterBefore(authorizationFilter,
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(exceptionHandlingFilter, AuthorizationFilter.class)
            //authentication 경로 설정
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login").permitAll()
                .requestMatchers("/hello").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll())

            //session 설정
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
