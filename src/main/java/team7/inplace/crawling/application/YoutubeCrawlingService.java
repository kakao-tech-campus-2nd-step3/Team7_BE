package team7.inplace.crawling.application;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team7.inplace.crawling.client.KakaoMapClient;
import team7.inplace.crawling.client.YoutubeClient;
import team7.inplace.crawling.persistence.YoutubeChannelRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeCrawlingService {
    private final YoutubeChannelRepository youtubeChannelRepository;
    private final YoutubeClient youtubeClient;
    private final KakaoMapClient kakaoMapClient;

    /*
        1. 유튜브 채널 정보를 모두 가져온다.
        2. 마지막 비디오와, 유튜브 UUID를 이용하여 비디오 정보를 가져온다.
        3. 마지막 비디오 UUID를 업데이트 한다.
        4. 카카오 API를 호출해 장소 정보를 가져온다
     */
    public void crawlAllVideos() {
        var youtubeChannels = youtubeChannelRepository.findAll();
        for (var channel : youtubeChannels) {
            var rawVideoInfos = youtubeClient.getVideos(channel.getPlayListUUID(), channel.getLastVideoUUID());
            channel.updateLastVideoUUID(rawVideoInfos.get(0).videoId());

            var videos = rawVideoInfos.stream()
                    .map(rawVideoInfo -> kakaoMapClient.search(rawVideoInfo, channel.getChannelType().getCode()))
                    .filter(Objects::nonNull)
                    .toList();
        }
    }
}

