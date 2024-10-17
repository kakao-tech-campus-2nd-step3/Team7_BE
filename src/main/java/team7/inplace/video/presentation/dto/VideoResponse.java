package team7.inplace.video.presentation.dto;

import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.video.application.dto.VideoInfo;

// Video 엔티티의 Controller Response 정보 전달을 담당하는 클래스
public record VideoResponse(
        Long videoId,
        String videoAlias,
        String videoUrl,
        PlaceForVideo place
) {
    public static VideoResponse from(VideoInfo videoInfo) {
        return new VideoResponse(
                videoInfo.videoId(),
                videoInfo.videoAlias(),
                videoInfo.videoUrl(),
                videoInfo.place()
        );
    }
}