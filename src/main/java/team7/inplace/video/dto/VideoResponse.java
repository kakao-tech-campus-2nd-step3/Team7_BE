package team7.inplace.video.dto;

import team7.inplace.place.dto.PlaceForVideo;

// Video 엔티티의 Controller Response 정보 전달을 담당하는 클래스
public record VideoResponse(Long videoId, String videoAlias, String videoUrl, PlaceForVideo place) {
    public VideoResponse(VideoData videoData) {
        this(videoData.videoId(), videoData.videoAlias(), videoData.videoUrl(), videoData.place());
    }
}
