package team7.inplace.video.presentation;

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

    // 내 인플루언서가 방문한 그 곳 ( 토큰 0 )
    @GetMapping("/video")
    public ResponseEntity<List<VideoResponse>> readByInfluencer(
            @RequestParam(name = "influencer", required = false) List<String> influencers
    ) {
        List<VideoInfo> videoInfos = videoService.findByInfluencer(influencers);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::of).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 내 주변 그 곳 ( 토큰 x )
    // Place 쪽에 기능을 넣어야 할 것 같다 ( 내 위치 기준으로 가까운 장소 가져오기 )
    @GetMapping("/video")
    public ResponseEntity<List<VideoResponse>> readBySurround(
            @RequestParam(name = "longitude") String longtitude,
            @RequestParam(name = "latitude") String latitude
    ) {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 새로 등록된 그 곳
    @GetMapping("/videos/new")
    public ResponseEntity<List<VideoResponse>> readByNew() {
        List<VideoInfo> videoInfos = videoService.findAllDESC();
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::of).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 쿨한 그 곳
    // 조회수 가져오는 로직 개발 전에 완성 못함
    @GetMapping("/videos/cool")
    public ResponseEntity<List<VideoResponse>> readByCool() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

}
