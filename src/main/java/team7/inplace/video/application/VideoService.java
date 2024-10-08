package team7.inplace.video.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.global.exception.code.VideoErrorCode;
import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.application.UserService;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;
import team7.inplace.video.presentation.dto.VideoSearchParams;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlaceRepository placeRepository;
    private final UserService userService;

    public Page<VideoInfo> getVideosBySurround(VideoSearchParams videoSearchParams, Pageable pageable) {
        Page<Place> places = placeRepository.getPlacesByDistance(
                videoSearchParams.longitude(),
                videoSearchParams.latitude(),
                pageable
        );

        List<Video> videos = new ArrayList<>();
        for (Place place : places.getContent()) {
            if (videos.size() == places.getSize())
                break;
            videos.add(videoRepository.findTopByPlaceOrderByIdDesc(place)
                    .orElseThrow(()-> InplaceException.of(VideoErrorCode.NO_SUCH_VIDEO)));
        }
        return new PageImpl<>(videos).map(this::videoToInfo);
    }

    public Page<VideoInfo> getAllVideosDesc(Pageable pageable) {
        // id를 기준으로 내림차순 정렬하여 비디오 정보 불러오기
        Page<Video> videos = videoRepository.findAllByOrderByIdDesc(pageable);

        // DTO 형식에 맞게 대입
        return videos.map(this::videoToInfo);
    }

    public Page<VideoInfo> getVideosByMyInfluencer(Pageable pageable){
        // User 정보를 쿠키에서 추출
        String username = AuthorizationUtil.getUsername();
        // 토큰 정보에 대한 검증
        if(!StringUtils.hasText(username) || username == null) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }
        // 유저 정보를 이용하여 유저가 좋아요를 누른 인플루언서 id 리스트를 조회
        List<Long> influencerIds = userService.getInfluencerIdsByUsername(username);
        // 인플루언서 id 리스트를 이용하여 해당 인플루언서의 비디오들을 조회
        Page<Video> videos = videoRepository.findVideosByInfluencerIdIn(influencerIds, pageable);
        return videos.map(this::videoToInfo);
    }

    private VideoInfo videoToInfo(Video savedVideo) {
        Place place = savedVideo.getPlace();
        String alias = AliasUtil.makeAlias(
                savedVideo.getInfluencer().getName(),
                place.getCategory()
        );
        return new VideoInfo(
                savedVideo.getId(),
                alias,
                savedVideo.getVideoUrl(),
                PlaceForVideo.of(place.getId(), place.getName())
        );
    }
}
