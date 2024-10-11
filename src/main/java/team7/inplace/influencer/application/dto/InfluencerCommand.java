package team7.inplace.influencer.application.dto;

import team7.inplace.influencer.domain.Influencer;

public record InfluencerCommand(
    String influencerName,
    String influencerImgUrl,
    String influencerJob
) {

    public static Influencer to(InfluencerCommand influencerCommand) {
        return new Influencer(
            influencerCommand.influencerName,
            influencerCommand.influencerImgUrl,
            influencerCommand.influencerJob
        );
    }
}
