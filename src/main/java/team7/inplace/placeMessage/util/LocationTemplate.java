package team7.inplace.placeMessage.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import team7.inplace.placeMessage.application.command.PlaceMessageCommand;

public record LocationTemplate(
    @NonNull @JsonProperty("object_type") String objectType,
    @NonNull String address,
    @Nullable @JsonProperty("address_title") String addressTitle,
    @NonNull Content content,
    @Nullable @JsonProperty("buttons") List<Button> buttons
) {

    private final static String BUTTON_NAME = "자세히 보기";
    private final static String OBJECT_TYPE = "location";

    public static LocationTemplate of(String frontEndUrl, PlaceMessageCommand placeMessageCommand) {
        Link link = Link.of(frontEndUrl + "/detail/" + placeMessageCommand.placeId());
        return new LocationTemplate(
            OBJECT_TYPE,
            placeMessageCommand.address(),
            placeMessageCommand.title(),
            Content.of(placeMessageCommand, placeMessageCommand.description(), link),
            List.of(Button.of(BUTTON_NAME, link))
        );
    }
}
