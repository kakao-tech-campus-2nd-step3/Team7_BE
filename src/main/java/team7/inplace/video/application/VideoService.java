package team7.inplace.video.application;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.security.util.AuthorizationUtil;
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
            videos.add(videoRepository.findTopByPlaceOrderByIdDesc(place).orElseThrow(NoSuchFieldError::new));
        }
        return new PageImpl<>(videos).map(this::videoToInfo);
    }

    public Page<VideoInfo> getAllVideosDesc(Pageable pageable) {
        // id를 기준으로 내림차순 정렬하여 비디오 정보 불러오기
        Page<Video> videos = videoRepository.findAllByOrderByIdDesc(pageable);

        // DTO 형식에 맞게 대입
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
