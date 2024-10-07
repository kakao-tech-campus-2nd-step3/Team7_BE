package team7.inplace.video.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team7.inplace.video.application.VideoService;
import team7.inplace.video.presentation.dto.VideoResponse;
import team7.inplace.video.presentation.dto.VideoSearchParams;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController implements VideoControllerApiSpec {
    private final VideoService videoService;

    @GetMapping()
    public ResponseEntity<Page<VideoResponse>> readVideos(
            @ModelAttribute VideoSearchParams searchParams,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<VideoResponse> videoResponses = videoService.getVideosBySurround(searchParams, pageable)
                .map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<Page<VideoResponse>> readByNew(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<VideoResponse> videoResponses = videoService.getAllVideosDesc(pageable)
                .map(VideoResponse::from);
        return new ResponseEntity<>(videoResponses, HttpStatus.OK);
    }

    // 조회수 반환 기능 개발 시 개발
    @GetMapping("/cool")
    public ResponseEntity<Page<VideoResponse>> readByCool(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        List<VideoResponse> videoResponses = new ArrayList<>();
        return new ResponseEntity<>(new PageImpl<>(videoResponses), HttpStatus.OK);
    }
}
