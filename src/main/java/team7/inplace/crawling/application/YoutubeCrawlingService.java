package team7.inplace.crawling.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        2.
     */
    public void crawlAllVideos() {
        var youtubeChannels = youtubeChannelRepository.findAll();

        var videos = youtubeChannels.stream()
                .map(channel -> youtubeClient.getVideos(channel.getPlayListUUID(), channel.getLastVideoUUID()))
                .toList();
    }
}

