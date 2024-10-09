package team7.inplace.crawling.application;

import lombok.RequiredArgsConstructor;
import team7.inplace.global.annotation.Facade;
import team7.inplace.video.application.VideoFacade;

@Facade
@RequiredArgsConstructor
public class CrawlingFacade {
    private final YoutubeCrawlingService youtubeCrawlingService;
    private final VideoFacade videoFacade;

    public void updateVideos() {
        var crawlingInfos = youtubeCrawlingService.crawlAllVideos();
        for (var crawlingInfo : crawlingInfos) {
            var videoCommands = crawlingInfo.toVideoCommands();
            var placesCommands = crawlingInfo.toPlacesCommands();

            videoFacade.createVideos(videoCommands, placesCommands);
        }
    }
}
