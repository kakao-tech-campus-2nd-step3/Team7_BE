package team7.inplace.video.application.command;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoCommand {
    public record Create(
            String videoId,
            String videoTitle,
            String address
    ) {
        private static final String ADDRESS_REGEX = "[가-힣0-9]+(?:도|시|구|군|읍|면|동|리|로|길)[^#,\\n()]+(?:동|읍|면|리|로|길|호|층|번지)[^#,\\n()]+";

        public static Create from(JsonNode snippet) {
            String videoId = snippet.path("resourceId").path("videoId").asText();
            String videoTitle = snippet.path("title").asText();
            String videoDescription = snippet.path("description").asText();
            String address = extractAddress(videoDescription);

            return new Create(videoId, videoTitle, address);
        }

        private static String extractAddress(String description) {
            Pattern pattern = Pattern.compile(ADDRESS_REGEX);
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                return matcher.group();
            }
            return null;
        }
    }
}