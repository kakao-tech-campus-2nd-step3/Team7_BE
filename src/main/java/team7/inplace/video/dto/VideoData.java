package team7.inplace.video.dto;

import team7.inplace.place.dto.PlaceForVideo;

public record VideoData(Long videoId, String videoAlias, String videoUrl, PlaceForVideo place) {
}
