package team7.inplace.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.security.application.dto.CustomOAuth2User;
import team7.inplace.security.util.JwtUtil;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenCookie(cookies).getValue();
        addUserToAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private Cookie getTokenCookie(Cookie[] cookies) throws InplaceException {
        Cookie tokenCookie = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals("Authorization"))
            .findFirst()
            .orElseThrow(() -> InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY));
        validateToken(tokenCookie);
        return tokenCookie;
    }

    private void addUserToAuthentication(String token) throws InplaceException {
        String username = jwtUtil.getUsername(token);
        Long id = jwtUtil.getId(token);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(username, id, false);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void validateToken(Cookie authorizationCookie) throws InplaceException {
        validateTokenEmpty(authorizationCookie);
        jwtUtil.validateExpired(authorizationCookie.getValue());
    }

    private void validateTokenEmpty(Cookie authorizationCookie) throws InplaceException {
        if (authorizationCookie.getValue() == null) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }
    }

}
