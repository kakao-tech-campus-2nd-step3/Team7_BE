package team7.inplace.security.application.dto;

import java.util.Map;

public record KakaoOAuthResponse(
    Map<String, Object> attribute
) {

    public KakaoOAuthResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
    }

    public String getEmail() {
        return attribute.get("email").toString();
    }

    public String getNickname() {
        return ((Map<String, Object>) attribute.get("profile")).get("nickname").toString();
    }

}
