package team7.inplace.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthorizationErrorCode {
    TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "Token is Empty"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "Invalid Token"),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST, "Token is Expired");

    private final HttpStatus httpStatus;
    private final String message;
}
