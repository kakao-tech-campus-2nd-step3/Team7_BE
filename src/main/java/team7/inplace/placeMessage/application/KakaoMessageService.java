package team7.inplace.placeMessage.application;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import team7.inplace.global.kakao.config.KakaoApiProperties;
import team7.inplace.placeMessage.application.command.PlaceMessageCommand;
import team7.inplace.placeMessage.util.KakaoMessageMaker;

@Service
@RequiredArgsConstructor
public class KakaoMessageService {

    private final KakaoApiProperties kakaoApiProperties;
    private final KakaoMessageMaker kakaoMessageMaker;
    private final WebClient webClient;

    public void sendLocationMessageToMe(String oauthToken,
        PlaceMessageCommand placeMessageCommand) {
        webClient.post()
            .uri(URI.create(kakaoApiProperties.sendMessageToMeUrl()))
            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header("Authorization", "Bearer " + oauthToken)
            .body(BodyInserters.fromFormData(
                kakaoMessageMaker.createLocationTemplate(placeMessageCommand)))
            .retrieve()
            .bodyToMono(String.class)
            .subscribe();
    }

    public void sendFeedMessageToMe(String oauthToken,
        PlaceMessageCommand placeMessageCommand) {
        webClient.post()
            .uri(URI.create(kakaoApiProperties.sendMessageToMeUrl()))
            .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header("Authorization", "Bearer " + oauthToken)
            .body(BodyInserters.fromFormData(
                kakaoMessageMaker.createFeedTemplate(placeMessageCommand)))
            .retrieve()
            .bodyToMono(String.class)
            .subscribe();
    }
}
