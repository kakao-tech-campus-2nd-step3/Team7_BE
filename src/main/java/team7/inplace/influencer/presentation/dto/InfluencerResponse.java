package team7.inplace.influencer.presentation.dto;

import team7.inplace.influencer.application.dto.InfluencerDto;

public record InfluencerResponse(
    Long influencerId,
    String influencerName,
    String influencerImgUrl,
    String influencerJob,
    boolean likes
) {

    public static InfluencerResponse convertToResponse(InfluencerDto influencerDto) {
        return new InfluencerResponse(
            influencerDto.influencerId(),
            influencerDto.influencerName(),
            influencerDto.influencerImgUrl(),
            influencerDto.influencerJob(),
            influencerDto.likes()
        );
    }
}
