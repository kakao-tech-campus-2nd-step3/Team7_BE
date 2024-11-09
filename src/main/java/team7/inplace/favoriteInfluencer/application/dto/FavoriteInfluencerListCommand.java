package team7.inplace.favoriteInfluencer.application.dto;

import java.util.List;

public record FavoriteInfluencerListCommand(
    List<Long> influencerIds,
    Boolean likes
) {

}
