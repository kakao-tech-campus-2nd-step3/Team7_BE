package team7.inplace.security.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.user.domain.Role;

class AuthorizationUtilTest {

    @Test
    void 유저가_있는_경우_getUsername() {
        setUser();
        assertThat(AuthorizationUtil.getUsername()).isEqualTo("test");
    }

    @Test
    void 유저가_있는_경우_getUserId() {
        setUser();
        assertThat(AuthorizationUtil.getUserId()).isEqualTo(1L);
    }

    @Test
    void 유저가_없는_경우_getUsername() {
        setAnonymousUser();
        assertThat(AuthorizationUtil.getUsername()).isNull();
    }

    @Test
    void 유저가_없는_경우_getUserId() {
        setAnonymousUser();
        assertThat(AuthorizationUtil.getUserId()).isNull();
    }

    private void setAnonymousUser() {
        Authentication authentication = new AnonymousAuthenticationToken("key", "anonymous",
            AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void setUser() {
        CustomOAuth2User customOAuth2User = new CustomOAuth2User("test", 1L, Role.USER.getRoles());
        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User,
            null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
