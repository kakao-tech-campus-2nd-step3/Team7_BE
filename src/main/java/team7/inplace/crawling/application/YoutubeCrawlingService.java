package team7.inplace.crawling.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team7.inplace.crawling.application.dto.RawVideoInfo;
import team7.inplace.crawling.client.YoutubeClient;
import team7.inplace.crawling.repository.YoutubeChannelRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeCrawlingService {
    private final YoutubeChannelRepository youtubeChannelRepository;
    private final YoutubeClient youtubeClient;

    /*
        1. 유튜브 채널 정보를 모두 가져온다.
        2. 마지막 비디오와, 유튜브 UUID를 이용하여 비디오 정보를 가져온다.
        3. 마지막 비디오 UUID를 업데이트 한다.
        4. 카카오 API를 호출해 장소 정보를 가져온다
     */
    public void crawlAllVideos() {
        var youtubeChannels = youtubeChannelRepository.findAll();
        List<RawVideoInfo> rawVideoInfos = new ArrayList<>();
        for (var channel : youtubeChannels) {
            var videos = youtubeClient.getVideos(channel.getPlayListUUID(), channel.getLastVideoUUID());
            rawVideoInfos.addAll(videos);
            channel.updateLastVideoUUID(videos.get(0).videoId());
        }
    }
}

