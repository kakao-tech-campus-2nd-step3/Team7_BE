package team7.inplace.place.application.dto;

public record PlaceForVideo(
        Long placeId,
        String placeName
) {
    public static PlaceForVideo of(Long placeId, String placeName) {
        return new PlaceForVideo(
                placeId,
                placeName
        );
    }
}
