package team7.inplace.video.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.video.dto.VideoData;
import team7.inplace.video.dto.VideoResponse;
import team7.inplace.video.service.VideoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    // 내 인플루언서가 방문한 그 곳
    @GetMapping("/video")
    public ResponseEntity<List<VideoResponse>> readByInfluencer(
            @RequestParam(name="influencer", required = false) List<String> influencers
    ){
        List<VideoData> videoDatas = videoService.findByInfluencer(influencers);
        List<VideoResponse> videoInfo = videoDatas.stream().map(VideoResponse::new).toList();
        return new ResponseEntity<>(videoInfo, HttpStatus.OK);
    }
}
