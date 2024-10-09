package team7.inplace.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import team7.inplace.security.application.dto.CustomOAuth2User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationUtil {

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousUser(authentication)) {
            return null;
        }
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        return customOAuth2User.getName();
    }

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isAnonymousUser(authentication)) {
            return null;
        }
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        return customOAuth2User.id();
    }

    private static boolean isAnonymousUser(Authentication authentication) {
        return !(authentication.getPrincipal() instanceof CustomOAuth2User);
    }
}
