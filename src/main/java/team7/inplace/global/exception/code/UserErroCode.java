package team7.inplace.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErroCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User is not found");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String code() {
        return "";
    }

    @Override
    public String message() {
        return "";
    }
}
