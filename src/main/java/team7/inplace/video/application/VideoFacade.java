package team7.inplace.video.application;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.crawling.application.YoutubeCrawlingService;
import team7.inplace.global.annotation.Facade;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.place.application.PlaceService;
import team7.inplace.place.application.command.PlacesCommand;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.application.UserService;
import team7.inplace.video.application.command.VideoCommand;
import team7.inplace.video.application.dto.VideoInfo;

@Facade
@RequiredArgsConstructor
public class VideoFacade {
    private final VideoService videoService;
    private final PlaceService placeService;
    private final YoutubeCrawlingService youtubeCrawlingService;
    private final UserService userService;

    @Transactional
    public void createVideos(List<VideoCommand.Create> videoCommands, List<PlacesCommand.Create> placeCommands) {

        var placeIds = placeService.createPlaces(placeCommands);
        youtubeCrawlingService.updateLastVideoUUID(videoCommands.get(0).influencerId(), videoCommands.get(0).videoId());
        videoService.createVideos(videoCommands, placeIds);
    }

    public Page<VideoInfo> getVideosByMyInfluencer(Pageable pageable) {
        // User 정보를 쿠키에서 추출
        Long userId = AuthorizationUtil.getUserId();
        // 토큰 정보에 대한 검증
        if (Objects.isNull(userId)) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }
        // 유저 정보를 이용하여 유저가 좋아요를 누른 인플루언서 id 리스트를 조회
        List<Long> influencerIds = userService.getInfluencerIdsByUsername(userId);
        // 인플루언서 id를 사용하여 영상을 조회
        return videoService.getVideosByMyInfluencer(influencerIds, pageable);
    }
}
