package team7.inplace.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import team7.inplace.video.presentation.dto.VideoResponse;
import team7.inplace.video.presentation.dto.VideoSearchParams;

import java.util.List;


public interface VideoControllerApiSpec {
    @Operation(
            summary = "내 인플루언서가 방문한 or 내 주변 그곳 ",
            description = "토큰의 유무에 따라 다른 동작을 수행합니다."
    )
    ResponseEntity<Page<VideoResponse>> readVideos(
            HttpServletRequest request,
            @RequestParam(name = "influencer", required = false) List<String> influencers,
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    );

    @Operation(summary = "새로 등록된 그 곳", description = "id를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    ResponseEntity<Page<VideoResponse>> readByNew(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    );

    @Operation(summary = "쿨한 그 곳", description = "조회수를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    ResponseEntity<Page<VideoResponse>> readByCool();
}
