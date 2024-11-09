package team7.inplace.place.application.command;

public record PlaceLikeCommand(
    Long placeId,
    Boolean likes
) {

}
