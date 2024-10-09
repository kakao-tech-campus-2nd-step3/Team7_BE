package team7.inplace.user.application.dto;

import team7.inplace.security.application.dto.KakaoOAuthResponse;
import team7.inplace.user.domain.Role;
import team7.inplace.user.domain.User;
import team7.inplace.user.domain.UserType;

public class UserCommand {

    public record Create(
        String username,
        String nickname,
        UserType userType,
        Role role
    ) {

        public static UserCommand.Create of(KakaoOAuthResponse kakaoOAuthResponse) {
            return new UserCommand.Create(kakaoOAuthResponse.getEmail(),
                kakaoOAuthResponse.getNickname(), UserType.KAKAO, Role.USER);
        }

        public User toEntity() {
            return new User(this.username, null, this.nickname, this.userType, this.role);
        }
    }

    public record Info(
        Long id,
        String username,
        Role role
    ) {

        public static Info of(User user) {
            return new Info(user.getId(), user.getUsername(), user.getRole());
        }
    }
}
