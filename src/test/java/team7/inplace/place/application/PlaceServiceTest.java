package team7.inplace.place.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.PlaceDetailInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private PlaceRepository placeRepository;
    @InjectMocks
    private PlaceService placeService;

    private Place place1, place2, place3, place4;
    private Video video1, video2, video3;
    private Influencer influencer1, influencer2;

//     * 테스트 Place 좌표 (longitude, latitude)
//     * (10.0, 10.0) -> video1 -> 성시경
//     * (10.0, 50.0)
//     * (10.0, 100.0)
//     * (50.0, 50.0) -> video2 -> 아이유
//     *
//     * 테스트 좌표
//     * (10.0, 51.0)
//     *
//     * boundary 좌표
//     * 좌상단: (10.0, 60.0)
//     * 우하단: (50.0, 10.0)
//     *

    String topLeftLongitude = "10.0";
    String topLeftLatitude = "60.0";
    String bottomRightLongitude = "50.0";
    String bottomRightLatitude = "10.0";
    String longitude = "10.0";
    String latitude = "51.0";
    Pageable pageable = PageRequest.of(0, 10);
    PlacesCoordinateCommand coordinateCommand = new PlacesCoordinateCommand(
        topLeftLongitude,
        topLeftLatitude,
        bottomRightLongitude,
        bottomRightLatitude,
        longitude,
        latitude,
        pageable
    );

    @BeforeEach
    public void init() throws NoSuchFieldException, IllegalAccessException {
        Field placeIdField = Place.class.getDeclaredField("id");
        placeIdField.setAccessible(true);

        place1 = new Place("Place 1",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", "카페",
            "Address 1|Address 2|Address 3",
            "10.0", "10.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false|menu.url|description",
                "돼지찌개|7000|true|menu.url|description"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );

        place2 = new Place("Place 2",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", "일식",
            "Address 1|Address 2|Address 3",
            "10.0", "50.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false|menu.url|description",
                "돼지찌개|7000|true|menu.url|description"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );

        place3 = new Place("Place 3",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", "카페",
            "Address 1|Address 2|Address 3",
            "10.0", "100.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false|menu.url|description",
                "돼지찌개|7000|true|menu.url|description"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );

        place4 = new Place("Place 4",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", "일식",
            "Address 1|Address 2|Address 3",
            "50.0", "50.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false|menu.url|description",
                "돼지찌개|7000|true|menu.url|description"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );
        placeIdField.set(place1, 1L);
        placeIdField.set(place2, 2L);
        placeIdField.set(place3, 3L);
        placeIdField.set(place4, 4L);
        placeIdField.setAccessible(true);

        Field influencerIdField = Influencer.class.getDeclaredField("id");
        influencerIdField.setAccessible(true);
        influencer1 = new Influencer("성시경", "가수", "img.url");
        influencerIdField.set(influencer1, 1L);
        influencer2 = new Influencer("아이유", "가수", "img.rul");
        influencerIdField.set(influencer2, 2L);

        video1 = Video.from(influencer1, place1, "video1.url");
        video2 = Video.from(influencer2, place4, "video2.url");
        video3 = Video.from(influencer1, place4, "video3.url");
    }

    @Test
    @DisplayName("필터링 없이 가까운 장소 조회")
    public void test1() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3);
        when(
            placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(), any(), any(),
                any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2, video3));
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(null, null);

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo(null);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(2).influencerName()).isEqualTo("성시경");
    }

    @Test
    @DisplayName("카테고리 필터링(일식, 카페)")
    public void test2() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3);
        when(
            placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(), any(), any(),
                any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2, video3));

        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("일식, 카페",
            null);

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(0).category()).isEqualTo(
            Category.JAPANESE.name());
        assertThat(result.getContent().get(0).influencerName()).isEqualTo(null);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(1).category()).isEqualTo(
            Category.JAPANESE.name());
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(2).category()).isEqualTo(Category.CAFE.name());
        assertThat(result.getContent().get(2).influencerName()).isEqualTo("성시경");
    }

    @Test
    @DisplayName("카테고리 필터링(일식)")
    public void test3() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4), pageable, 2);
        when(placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(), any(),
            any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2, video3));
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("일식",
            null);

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(0).category()).isEqualTo(
            Category.JAPANESE.toString());
        assertThat(result.getContent().get(0).influencerName()).isEqualTo(null);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(1).category()).isEqualTo(
            Category.JAPANESE.toString());
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("아이유");
    }

    @Test
    @DisplayName("인플루언서(아이유, 성시경) 필터링")
    public void test4() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4, place1), pageable, 1);
        when(
            placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(), any(),
                any(),
                any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2, video3));
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(null,
            "성시경, 아이유");

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("성시경");
    }

    @Test
    @DisplayName("인플루언서(아이유) 필터링")
    public void test5() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4), pageable, 1);
        when(placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(),
            any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2, video3));

        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(null,
            "아이유");

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("아이유");
    }

    @Test
    @DisplayName("카테고리(Japanese) + 인플루언서(아이유, 성시경) 필터링")
    public void test6() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4), pageable, 1);
        when(placeRepository.findPlacesByDistanceAndFilters(any(), any(), any(), any(),
            any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2, video3));

        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(
            "JAPANESE", "아이유");

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("아이유");
    }

    @Test
    @DisplayName("장소 세부정보 조회")
    public void test7() {
        // given
//        Place place = video1.getPlace();
//        Influencer influencer = video1.getInfluencer();
        PlaceDetailInfo expected = PlaceDetailInfo.from(place4,
            influencer2, video2);

        when(placeRepository.findById(place4.getId()))
            .thenReturn(Optional.of(place4));
        when(videoRepository.findByPlaceId(place4.getId()))
            .thenReturn(Arrays.asList(video2, video3));

        // when
        PlaceDetailInfo result = placeService.getPlaceDetailInfo(4L);

        // then
        // menuInfos의 timeExp는 실시간으로 바껴서 테스트에서 제외함
        assertThat(result).isEqualToIgnoringGivenFields(expected, "menuInfos");
        assertThat(result.menuInfos().menuList()).isEqualTo(
            expected.menuInfos().menuList());
        assertThat(result.placeInfo().influencerName()).isEqualTo(
            expected.placeInfo().influencerName());
    }

}
