package team7.inplace.security.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import team7.inplace.security.application.dto.CustomOAuth2User;

class AuthorizationUtilTest {

    @BeforeEach
    void setUp() {
        CustomOAuth2User customOAuth2User = new CustomOAuth2User("test", 1L, false);
        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User,
            null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getUsername() {
        assertThat(AuthorizationUtil.getUsername()).isEqualTo("test");
    }

    @Test
    void getUserId() {
        assertThat(AuthorizationUtil.getUserId()).isEqualTo(1L);
    }
}
