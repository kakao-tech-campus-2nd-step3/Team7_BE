package team7.inplace.video.application.command;

import com.fasterxml.jackson.databind.JsonNode;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

public class VideoCommand {
    public record Create(
            Long influencerId,
            String videoId,
            String videoTitle
    ) {
        public static Create from(JsonNode snippet, Long influencerId) {
            String videoId = snippet.path("resourceId").path("videoId").asText();
            String videoTitle = snippet.path("title").asText();

            return new Create(influencerId, videoId, videoTitle);
        }

        public Video toEntityFrom(Influencer influencer, Place place) {
            return Video.from(influencer, place, videoId());
        }
    }
}