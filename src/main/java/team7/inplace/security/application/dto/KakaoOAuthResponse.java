package team7.inplace.security.application.dto;

import java.util.Map;
import team7.inplace.security.domain.User;
import team7.inplace.security.domain.UserType;

public record KakaoOAuthResponse(
    Map<String, Object> attribute
) {

    public KakaoOAuthResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
    }

    public String getProvider() {
        return "kakao";
    }

    public String getEmail() {
        return attribute.get("email").toString();
    }

    public String getNickname() {
        return ((Map<String, Object>) attribute.get("profile")).get("nickname").toString();
    }

    public User toUser() {
        return new User(this.getEmail(), null, this.getNickname(), UserType.KAKAO);
    }
}
