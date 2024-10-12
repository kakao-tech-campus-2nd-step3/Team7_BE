package team7.inplace.token.presentation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.security.util.CookieUtil;
import team7.inplace.security.util.JwtUtil;
import team7.inplace.token.application.RefreshTokenFacade;
import team7.inplace.token.application.dto.TokenCommand.ReIssued;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenFacade refreshTokenFacade;
    private final CookieUtil cookieUtil;

    @GetMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(@CookieValue(value = "refresh_token") Cookie cookie,
        HttpServletResponse response) {
        if (cannotRefreshToken(cookie)) {
            throw InplaceException.of(AuthorizationErrorCode.INVALID_TOKEN);
        }

        String refreshToken = cookie.getValue();
        ReIssued reIssuedToken = refreshTokenFacade.getReIssuedRefreshTokenCookie(
            jwtUtil.getUsername(refreshToken), refreshToken);
        addTokenToCookie(response, reIssuedToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void addTokenToCookie(HttpServletResponse response, ReIssued reIssuedToken) {
        Cookie accessTokenCookie = cookieUtil.createCookie("access_token",
            reIssuedToken.accessToken());
        Cookie refreshTokenCookie = cookieUtil.createCookie("refresh_token",
            reIssuedToken.refreshToken());
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private boolean cannotRefreshToken(Cookie cookie) {
        return AuthorizationUtil.getUserId() == null || !jwtUtil.isRefreshToken(cookie.getValue());
    }

}
