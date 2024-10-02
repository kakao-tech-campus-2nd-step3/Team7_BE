package team7.inplace.crawling.client;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team7.inplace.crawling.application.dto.RawVideoInfo;

@Slf4j
@Component
public class YoutubeClient {
    private static final String PLAY_LIST_ITEMS_BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems";
    private static final String PLAY_LIST_PARAMS = "?part=snippet&playlistId=%s&key=%s&maxResults=50";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;

    public YoutubeClient(@Value("${youtube.api.key}") String apiKey) {
        log.info("Youtube API Key: {}", apiKey);
        this.apiKey = apiKey;
    }

    public List<RawVideoInfo> getVideos(String playListId, String finalVideoUUID) {
        List<RawVideoInfo> videoInfos = new ArrayList<>();
        String nextPageToken = null;
        while (true) {
            String url = PLAY_LIST_ITEMS_BASE_URL + String.format(PLAY_LIST_PARAMS, playListId, apiKey);

            JsonNode response = null;
            if (Objects.nonNull(nextPageToken)) {
                url += "&pageToken=" + nextPageToken;
            }
            try {
                response = restTemplate.getForObject(url, JsonNode.class);
            } catch (Exception e) {
                log.error("Youtube API 호출이 실패했습니다. Youtuber Id {}", playListId);
                log.info(e.getMessage());
                break;
            }
            if (Objects.isNull(response)) {
                log.error("Youtube API Response가 NULL입니다 {}.", playListId);
                break;
            }

            var containsLastVideo = extractRawVideoInfo(videoInfos, response.path("items"), finalVideoUUID);
            if (containsLastVideo) {
                break;
            }
            nextPageToken = response.path("nextPageToken").asText();
            if (isLastPage(nextPageToken)) {
                break;
            }
        }

        return videoInfos;
    }

    private boolean isLastPage(String nextPageToken) {
        return Objects.isNull(nextPageToken) || nextPageToken.isEmpty();
    }

    private boolean extractRawVideoInfo(List<RawVideoInfo> videoInfos, JsonNode items, String finalVideoUUID) {
        for (JsonNode item : items) {
            var snippet = item.path("snippet");
            var videoId = snippet.path("resourceId").path("videoId").asText();
            var videoTitle = snippet.path("title").asText();
            var videoDescription = snippet.path("description").asText();
            if (videoId.equals(finalVideoUUID)) {
                return true;
            }
            videoInfos.add(new RawVideoInfo(videoId, videoTitle, videoDescription));
        }
        return false;
    }
}
