package team7.inplace.video.DTO;

public record VideoResponse(Long videoId, String videoAlias, String videoUrl, PlaceForVideo place) {
    public VideoResponse(VideoData videoData) {
        this(videoData.videoId(), videoData.videoAlias(), videoData.videoUrl(), videoData.place());
    }
}
