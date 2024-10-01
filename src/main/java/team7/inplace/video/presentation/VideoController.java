package team7.inplace.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.presentation.dto.VideoResponse;
import team7.inplace.video.application.VideoService;
import team7.inplace.video.presentation.dto.VideoSearchParams;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;

    // 토큰 필요 메서드
    @GetMapping()
    @Operation(summary = "내 인플루언서가 방문한 그곳", description = "인플루언서를 기준으로 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByInfluencer(
            @RequestParam(name = "influencer", required = false) List<String> influencers
    ) {
        List<VideoInfo> videoInfos = videoService.findByInfluencer(influencers);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 토큰 불필요 메서드
    @GetMapping()
    @Operation(summary = "내 주변 그 곳", description = "내 위치를 기준으로 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readBySurround(
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        List<VideoInfo> videoInfos = videoService.findBySurround(searchParams, pageable);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/new")
    @Operation(summary = "새로 등록된 그 곳", description = "id를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByNew() {
        List<VideoInfo> videoInfos = videoService.findAllDESC();
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/cool")
    @Operation(summary = "쿨한 그 곳", description = "조회수를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByCool() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

}
