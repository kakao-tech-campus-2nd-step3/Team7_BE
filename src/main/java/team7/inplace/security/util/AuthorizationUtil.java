package team7.inplace.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import team7.inplace.security.application.dto.CustomOAuth2User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationUtil {

    public static String getUsername() {
        if (isNotLoginUser()) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        return customOAuth2User.getName();
    }

    public static Long getUserId() {
        if (isNotLoginUser()) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        return customOAuth2User.id();
    }

    public static boolean isNotLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication.getPrincipal() instanceof CustomOAuth2User);
    }
}
