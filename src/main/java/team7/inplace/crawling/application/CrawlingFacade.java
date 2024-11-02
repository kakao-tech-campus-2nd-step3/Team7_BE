package team7.inplace.crawling.application;

import lombok.RequiredArgsConstructor;
import team7.inplace.crawling.application.dto.CrawlingVideoViewInfo;
import team7.inplace.global.annotation.Facade;
import team7.inplace.video.application.VideoFacade;

@Facade
@RequiredArgsConstructor
public class CrawlingFacade {
    private final YoutubeCrawlingService youtubeCrawlingService;
    private final VideoCrawlingService videoCrawlingService;
    private final VideoFacade videoFacade;

    //TODO: 스케쥴링 추가 예정
    public void updateVideos() {
        var crawlingInfos = youtubeCrawlingService.crawlAllVideos();
        for (var crawlingInfo : crawlingInfos) {
            var videoCommands = crawlingInfo.toVideoCommands();
            var placesCommands = crawlingInfo.toPlacesCommands();

            videoFacade.createVideos(videoCommands, placesCommands);
        }
    }
    
    //TODO: 스케쥴링 추가 예정
    public void updateVideoView() {
        var crawlingInfos = videoCrawlingService.crawlingVideoView();
        var videoCommands = crawlingInfos.stream()
                .map(CrawlingVideoViewInfo::toVideoCommand)
                .toList();
        videoFacade.updateVideoViews(videoCommands);
    }
}
