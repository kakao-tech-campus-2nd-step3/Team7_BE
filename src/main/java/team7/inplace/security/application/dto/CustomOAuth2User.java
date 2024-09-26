package team7.inplace.security.application.dto;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team7.inplace.security.domain.User;
import team7.inplace.security.domain.UserType;

public record CustomOAuth2User(
    String username,
    String nickname,
    UserType userType
) implements OAuth2User {

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return username;
    }

    public static CustomOAuth2User of(User user) {
        return new CustomOAuth2User(user.getUsername(), user.getNickname(), user.getUserType());
    }
}
