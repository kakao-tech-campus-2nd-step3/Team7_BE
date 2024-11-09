package team7.inplace.favoriteInfluencer.presentation.dto;

import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerCommand;

public record InfluencerLikeRequest(
    Long influencerId,
    Boolean likes
) {

    public FavoriteInfluencerCommand toCommand() {
        return new FavoriteInfluencerCommand(influencerId, likes);
    }
}
