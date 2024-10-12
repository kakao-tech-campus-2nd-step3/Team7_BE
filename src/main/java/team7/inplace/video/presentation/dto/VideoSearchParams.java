package team7.inplace.video.presentation.dto;

public record VideoSearchParams(
        String topLeftLongitude,
        String topLeftLatitude,
        String bottomRightLongitude,
        String bottomRightLatitude,
        String longitude,
        String latitude
) {
}
