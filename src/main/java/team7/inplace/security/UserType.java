package team7.inplace.security;

public enum UserType {
    KAKAO("kakao");

    private final String provider;

    UserType(String provider) {
        this.provider = provider;
    }
}
