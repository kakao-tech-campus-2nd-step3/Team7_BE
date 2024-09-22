package team7.inplace.video.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.inplace.place.domain.Category;
import team7.inplace.place.dto.PlaceForVideo;
import team7.inplace.video.dto.VideoData;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.video.entity.Video;
import team7.inplace.influencer.repository.InfluencerRepository;
import team7.inplace.video.repository.VideoRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VideoService {
    private static Map<Category, List<String>> template = new HashMap<>();
    private final VideoRepository videoRepository;
    private final InfluencerRepository influencerRepository;

    // 더 나은 로직이 존재하면 건의 부탁합니다
    static {
        template.put(Category.CAFE, Arrays.asList(
                "이(가) 방문한 카페에서 따뜻한 커피를 즐겨보세요!",
                "이(가) 방문했던 카페에서 여유로운 시간을 보내보세요!",
                "이(가) 추천하는 사진 명소 예쁜 카페에 방문해보세요!"
        ));

        template.put(Category.WESTERN, Arrays.asList(
                "이(가) 추천! 맛있는 서양 음식을 즐길 수 있는 곳!",
                "이(가) 추천하는 고급스러운 서양 요리를 맛볼 수 있습니다.",
                "이(가) 극찬! 서양식 디저트를 꼭 한 번 시도해보세요!"
        ));

        template.put(Category.JAPANESE, Arrays.asList(
                "피셜, 현지와 다름없는 정통 일본 요리 레스토랑.",
                "이(가) 추천하는 맛있는 일본 초밥과 라멘을 즐겨보세요.",
                "이(가) 극찬! 일본 요리의 정수를 느껴보세요."
        ));

        template.put(Category.KOREAN, Arrays.asList(
                "이(가) 전통 한식을 맛볼 수 있는 한식당입니다.",
                "이(가) 추천하는 가게에서 정성스럽게 준비된 한식으로 든든한 한 끼를!",
                "이(가) 극찬! 한식의 깊은 맛을 느껴보세요."
        ));
    }

    public List<VideoData> findByInfluencer(List<String> influencers){
        // 인플루언서 정보 처리
        List<Long> influencerIds = influencerRepository.findByNameIn(influencers).stream()
                .map(Influencer::getId)
                .toList();

        // 인플루언서 정보로 필터링한 비디오 정보 불러오기
        List<Video> savedVideos = videoRepository.findVideosByInfluencerIdIn(influencerIds);

        // 변수명 변경 가능
        List<VideoData> returnValue = new ArrayList<>();

        // DTO 형식에 맞게 대입
        for (Video savedVideo : savedVideos) {
            PlaceForVideo placeForVideo = new PlaceForVideo(savedVideo.getPlace().getPlaceId(), savedVideo.getPlace().getName());
            String alias = makeAlias(savedVideo.getInfluencer().getName(), savedVideo.getPlace().getCategory());
            returnValue.add(new VideoData(savedVideo.getId(), alias, savedVideo.getVideoUrl(), placeForVideo));
        }

        return returnValue;
    }

    private String makeAlias(String influencerName, Category category){
        List<String> syntax = template.get(category);

        Random random = new Random();
        int randomNumber = random.nextInt(syntax.size());
        return influencerName + " " + syntax.get(randomNumber);
    }
}
