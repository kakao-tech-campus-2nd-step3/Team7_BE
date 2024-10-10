package team7.inplace.security.handler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.CookieUtil;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.token.application.RefreshTokenService;
import team7.inplace.user.application.UserService;
import team7.inplace.user.application.dto.UserCommand;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;

    public CustomSuccessHandler(
        JwtUtil jwtUtil,
        UserService userService,
        RefreshTokenService refreshTokenService,
        CookieUtil cookieUtil
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        UserCommand.Info userInfo = userService.getUserByUsername(customOAuth2User.username());
        String accessToken = jwtUtil.createAccessToken(userInfo.username(), userInfo.id(),
            userInfo.role().getRoles());
        String refreshToken = jwtUtil.createRefreshToken(userInfo.username(), userInfo.id(),
            userInfo.role().getRoles());
        refreshTokenService.saveRefreshToken(userInfo.username(), refreshToken);
        addTokenToResponse(response, accessToken, refreshToken);
        setRedirectUrlToResponse(response, customOAuth2User);
    }

    private void addTokenToResponse(
        HttpServletResponse response,
        String accessToken, String refreshToken
    ) {
        Cookie accessTokenCookie = cookieUtil.createCookie("access_token", accessToken);
        Cookie refreshTokenCookie = cookieUtil.createCookie("refresh_token", refreshToken);

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
}
