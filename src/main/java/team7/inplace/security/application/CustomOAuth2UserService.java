package team7.inplace.security.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.application.dto.KakaoOAuthResponse;
import team7.inplace.security.domain.User;
import team7.inplace.security.domain.UserType;
import team7.inplace.security.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
        throws OAuth2AuthenticationException {
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        if (!provider.equals(UserType.KAKAO.getProvider())) {
            return null;
        }
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        KakaoOAuthResponse kakaoOAuthResponse = new KakaoOAuthResponse(oAuth2User.getAttributes());
        return updateUser(kakaoOAuthResponse);
    }

    private CustomOAuth2User updateUser(KakaoOAuthResponse kakaoOAuthResponse) {
        User existUser = userRepository.findByUsername(kakaoOAuthResponse.getEmail());
        if (existUser != null) {
            existUser.updateInfo(kakaoOAuthResponse.getNickname());
            userRepository.save(existUser);
            return new CustomOAuth2User(kakaoOAuthResponse.getEmail(),
                kakaoOAuthResponse.getNickname(), UserType.KAKAO);
        }
        User newUser = new User(kakaoOAuthResponse.getEmail(), null,
            kakaoOAuthResponse.getNickname(), UserType.KAKAO);
        userRepository.save(newUser);
        return new CustomOAuth2User(kakaoOAuthResponse.getEmail(), kakaoOAuthResponse.getNickname(),
            UserType.KAKAO);
    }
}
