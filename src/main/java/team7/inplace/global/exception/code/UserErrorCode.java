package team7.inplace.global.exception.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User가 없습니다."),
    OAUTH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "OauthToken이 없습니다."),
    FAIL_OAUTH_TOKEN_DECRYPT(HttpStatus.INTERNAL_SERVER_ERROR, "U003", "OauthToken 복호화 실패"),
    FAIL_OAUTH_TOKEN_ENCRYPT(HttpStatus.INTERNAL_SERVER_ERROR, "U004", "OauthToken 암호화 실패");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
