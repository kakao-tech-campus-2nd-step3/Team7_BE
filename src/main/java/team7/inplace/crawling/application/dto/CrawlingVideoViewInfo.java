package team7.inplace.crawling.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import team7.inplace.video.application.command.VideoCommand;

public record CrawlingVideoViewInfo(
        Long videoId,
        JsonNode videoDetail
) {
    public static CrawlingVideoViewInfo of(Long videoId, JsonNode videoDetail) {
        return new CrawlingVideoViewInfo(videoId, videoDetail);
    }

    public VideoCommand.UpdateViewCount toVideoCommand() {
        return VideoCommand.UpdateViewCount.from(videoDetail().path("items").get(0).get("statistics"), videoId());
    }
}