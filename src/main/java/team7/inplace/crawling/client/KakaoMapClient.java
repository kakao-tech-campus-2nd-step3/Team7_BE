package team7.inplace.crawling.client;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team7.inplace.crawling.client.dto.RawPlace;
import team7.inplace.crawling.client.dto.RawVideoInfo;
import team7.inplace.global.kakao.config.KakaoApiProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoMapClient {
    private static final String KAKAO_MAP_LOCATE_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private static final String KAKAO_MAP_LOCATE_SEARCH_PARAMS = "?query=%s&sort=accuracy&page=1&size=15";
    private static final String KAKAO_MAP_PLACE_SEARCH_URL = "https://place.map.kakao.com/main/v/";
    private final KakaoApiProperties kakaoApiProperties;
    private final RestTemplate restTemplate;

    public RawPlace.Info search(RawVideoInfo videoInfo, String category) {
        var address = videoInfo.address();
        var locationInfo = getLocateInfo(address, category);
        var placeId = locationInfo.has("documents") ?
                locationInfo.get("documents").get(0).get("id").asText() : null;
        if (Objects.isNull(placeId)) {
            return null;
        }

        var placeInfo = getPlaceInfo(placeId);
        return RawPlace.Info.from(locationInfo, placeInfo);
    }

    private JsonNode getLocateInfo(String address, String category) {
        var url = KAKAO_MAP_LOCATE_SEARCH_URL + KAKAO_MAP_LOCATE_SEARCH_PARAMS.formatted(address);
        url = url + (Objects.isNull(category) ? "" : "&category_group_code=" + category);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", kakaoApiProperties.getAuthorization());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        return response.getBody();
    }

    private JsonNode getPlaceInfo(String placeId) {
        var url = KAKAO_MAP_PLACE_SEARCH_URL + placeId;

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        return response.getBody();
    }
}
