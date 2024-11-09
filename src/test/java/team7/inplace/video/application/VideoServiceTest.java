package team7.inplace.video.application;

import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.application.dto.PlaceForVideo;
import team7.inplace.place.domain.*;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.util.TestUtil;
import team7.inplace.video.application.dto.VideoInfo;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;
import team7.inplace.video.presentation.dto.VideoSearchParams;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private InfluencerRepository influencerRepository;
    @InjectMocks
    private VideoService videoService;

    @Test
    @DisplayName("getVideosBySurround Test")
    void test1(){
        // given
        // 매개변수
        VideoSearchParams videoSearchParams = new VideoSearchParams(
                "10.0",
                "60.0",
                "50.0",
                "10.0",
                "10.0",
                "51.0"
        );
        Pageable pageable = PageRequest.of(0, 10);

        // Place 객체
        Place place1 = new Place("Place 1",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "카페",
                "Address 1|Address 2|Address 3",
                "10.0", "10.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );
        Place place2 = new Place("Place 2",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "일식",
                "Address 1|Address 2|Address 3",
                "10.0", "50.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );
        // method 1 실행 결과 값 설정
        List<Place> placeList = List.of(place1, place2);
        Page<Place> places = new PageImpl<>(placeList, pageable, 2);
        // method 1 mock 설정
        given(placeRepository.findPlacesByDistanceAndFilters(
                videoSearchParams.topLeftLongitude(),
                videoSearchParams.topLeftLatitude(),
                videoSearchParams.bottomRightLongitude(),
                videoSearchParams.bottomRightLatitude(),
                videoSearchParams.longitude(),
                videoSearchParams.latitude(),
                new ArrayList<>(),
                new ArrayList<>(),
                pageable)
        ).willAnswer(invocation -> places);

        // method 2 실행 결과 값 설정
        Video video1 = Video.from(new Influencer("성시경", "url1", "가수"), place1, "url_1");
        Video video2 = Video.from(new Influencer("성시경", "url2", "가수"), place2, "url_2");
        // method 2 mock 설정
        ArgumentCaptor<Place> captor_pla = ArgumentCaptor.forClass(Place.class);
        given(videoRepository.findTopByPlaceOrderByIdDesc(captor_pla.capture())).willAnswer(
                invocation -> {
                    if(captor_pla.getValue() == place1)
                        return Optional.of(video1);
                    return Optional.of(video2);
                }
        );
        // when
        Page<VideoInfo> videoInfos = videoService.getVideosBySurround(videoSearchParams, pageable);
        // then
        Assertions.assertThat(videoInfos.getContent().get(0).place().placeName()).isEqualTo("Place 1");
        Assertions.assertThat(videoInfos.getContent().get(1).place().placeName()).isEqualTo("Place 2");
    }

    @Test
    @DisplayName("getAllVideosDesc Test")
    void test2(){
        // Place 객체
        Place place1 = new Place("Place 1",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "카페",
                "Address 1|Address 2|Address 3",
                "10.0", "10.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );

        // Video 객체
        Video video1 = Video.from(new Influencer("성시경", "url", "가수"), place1, "url1");
        TestUtil.setId(video1, 1L);

        Video video2 = Video.from(new Influencer("성시경", "url", "가수"), place1, "url2");
        TestUtil.setId(video2, 2L);

        // method 1 실행 결과 값 설정
        Pageable pageable = PageRequest.of(0, 10);
        List<Video> videoList = Arrays.asList(video2, video1);
        Page<Video> videos = new PageImpl<>(videoList, pageable, 2);

        given(videoRepository.findAllByOrderByIdDesc(pageable)).willAnswer(
                invocation -> videos
        );
        // when
        Page<VideoInfo> videoInfos = videoService.getAllVideosDesc(pageable);
        // then
        Assertions.assertThat(videoInfos.getContent().get(0).videoId()).isEqualTo(2L);
        Assertions.assertThat(videoInfos.getContent().get(1).videoId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getVideosByMyInfluencer Test")
    void test3() {
        // given
        // Place 객체
        Place place1 = new Place("Place 1",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "카페",
                "Address 1|Address 2|Address 3",
                "10.0", "10.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );

        // method 1 실행 결과 값 설정
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Influencer> influencers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Influencer influencer = new Influencer(i + "번", "url" + i, "가수");
            influencers.add(influencer);
            TestUtil.setId(influencer, (long) i);
        }

        Video video1 = Video.from(influencers.get(0), place1, "url_1");
        Video video2 = Video.from(influencers.get(1), place1, "url_2");
        Video video3 = Video.from(influencers.get(1), place1, "url_3");
        Video video4 = Video.from(influencers.get(2), place1, "url_4");
        List<Video> videoList = Arrays.asList(video1, video2, video3);
        Page<Video> videos = new PageImpl<>(videoList, pageable, 3);

        // method 1 설정
        given(videoRepository.findVideosByInfluencerIdIn(ids, pageable)).willAnswer(
                invocation -> videos
        );

        // when
        Page<VideoInfo> videoInfos = videoService.getVideosByMyInfluencer(ids, pageable);

        // then
        Assertions.assertThat(videoInfos.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("getPlaceNullVideo Test")
    void test4(){
        // Place 객체
        Place place1 = new Place("Place 1",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "카페",
                "Address 1|Address 2|Address 3",
                "10.0", "10.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );

        // Influencer 객체
        Influencer influencer = new Influencer("성시경", "url1", "가수");

        // method 1 실행 결과 설정
        Pageable pageable = PageRequest.of(0, 10);

        Video video1 = Video.from(influencer, place1, "url_1");
        Video video2 = Video.from(influencer, null, "url_2");
        Video video3 = Video.from(influencer, place1, "url_3");
        Video video4 = Video.from(influencer, null, "url_4");
        List<Video> videoList = Arrays.asList(video2, video4);
        Page<Video> videos = new PageImpl<>(videoList, pageable, 3);

        // method 1 설정
        given(videoRepository.findAllByPlaceIsNull(pageable)).willAnswer(
                invocation -> videos
        );

        // when
        Page<VideoInfo> videoInfos = videoService.getPlaceNullVideo(pageable);

        //
        Assertions.assertThat(videoInfos.getContent().size()).isEqualTo(2);
    }
}
