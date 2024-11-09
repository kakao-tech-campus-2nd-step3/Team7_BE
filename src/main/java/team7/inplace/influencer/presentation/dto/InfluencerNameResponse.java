package team7.inplace.influencer.presentation.dto;

import team7.inplace.influencer.application.dto.InfluencerNameInfo;

public record InfluencerNameResponse(
    String influencerName
) {

    public static InfluencerNameResponse from(InfluencerNameInfo influencerNameInfo) {
        return new InfluencerNameResponse(
            influencerNameInfo.name()
        );
    }
}
