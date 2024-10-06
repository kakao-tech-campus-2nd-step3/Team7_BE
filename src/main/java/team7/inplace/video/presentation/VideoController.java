package team7.inplace.video.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public ResponseEntity<Page<VideoResponse>> readVideos(
            HttpServletRequest request,
            @RequestParam(name = "influencer", required = false) List<String> influencers,
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        // 토큰 존재 여부 검사
        if(true) {
            // 토큰 유효성 검사

            // 토큰이 있는 경우
            return readByInfluencer(influencers, pageable);
        }

        // 토큰이 없는 경우
        return readBySurround(searchParams, pageable);
    }

    private ResponseEntity<Page<VideoResponse>> readByInfluencer(List<String> influencers, Pageable pageable){
        Page<VideoInfo> videoInfos = videoService.getByVideosInfluencer(influencers, pageable);
        Page<VideoResponse> videoResponses = videoInfos.map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    private ResponseEntity<Page<VideoResponse>> readBySurround(VideoSearchParams searchParams, Pageable pageable) {
        Page<VideoInfo> videoInfos = videoService.getVideosBySurround(searchParams, pageable);
        Page<VideoResponse> videoResponses = videoInfos.map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<VideoResponse>> readByNew(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoInfo> videoInfos = videoService.getAllVideosDesc(pageable);
        Page<VideoResponse> videoResponses = videoInfos.map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/cool")
    public ResponseEntity<Page<VideoResponse>> readByCool() {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(new PageImpl<>(videoResponses), HttpStatus.OK);
    }

}
