package team7.inplace.influencer.presentation.dto;

import team7.inplace.influencer.application.dto.InfluencerInfo;

public record InfluencerResponse(
    Long influencerId,
    String influencerName,
    String influencerImgUrl,
    String influencerJob,
    boolean likes
) {

    public static InfluencerResponse from(InfluencerInfo influencerInfo) {
        return new InfluencerResponse(
            influencerInfo.influencerId(),
            influencerInfo.influencerName(),
            influencerInfo.influencerImgUrl(),
            influencerInfo.influencerJob(),
            influencerInfo.likes()
        );
    }
}
