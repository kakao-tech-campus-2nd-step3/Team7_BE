package team7.inplace.VideoTest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.influencer.repository.InfluencerRepository;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;
import team7.inplace.video.application.VideoService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private InfluencerRepository influencerRepository;
    @InjectMocks
    private VideoService videoService;

    @Test
    @DisplayName("findByInfluencer Test")
    void test1() {
        // given
        ArgumentCaptor<List<String>> captor_s = ArgumentCaptor.forClass((Class) List.class);
        ArgumentCaptor<List<Long>> captor_i = ArgumentCaptor.forClass((Class) List.class);

        Place place = Place.builder()
                .name("Test Place")
                .pet(false)
                .wifi(true)
                .parking(false)
                .fordisabled(true)
                .nursery(false)
                .smokingroom(false)
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .menuImgUrl("menu.jpg")
                .category(Category.CAFE)
                .longitude("127.0")
                .latitude("37.0")
                .build();
        Influencer influencer = new Influencer("성시경", "가수", "imgUrl");
        Video video = new Video("url", influencer, place);

        // 함수 매개변수
        List<String> names = new ArrayList<>();
        names.add("성시경");

        // influencerRepository.findByNameIn의 결과
        List<Influencer> influencers = new ArrayList<>();
        influencers.add(influencer);
        given(influencerRepository.findByNameIn(captor_s.capture())).willAnswer(invocation -> influencers);

        // videoRepository.findVideosByInfluencerIdIn의 결과
        List<Video> savedVideos = new ArrayList<>();
        savedVideos.add(video);
        given(videoRepository.findVideosByInfluencerIdIn(captor_i.capture())).willAnswer(invocation -> savedVideos);

        // when
        List<VideoInfo> savedVideoData = videoService.findByInfluencer(names);
        // then
        Assertions.assertThat(savedVideoData.get(0).place().placeName()).isEqualTo(place.getName());
        Assertions.assertThat(savedVideoData.get(0).videoUrl()).isEqualTo("url");
        // 여러 번 실행 시 Alias 부분이 바뀌는지 검사 ( 확인 완료 )
        System.out.println("videoAlias(" + ") = " + savedVideoData.get(0).videoAlias());
    }
}
