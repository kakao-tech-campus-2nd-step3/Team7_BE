package team7.inplace.crawling.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import team7.inplace.crawling.client.dto.PlaceNode;

public record CrawlingInfo(
        Long influencerId,
        List<JsonNode> videoSnippets,
        List<PlaceNode> placeNodes
) {
}
