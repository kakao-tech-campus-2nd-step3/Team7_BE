package team7.inplace.video.dto;

import team7.inplace.place.dto.PlaceForVideo;

// Video 도메인의 Controller와 Service 사이의 정보 전달을 담당하는 클래스 ( Service Return )
public record VideoData(Long videoId, String videoAlias, String videoUrl, PlaceForVideo place) {
}
