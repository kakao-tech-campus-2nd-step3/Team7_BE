package team7.inplace.video.dto;

import team7.inplace.place.dto.PlaceForVideo;

public record VideoResponse(Long videoId, String videoAlias, String videoUrl, PlaceForVideo place) {
    public VideoResponse(VideoData videoData) {
        this(videoData.videoId(), videoData.videoAlias(), videoData.videoUrl(), videoData.place());
    }
}
