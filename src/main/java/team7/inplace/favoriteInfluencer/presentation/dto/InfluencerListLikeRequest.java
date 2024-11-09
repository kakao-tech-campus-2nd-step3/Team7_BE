package team7.inplace.favoriteInfluencer.presentation.dto;

import java.util.List;
import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerListCommand;

public record InfluencerListLikeRequest(
    List<Long> influencerIds,
    Boolean likes
) {

    public FavoriteInfluencerListCommand toCommand() {
        return new FavoriteInfluencerListCommand(influencerIds, likes);
    }
}
