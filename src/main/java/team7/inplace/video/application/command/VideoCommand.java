package team7.inplace.video.application.command;

import com.fasterxml.jackson.databind.JsonNode;

public class VideoCommand {
    public record Create(
            String videoId,
            String videoTitle
    ) {
        public static Create from(JsonNode snippet) {
            String videoId = snippet.path("resourceId").path("videoId").asText();
            String videoTitle = snippet.path("title").asText();

            return new Create(videoId, videoTitle);
        }
    }
}