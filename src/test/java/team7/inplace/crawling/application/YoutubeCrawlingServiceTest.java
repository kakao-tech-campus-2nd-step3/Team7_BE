package team7.inplace.crawling.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import team7.inplace.crawling.client.YoutubeClient;

@SpringBootTest
@ActiveProfiles("test")
class YoutubeCrawlingServiceTest {
    @Autowired
    public YoutubeClient youtubeClient;

    @Test
    @DisplayName("DB에 저장된 모든 비디오 정보를 크롤링 해오는 테스트")
    void test() {
        youtubeClient.getVideos("PLuMuHAJh9g_Py_PSm8gmHdlcil6CQ9QCM", "sGeuize0CAA");
    }
}