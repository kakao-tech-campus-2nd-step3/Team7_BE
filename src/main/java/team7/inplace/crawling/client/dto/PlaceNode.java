package team7.inplace.crawling.client.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record PlaceNode(
        JsonNode locationNode,
        JsonNode placeNode
) {
    public static PlaceNode of(JsonNode locationNode, JsonNode placeNode) {
        return new PlaceNode(locationNode, placeNode);
    }
}
