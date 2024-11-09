package team7.inplace.placeMessage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import team7.inplace.placeMessage.application.command.PlaceMessageCommand;

@Component
@RequiredArgsConstructor
public class KakaoMessageMaker {

    public static final String TEMPLATE_OBJECT = "template_object";

    @Value("${spring.redirect.front-end-url}")
    private String frontEndUrl;
    private final ObjectMapper objectMapper;

    public MultiValueMap<String, String> createLocationTemplate(
        PlaceMessageCommand placeMessageCommand) {
        try {
            LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(TEMPLATE_OBJECT, objectMapper.writeValueAsString(
                LocationTemplate.of(frontEndUrl, placeMessageCommand)));
            return body;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MultiValueMap<String, String> createFeedTemplate(
        PlaceMessageCommand placeMessageCommand) {
        try {
            LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add(TEMPLATE_OBJECT, objectMapper.writeValueAsString(
                FeedTemplate.of(frontEndUrl, placeMessageCommand)));
            return body;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
