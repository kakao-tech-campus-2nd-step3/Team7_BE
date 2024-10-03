package team7.inplace.video.presentation;

import jakarta.servlet.http.HttpServletRequest;
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
public class VideoController implements VideoControllerApiSpec{
    private final VideoService videoService;

    // 토큰 필요 메서드
    @GetMapping()
    public ResponseEntity<List<VideoResponse>> readVideos(
            HttpServletRequest request,
            @RequestParam(name = "influencer", required = false) List<String> influencers,
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        // Authorization 헤더 추출
        String authHeader = request.getHeader("Authorization");
        // 토큰 존재 여부 검사
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // 토큰 유효성 검사

            // 토큰이 있는 경우
            return readByInfluencer(influencers);
        }

        // 토큰이 없는 경우
        return readBySurround(searchParams, page, size);
    }

    private ResponseEntity<List<VideoResponse>> readByInfluencer(List<String> influencers){
        List<VideoInfo> videoInfos = videoService.findByInfluencer(influencers);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    private ResponseEntity<List<VideoResponse>> readBySurround(VideoSearchParams searchParams, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<VideoInfo> videoInfos = videoService.findBySurround(searchParams, pageable);
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<List<VideoResponse>> readByNew() {
        List<VideoInfo> videoInfos = videoService.findAllDesc();
        List<VideoResponse> videoResponses = videoInfos.stream().map(VideoResponse::from).toList();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/cool")
    public ResponseEntity<List<VideoResponse>> readByCool() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

}
