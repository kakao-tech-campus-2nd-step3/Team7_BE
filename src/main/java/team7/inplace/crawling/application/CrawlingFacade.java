package team7.inplace.crawling.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team7.inplace.crawling.application.dto.CrawlingInfo;
import team7.inplace.global.annotation.Facade;
import team7.inplace.place.application.command.PlacesCommand;
import team7.inplace.video.application.VideoFacade;

@Facade
@Slf4j
@RequiredArgsConstructor
public class CrawlingFacade {
    private final YoutubeCrawlingService youtubeCrawlingService;
    private final VideoCrawlingService videoCrawlingService;
    private final KakaoCrawlingService kakaoCrawlingService;
    private final VideoFacade videoFacade;

    //TODO: 스케쥴링 추가 예정
    public void updateVideos() {
        var crawlingInfos = youtubeCrawlingService.crawlAllVideos();
        for (var crawlingInfo : crawlingInfos) {
            var videoCommands = crawlingInfo.toVideoCommands();
            var placesCommands = crawlingInfo.toPlacesCommands();
            if (videoCommands.isEmpty()) {
                continue;
            }
            videoFacade.createVideos(videoCommands, placesCommands, crawlingInfo.playListUUID());
        }
    }

    //TODO: 스케쥴링 추가 예정
    public void updateVideoView() {
        var crawlingInfos = videoCrawlingService.crawlingVideoView();
        var videoCommands = crawlingInfos.stream()
                .map(CrawlingInfo.ViewInfo::toVideoCommand)
                .toList();
        videoFacade.updateVideoViews(videoCommands);
    }

    public void addPlaceInfo(Long videoId, Long placeId) {
        var placeInfo = kakaoCrawlingService.searchPlaceWithPlaceId(placeId);
        var placeCommand = PlacesCommand.Create.from(placeInfo.locationNode(), placeInfo.placeNode());
        videoFacade.addPlaceInfo(videoId, placeCommand);
    }
}
