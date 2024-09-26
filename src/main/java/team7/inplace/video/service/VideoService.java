package team7.inplace.video.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import team7.inplace.video.DTO.PlaceForVideo;
import team7.inplace.video.DTO.VideoData;
import team7.inplace.video.entity.Influencer;
import team7.inplace.video.entity.Video;
import team7.inplace.video.repository.InfluencerRepository;
import team7.inplace.video.repository.VideoRepository;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final InfluencerRepository influencerRepository;

    public VideoService(VideoRepository videoRepository,
        InfluencerRepository influencerRepository) {
        this.videoRepository = videoRepository;
        this.influencerRepository = influencerRepository;
    }

    public List<VideoData> findByInfluencer(List<String> influencers) {
        // 변수명 변경 가능
        List<VideoData> returnValue = new ArrayList<>();

        // 인플루언서 정보 처리
        List<Influencer> savedInfluencers = influencerRepository.findByNameIn(influencers);
        List<Long> influencerIds = savedInfluencers.stream()
            .map(Influencer::getId)
            .toList();

        // 인플루언서 정보로 필터링한 비디오 정보 불러오기
        List<Video> savedVideos = videoRepository.findVideosByInfluencerIdIn(influencerIds);

        // DTO 형식에 맞게 대입
        for (Video savedVideo : savedVideos) {
            PlaceForVideo placeForVideo = new PlaceForVideo(savedVideo.getPlace().getId(),
                savedVideo.getPlace().getName());
            returnValue.add(
                new VideoData(savedVideo.getId(), " ", savedVideo.getVideoUrl(), placeForVideo));
        }

        return returnValue;
    }
}
