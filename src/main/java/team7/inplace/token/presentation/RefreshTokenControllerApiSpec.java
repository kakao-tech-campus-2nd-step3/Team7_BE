package team7.inplace.token.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;

public interface RefreshTokenControllerApiSpec {

    @Operation(
        summary = "토큰 리프레시",
        description = "Cookie에 refreshToken을 보내면 accessToken과 refreshToken을 새로 발급함."
    )
    public ResponseEntity<Void> refreshToken(
        @CookieValue(value = "refresh_token") Cookie cookie,
        HttpServletResponse response
    );
}
