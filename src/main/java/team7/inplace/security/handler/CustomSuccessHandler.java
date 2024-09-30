package team7.inplace.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.JwtUtil;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    public CustomSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws IOException, ServletException {

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        addAccessAndRefreshTokenToResponse(response, customOAuth2User);
    }

    private void addAccessAndRefreshTokenToResponse(HttpServletResponse response,
        CustomOAuth2User customOAuth2User)
        throws IOException {
        String username = customOAuth2User.getName();
        Long userId = customOAuth2User.id();
        Cookie accessTokenCookie = createCookie("access_token",
            jwtUtil.createAccessToken(username, userId));
        Cookie refreshTokenCookie = createCookie("refresh_token",
            jwtUtil.createRefreshToken(username, userId));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
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
