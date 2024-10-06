package team7.inplace.security.application.dto;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record CustomOAuth2User(
    String username,
    Long id,
    Boolean firstUser
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

    public static CustomOAuth2User makeExistUser(KakaoOAuthResponse kakaoOAuthResponse) {
        return new CustomOAuth2User(kakaoOAuthResponse.getEmail(), null, false);
    }

    public static CustomOAuth2User makeNewUser(KakaoOAuthResponse kakaoOAuthResponse) {
        return new CustomOAuth2User(kakaoOAuthResponse.getEmail(), null, true);
    }

    public Boolean isFirstUser() {
        return this.firstUser;
    }
}
