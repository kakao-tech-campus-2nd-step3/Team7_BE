package team7.inplace.video.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.video.application.VideoService;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.presentation.dto.VideoResponse;

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
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }
}
