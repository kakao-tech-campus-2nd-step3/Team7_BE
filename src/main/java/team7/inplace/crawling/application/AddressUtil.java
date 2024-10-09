package team7.inplace.crawling.application;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class AddressUtil {
    private static final String ADDRESS_REGEX = "[가-힣0-9]+(?:도|시|구|군|읍|면|동|리|로|길)[^#,\\n()]+(?:동|읍|면|리|로|길|호|층|번지)[^#,\\n()]+";

    public static String extractAddress(JsonNode snippet) {

        String videoDescription = snippet.path("description").asText();

        Pattern pattern = Pattern.compile(ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(videoDescription);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
