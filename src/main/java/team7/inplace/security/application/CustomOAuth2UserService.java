package team7.inplace.security.application;

import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.application.dto.KakaoOAuthResponse;
import team7.inplace.security.domain.User;
import team7.inplace.security.persistence.UserRepository;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final UserRepository userRepository;

    public CustomOAuth2UserService(DefaultOAuth2UserService defaultOAuth2UserService,
        UserRepository userRepository) {
        this.defaultOAuth2UserService = defaultOAuth2UserService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
        throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(oAuth2UserRequest);
        KakaoOAuthResponse kakaoOAuthResponse = new KakaoOAuthResponse(oAuth2User.getAttributes());
        User user = register(kakaoOAuthResponse);
        return CustomOAuth2User.of(user);
    }

    private User register(KakaoOAuthResponse kakaoOAuthResponse) {
        if (isExistUser(kakaoOAuthResponse)) {
            return userRepository.findByUsername(kakaoOAuthResponse.getEmail())
                .orElseThrow();
        }
        User user = kakaoOAuthResponse.toUser();
        userRepository.save(user);
        return user;
    }

    private boolean isExistUser(KakaoOAuthResponse kakaoOAuthResponse) {
        return userRepository.existsByUsername(kakaoOAuthResponse.getEmail());
    }
}
