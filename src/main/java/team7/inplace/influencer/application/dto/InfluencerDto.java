package team7.inplace.influencer.application.dto;

public record InfluencerDto(
    Long influencerId,
    String influencerName,
    String influencerImgUrl,
    String influencerJob,
    boolean likes
) {

}
