package team7.inplace.security.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.user.application.UserService;
import team7.inplace.user.application.dto.UserCommand;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public CustomSuccessHandler(
        JwtUtil jwtUtil,
        UserService userService
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        addTokenToResponse(response, customOAuth2User);
        setRedirectUrlToResponse(response, customOAuth2User);
    }

    private void addTokenToResponse(
        HttpServletResponse response,
        CustomOAuth2User customOAuth2User
    ) {
        UserCommand.Info user = userService.getUserByUsername(customOAuth2User.username());
        Cookie accessTokenCookie = createCookie("access_token",
            jwtUtil.createAccessToken(user.username(), user.id(), user.role().getRoles()));
        Cookie refreshTokenCookie = createCookie("refresh_token",
            jwtUtil.createRefreshToken(user.username(), user.id(), user.role().getRoles()));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void setRedirectUrlToResponse(
        HttpServletResponse response,
        CustomOAuth2User customOAuth2User
    ) throws IOException {
        if (customOAuth2User.isFirstUser()) {
            response.sendRedirect("http://localhost:8080/choice");
            return;
        }
        response.sendRedirect("http://localhost:8080/auth");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

}
