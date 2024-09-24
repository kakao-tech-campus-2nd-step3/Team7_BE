package team7.inplace.video.presentation.dto;

import team7.inplace.place.dto.PlaceForVideo;
import team7.inplace.video.application.dto.VideoInfo;

// Video 엔티티의 Controller Response 정보 전달을 담당하는 클래스
public record VideoResponse(
        Long videoId,
        String videoAlias,
        String videoUrl,
        PlaceForVideo place
) {
    public VideoResponse(VideoInfo videoData) {
        this(videoData.videoId(), videoData.videoAlias(), videoData.videoUrl(), videoData.place());
    }
}
