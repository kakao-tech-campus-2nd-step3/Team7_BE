package team7.inplace.placeMessage.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import team7.inplace.placeMessage.application.command.PlaceMessageCommand;

public record Content(
    @NonNull String title,
    @NonNull @JsonProperty("image_url") String imageUrl,
    @Nullable @JsonProperty("image_width") Integer imageWidth,
    @Nullable @JsonProperty("image_height") Integer imageHeight,
    @NonNull String description,
    @NonNull Link link
) {

    private static final Integer DEFAULT_IMAGE_WIDTH = 1280;
    private static final Integer DEFAULT_IMAGE_HEIGHT = 720;

    public static Content of(PlaceMessageCommand placeMessageCommand, String description,
        Link link) {
        return new Content(
            placeMessageCommand.title(),
            placeMessageCommand.imageUrl(),
            DEFAULT_IMAGE_WIDTH,
            DEFAULT_IMAGE_HEIGHT,
            description,
            link);
    }
}
