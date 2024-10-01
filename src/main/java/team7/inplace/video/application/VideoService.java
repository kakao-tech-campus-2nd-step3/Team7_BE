package team7.inplace.video.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.domain.Place;
import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.place.persistence.PlaceRepository;
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
    private final InfluencerRepository influencerRepository;
    private final PlaceRepository placeRepository;

    public List<VideoInfo> findByInfluencer(List<String> influencers) {
        // 인플루언서 정보 처리
        List<Long> influencerIds = influencerRepository.findByNameIn(influencers).stream()
                .map(Influencer::getId)
                .toList();

        // 인플루언서 정보로 필터링한 비디오 정보 불러오기
        List<Video> savedVideos = videoRepository.findVideosByInfluencerIdIn(influencerIds);

        // DTO 형식에 맞게 대입
        return videoToInfo(savedVideos);
    }

    public List<VideoInfo> findAllDESC() {
        // id를 기준으로 내림차순 정렬하여 비디오 정보 불러오기
        List<Video> savedVideos = videoRepository.findAllByOrderByIdDesc();

        // DTO 형식에 맞게 대입
        return videoToInfo(savedVideos);
    }

    public List<VideoInfo> findBySurround(VideoSearchParams videoSearchParams, Pageable pageable) {
        Page<Place> placesByDistance = placeRepository.getPlacesByDistance(
                videoSearchParams.longitude(),
                videoSearchParams.latitude(),
                pageable
        );

        List<Place> places = placesByDistance.getContent();
        List<Video> videos = videoRepository.findDistinctByPlaceIn(places);
        return videoToInfo(videos);
    }

    private List<VideoInfo> videoToInfo(List<Video> savedVideos) {
        List<VideoInfo> videoInfos = new ArrayList<>();
        for (Video savedVideo : savedVideos) {
            Place place = savedVideo.getPlace();
            String alias = AliasUtil.makeAlias(
                    savedVideo.getInfluencer().getName(),
                    place.getCategory()
            );
            videoInfos.add(
                    new VideoInfo(
                            savedVideo.getId(),
                            alias,
                            savedVideo.getVideoUrl(),
                            PlaceForVideo.of(place.getId(), place.getName())
                    )
            );
        }
        return videoInfos;
    }
}
