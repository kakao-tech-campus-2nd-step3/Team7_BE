package team7.inplace.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @Operation(
            summary = "새로 등록된 그 곳",
            description = "id를 기준으로 내림차순 정렬한 Video 정보를 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readByNew(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @Operation(
            summary = "쿨한 그 곳",
            description = "조회수를 기준으로 내림차순 정렬한 Video 정보를 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readByCool(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @Operation(
            summary = "내 인플루언서의 비디오 반환",
            description = "내가 좋아요를 누른 인플루언서의 Video 정보를 조회합니다."
    )
    ResponseEntity<Page<VideoResponse>> readByInfluencer(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );
}
