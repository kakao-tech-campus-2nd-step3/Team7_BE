package team7.inplace.video.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.global.annotation.Facade;
import team7.inplace.place.application.PlaceService;
import team7.inplace.place.application.command.PlacesCommand;
import team7.inplace.video.application.command.VideoCommand;

@Facade
@RequiredArgsConstructor
public class VideoFacade {
    private final VideoService videoService;
    private final PlaceService placeService;

    @Transactional
    public void createVideos(List<VideoCommand.Create> videoCommands, List<PlacesCommand.Create> placeCommands) {

        var placeIds = placeService.createPlaces(placeCommands);
        videoService.createVideos(videoCommands, placeIds);
    }
}
