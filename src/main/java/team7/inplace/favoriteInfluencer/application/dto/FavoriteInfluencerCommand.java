package team7.inplace.favoriteInfluencer.application.dto;

public record FavoriteInfluencerCommand(
    Long influencerId,
    Boolean likes
) {

}
