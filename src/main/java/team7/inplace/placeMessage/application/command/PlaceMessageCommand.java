package team7.inplace.placeMessage.application.command;

import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Place;
import team7.inplace.video.application.AliasUtil;
import team7.inplace.video.domain.Video;

public record PlaceMessageCommand(
    Long placeId,
    String title,
    String address,
    String imageUrl,
    String description
) {

    public static PlaceMessageCommand of(Place place, Influencer influencer, Video video) {
        String influencerName = influencer != null ? influencer.getName() : null;
        String videoUUID = video != null ? video.getVideoUUID() : null;
        return new PlaceMessageCommand(
            place.getId(),
            place.getName(),
            place.getAddress().toString(),
            String.format("https://img.youtube.com/vi/%s/maxresdefault.jpg", videoUUID),
            AliasUtil.makeAlias(
                influencerName,
                place.getCategory()
            )
        );
    }
}
