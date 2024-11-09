package team7.inplace.placeMessage.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PlaceMessageControllerApiSpec {

    @Operation(
        summary = "장소 정보 보내기",
        description = "장소 정보에 대한 메세지를 토큰을 사용하여 보냄."
    )
    ResponseEntity<Void> test(@PathVariable("place-id") Long placeId);
}
