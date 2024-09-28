package team7.inplace.video.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.influencer.repository.InfluencerRepository;
import team7.inplace.place.domain.Place;
import team7.inplace.place.dto.PlaceForVideo;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final InfluencerRepository influencerRepository;

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
        List<Video> savedVideos = videoRepository.findAllByOrderById();

        // DTO 형식에 맞게 대입
        return videoToInfo(savedVideos);
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
                            PlaceForVideo.of(place.getPlaceId(), place.getName())
                    )
            );
        }
        return videoInfos;
    }
}
