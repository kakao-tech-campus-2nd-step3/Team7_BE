package team7.inplace.video.presentation;

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
            @ModelAttribute VideoSearchParams searchParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoInfo> videoInfos = videoService.getVideosBySurround(searchParams, pageable);
        Page<VideoResponse> videoResponses = videoInfos.map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<VideoResponse>> readByNew(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<VideoInfo> videoInfos = videoService.getAllVideosDesc(pageable);
        Page<VideoResponse> videoResponses = videoInfos.map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/cool")
    public ResponseEntity<Page<VideoResponse>> readByCool(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(new PageImpl<>(videoResponses), HttpStatus.OK);
    }

}
