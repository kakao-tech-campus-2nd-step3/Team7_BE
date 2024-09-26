package team7.inplace.influencer.presentation.dto;

import team7.inplace.influencer.application.dto.InfluencerDto;

public record InfluencerListResponse(
    Long influencerId,
    String influencerName,
    String influencerImgUrl,
    String influencerJob,
    boolean likes
) {

    public static InfluencerListResponse convertToResponse(InfluencerDto influencerDto) {
        return new InfluencerListResponse(
            influencerDto.influencerId(),
            influencerDto.influencerName(),
            influencerDto.influencerImgUrl(),
            influencerDto.influencerJob(),
            influencerDto.likes()
        );
    }
}
