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

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;

    public CustomSuccessHandler(
        JwtUtil jwtUtil,
        RefreshTokenService refreshTokenService,
        CookieUtil cookieUtil
    ) {
        this.jwtUtil = jwtUtil;
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
        String accessToken = jwtUtil.createAccessToken(customOAuth2User.username(),
            customOAuth2User.id(), customOAuth2User.roles());
        String refreshToken = jwtUtil.createRefreshToken(customOAuth2User.username(),
            customOAuth2User.id(), customOAuth2User.roles());
        refreshTokenService.saveRefreshToken(customOAuth2User.username(), refreshToken);
        addTokenToResponse(response, accessToken, refreshToken);
        setRedirectUrlToResponse(request, response, customOAuth2User);
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
        HttpServletRequest request,
        HttpServletResponse response,
        CustomOAuth2User customOAuth2User
    ) throws IOException {
        if (customOAuth2User.isFirstUser()) {
            response.sendRedirect("/choice");
            return;
        }
        response.sendRedirect("/auth");
    }
}
