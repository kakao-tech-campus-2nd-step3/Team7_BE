package team7.inplace.influencer.presentation.dto;

import team7.inplace.influencer.application.dto.InfluencerCommand;

public record InfluencerRequest(
    String influencerName,
    String influencerImgUrl,
    String influencerJob
) {

    public static InfluencerCommand to(InfluencerRequest request) {
        return new InfluencerCommand(
            request.influencerName(),
            request.influencerImgUrl(),
            request.influencerJob()
        );
    }
}
