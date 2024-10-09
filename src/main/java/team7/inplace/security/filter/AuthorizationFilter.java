package team7.inplace.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.JwtUtil;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        if (hasNoTokenCookie(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenCookie(request).getValue();
        jwtUtil.validateExpired(token);
        addUserToAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private boolean hasNoTokenCookie(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("Authorization"))
                .findAny())
            .isEmpty();
    }

    private Cookie getTokenCookie(HttpServletRequest request) throws InplaceException {
        return Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals("Authorization"))
            .findAny()
            .orElse(null);
    }

    private void addUserToAuthentication(String token) throws InplaceException {
        String username = jwtUtil.getUsername(token);
        Long id = jwtUtil.getId(token);
        String roles = jwtUtil.getRoles(token);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(username, id, roles);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
