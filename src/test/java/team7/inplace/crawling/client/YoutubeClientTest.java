package team7.inplace.crawling.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class YoutubeClientTest {
    @Autowired
    public YoutubeClient youtubeClient;

    @Test
    @DisplayName("크롤링 테스트")
    void crawlingTest() {
        final String playlistId = "PLuMuHAJh9g_Py_PSm8gmHdlcil6CQ9QCM";
        final String videoId = null;
        var response = youtubeClient.getVideos(playlistId, videoId);

        // 2024. 10. 2기준 154개
        final int expectedSize = 154;
        assertThat(response.size())
                .isEqualTo(expectedSize);
    }
}