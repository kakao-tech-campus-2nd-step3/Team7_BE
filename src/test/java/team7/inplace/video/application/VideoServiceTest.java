package team7.inplace.video.application;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Coordinate;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceOpenTime;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

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
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.CAFE)
            .coordinate(new Coordinate("127.0", "37.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        Influencer influencer = new Influencer("성시경", "가수", "imgUrl");
        Video video = new Video("url", influencer, place);

        // 함수 매개변수
        List<String> names = new ArrayList<>();
        names.add("성시경");

        // influencerRepository.findByNameIn의 결과
        List<Influencer> influencers = new ArrayList<>();
        influencers.add(influencer);
        given(influencerRepository.findByNameIn(captor_s.capture())).willAnswer(
            invocation -> influencers);

        // videoRepository.findVideosByInfluencerIdIn의 결과
        List<Video> savedVideos = new ArrayList<>();
        savedVideos.add(video);
        given(videoRepository.findVideosByInfluencerIdIn(captor_i.capture())).willAnswer(
            invocation -> savedVideos);

        // when
        List<VideoInfo> savedVideoData = videoService.getByVideosInfluencer(names);
        // then
        Assertions.assertThat(savedVideoData.get(0).place().placeName()).isEqualTo(place.getName());
        Assertions.assertThat(savedVideoData.get(0).videoUrl()).isEqualTo("url");
        // 여러 번 실행 시 Alias 부분이 바뀌는지 검사 ( 확인 완료 )
        System.out.println("videoAlias(" + ") = " + savedVideoData.get(0).videoAlias());
    }
}
