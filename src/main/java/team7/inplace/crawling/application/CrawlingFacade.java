package team7.inplace.crawling.application;

import lombok.RequiredArgsConstructor;
import team7.inplace.global.annotation.Facade;

@Facade
@RequiredArgsConstructor
public class CrawlingFacade {
    private final YoutubeCrawlingService youtubeCrawlingService;

    public void updateVideos() {
        var crawlingInfos = youtubeCrawlingService.crawlAllVideos();
        for (var crawlingInfo : crawlingInfos) {
            var videoCommands = crawlingInfo.toVideoCommands();
            var placesCommands = crawlingInfo.toPlacesCommands();
        }
    }
}
