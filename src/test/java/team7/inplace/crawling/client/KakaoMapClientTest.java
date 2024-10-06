package team7.inplace.crawling.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team7.inplace.crawling.client.dto.RawVideoInfo;

@SpringBootTest
@ActiveProfiles("test")
class KakaoMapClientTest {
    @Autowired
    public KakaoMapClient KakaoMapClient;

    @Test
    @DisplayName("카카오 맵 주소 검색 테스트")
    void searchAddressTest() {
        // given
        var address = "대구 북구 대현남로6길 25";
        var rawVideoInfo = new RawVideoInfo("test", "test", address);

        // when
        KakaoMapClient.search(rawVideoInfo, "FD6");
    }
}