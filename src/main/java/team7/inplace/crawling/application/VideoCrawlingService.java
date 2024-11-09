package team7.inplace.crawling.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.crawling.application.dto.CrawlingInfo;
import team7.inplace.crawling.client.YoutubeClient;
import team7.inplace.video.persistence.VideoRepository;

@Service
@RequiredArgsConstructor
public class VideoCrawlingService {
    private final VideoRepository videoRepository;
    private final YoutubeClient youtubeClient;

    @Transactional(readOnly = true)
    public List<CrawlingInfo.ViewInfo> crawlingVideoView() {
        var videos = videoRepository.findAll();

        var videoInfos = videos.stream().map(video -> {
            var videoId = video.getId();
            var videoUUID = video.getVideoUUID();
            var videoDetail = youtubeClient.getVideoDetail(videoUUID);
            return CrawlingInfo.ViewInfo.of(videoId, videoDetail);
        }).toList();
        return videoInfos;
    }
}
