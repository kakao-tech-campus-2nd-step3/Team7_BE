package team7.inplace.video.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.presentation.dto.VideoResponse;
import team7.inplace.video.application.VideoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    // 토큰 필요 메서드
    @GetMapping("/video")
    @Operation(summary = "내 인플루언서가 방문한 그곳", description = "인플루언서를 기준으로 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByInfluencer(
            @RequestParam(name = "influencer", required = false) List<String> influencers
    ) {
        List<VideoInfo> videoInfos = videoService.findByInfluencer(influencers);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::of).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 토큰 불필요 메서드
    // 내 주변 장소 반환 기능 ( PR 존재 ) weekly에 merge 시 개발
    @GetMapping("/video")
    @Operation(summary = "내 주변 그 곳", description = "내 위치를 기준으로 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readBySurround(
            @RequestParam(name = "longitude") String longtitude,
            @RequestParam(name = "latitude") String latitude
    ) {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/videos/new")
    @Operation(summary = "새로 등록된 그 곳", description = "id를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByNew() {
        List<VideoInfo> videoInfos = videoService.findAllDESC();
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::of).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/videos/cool")
    @Operation(summary = "쿨한 그 곳", description = "조회수를 기준으로 내림차순 정렬한 Video 정보를 조회합니다.")
    public ResponseEntity<List<VideoResponse>> readByCool() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

}
