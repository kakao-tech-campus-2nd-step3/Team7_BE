package team7.inplace.security.application;

import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.application.dto.KakaoOAuthResponse;
import team7.inplace.user.application.UserService;
import team7.inplace.user.application.dto.UserCommand;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final UserService userService;

    public CustomOAuth2UserService(
        DefaultOAuth2UserService defaultOAuth2UserService,
        UserService userService
    ) {
        this.defaultOAuth2UserService = defaultOAuth2UserService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
        throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(oAuth2UserRequest);
        KakaoOAuthResponse kakaoOAuthResponse = new KakaoOAuthResponse(oAuth2User.getAttributes());
        if (userService.isExistUser(kakaoOAuthResponse.getEmail())) {
            return CustomOAuth2User.makeExistUser(kakaoOAuthResponse);
        }
        userService.registerUser(UserCommand.Create.of(kakaoOAuthResponse));
        return CustomOAuth2User.makeNewUser(kakaoOAuthResponse);
    }
}
