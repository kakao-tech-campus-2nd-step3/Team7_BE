package team7.inplace.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserControllerApiSepc {
    @Operation(
            summary = "내 주변 그곳 ",
            description = "Parameter로 입력받은 위치의 주변 장소 Video를 조회합니다."
    )
    ResponseEntity<Void> updateNickname(
            @RequestParam String nickname
    );
}