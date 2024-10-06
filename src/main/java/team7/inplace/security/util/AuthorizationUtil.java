package team7.inplace.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import team7.inplace.security.application.dto.CustomOAuth2User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationUtil {

    public static String getUsername() {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return customOAuth2User.getName();
    }

    public static Long getUserId() {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return customOAuth2User.id();
    }
}
