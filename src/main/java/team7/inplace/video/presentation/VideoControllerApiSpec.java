package team7.inplace.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import team7.inplace.video.presentation.dto.VideoResponse;
import team7.inplace.video.presentation.dto.VideoSearchParams;

public interface VideoControllerApiSpec {
    @Operation(
            summary = "내 주변 그곳 ",
            description = "Parameter로 입력받은 위치의 주변 장소들을 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readVideos(
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "새로 등록된 그 곳",
            description = "id를 기준으로 내림차순 정렬한 Video 정보를 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readByNew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(
            summary = "쿨한 그 곳",
            description = "조회수를 기준으로 내림차순 정렬한 Video 정보를 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readByCool(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );
}
