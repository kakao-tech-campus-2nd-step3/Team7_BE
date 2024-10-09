package team7.inplace.influencer.application.dto;

import team7.inplace.influencer.domain.Influencer;

public record InfluencerInfo(
    Long influencerId,
    String influencerName,
    String influencerImgUrl,
    String influencerJob,
    boolean likes
) {

    public static InfluencerInfo from(Influencer influencer) {
        return new InfluencerInfo(
            influencer.getId(),
            influencer.getName(),
            influencer.getImgUrl(),
            influencer.getJob(),
            false // 좋아요 기능 추가할 때 로직 추가 예정
        );
    }
}
