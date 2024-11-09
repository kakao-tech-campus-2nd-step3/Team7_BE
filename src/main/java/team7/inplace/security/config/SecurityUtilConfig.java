package team7.inplace.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.security.util.TokenEncryptionUtil;

@Configuration
public class SecurityUtilConfig {

    @Value("${spring.oauth.password}")
    private String oauthPassword;

    @Value("${spring.oauth.salt}")
    private String oauthSalt;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public JwtUtil jwtUtil(JwtProperties jwtProperties) {
        return new JwtUtil(jwtProperties);
    }

    @Bean
    public TokenEncryptionUtil tokenEncryptionUtil() {
        return new TokenEncryptionUtil(oauthPassword, oauthSalt);
    }
}
