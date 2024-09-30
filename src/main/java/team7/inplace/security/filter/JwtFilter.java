package team7.inplace.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.JwtUtil;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        Cookie authorizationCookie = getAuthorizationCookie(request);
        if (isCookieInvalid(authorizationCookie)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationCookie.getValue();
        addUserToAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private void addUserToAuthentication(String token) {
        String username = jwtUtil.getUsername(token);
        Long id = jwtUtil.getId(token);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(username, id, null, null);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private Cookie getAuthorizationCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals("Authorization"))
            .findFirst().orElse(null);
    }

    private boolean isCookieInvalid(Cookie authorizationCookie) {
        return isCookieEmpty(authorizationCookie) || jwtUtil.isExpired(
            authorizationCookie.getValue());
    }

    private boolean isCookieEmpty(Cookie authorizationCookie) {
        return authorizationCookie == null || authorizationCookie.getValue() == null;
    }

}
