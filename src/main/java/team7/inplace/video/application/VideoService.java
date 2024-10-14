package team7.inplace.video.application;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.PlaceErrorCode;
import team7.inplace.global.exception.code.VideoErrorCode;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.application.command.VideoCommand.Create;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;
import team7.inplace.video.presentation.dto.VideoSearchParams;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final PlaceRepository placeRepository;
    private final InfluencerRepository influencerRepository;

    public Page<VideoInfo> getVideosBySurround(VideoSearchParams videoSearchParams,
        Pageable pageable) {
        // Place 엔티티 조회
        Page<Place> places = placeRepository.findPlacesByDistanceAndFilters(
            videoSearchParams.topLeftLongitude(),
            videoSearchParams.topLeftLatitude(),
            videoSearchParams.bottomRightLongitude(),
            videoSearchParams.bottomRightLatitude(),
            videoSearchParams.longitude(),
            videoSearchParams.latitude(),
            new ArrayList<>(),
            new ArrayList<>(),
            pageable
        );
        // 조회된 엔티티가 비어있는지 아닌지 확인
        if (places.isEmpty()) {
            throw InplaceException.of(PlaceErrorCode.NOT_FOUND);
        }
        // 장소를 기준으로 비디오 엔티티 조회 ( 장소 별로 가장 최근 비디오 하나 씩 )
        List<Video> videos = new ArrayList<>();
        for (Place place : places.getContent()) {
            videos.add(videoRepository.findTopByPlaceOrderByIdDesc(place)
                .orElseThrow(() -> InplaceException.of(VideoErrorCode.NOT_FOUND)));
        }
        return new PageImpl<>(videos).map(this::videoToInfo);
    }

    public Page<VideoInfo> getAllVideosDesc(Pageable pageable) {
        // id를 기준으로 내림차순 정렬하여 비디오 정보 불러오기
        Page<Video> videos = videoRepository.findAllByOrderByIdDesc(pageable);

        // DTO 형식에 맞게 대입
        return videos.map(this::videoToInfo);
    }

    public Page<VideoInfo> getVideosByMyInfluencer(List<Long> influencerIds, Pageable pageable) {
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

    public void createVideos(List<Create> videoCommands, List<Long> placeIds) {
        var videos = new ArrayList<Video>();
        for (int videoCommandIndex = 0; videoCommandIndex < videoCommands.size();
            videoCommandIndex++) {
            Create videoCommand = videoCommands.get(videoCommandIndex);
            Long placeId = placeIds.get(videoCommandIndex);
            var influencer = influencerRepository.getReferenceById(videoCommand.influencerId());

            if (hasNoPlace(placeId)) {
                videos.add(videoCommand.toEntityFrom(influencer, null));
                continue;
            }
            Place place = placeRepository.getReferenceById(placeId);
            videos.add(videoCommand.toEntityFrom(influencer, place));
        }

        videoRepository.saveAll(videos);
    }

    private boolean hasNoPlace(Long placeId) {
        return placeId == -1;
    }
}
