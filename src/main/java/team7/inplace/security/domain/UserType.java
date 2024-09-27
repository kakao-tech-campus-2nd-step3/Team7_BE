package team7.inplace.security.domain;

import lombok.Getter;

@Getter
public enum UserType {
    KAKAO("kakao");

    private final String provider;

    UserType(String provider) {
        this.provider = provider;
    }
}
