package team7.inplace.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum PlaceErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "장소 정보를 찾을 수 없습니다."),
    PLACE_LOCATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "P002", "장소 위치 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String code() {
        return errorCode;
    }

    @Override
    public String message() {
        return message;
    }
}
