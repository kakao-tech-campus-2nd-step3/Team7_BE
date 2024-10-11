package team7.inplace.crawling.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import java.util.Objects;
import team7.inplace.crawling.client.dto.PlaceNode;
import team7.inplace.place.application.command.PlacesCommand;
import team7.inplace.video.application.command.VideoCommand;

public record CrawlingInfo(
        Long influencerId,
        List<JsonNode> videoSnippets,
        List<PlaceNode> placeNodes
) {
    public List<VideoCommand.Create> toVideoCommands() {
        return videoSnippets.stream()
                .map(snippet -> VideoCommand.Create.from(snippet, influencerId))
                .toList();
    }

    public List<PlacesCommand.Create> toPlacesCommands() {
        return placeNodes.stream()
                .map(placeNode -> {
                    if (Objects.isNull(placeNode)) {
                        return null;
                    }
                    return PlacesCommand.Create.from(placeNode.locationNode(), placeNode.placeNode());
                })
                .toList();
    }
}
