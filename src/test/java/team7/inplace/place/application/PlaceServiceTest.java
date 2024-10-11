package team7.inplace.place.application;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
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
    private Video video1, video2;
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
            "menuImg.url", Category.CAFE.toString(),
            "Address 1|Address 2|Address 3",
            "10.0", "10.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false", "돼지찌개|7000|true"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );
        placeIdField.set(place1, 1L);

        place2 = new Place("Place 2",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", Category.JAPANESE.toString(),
            "Address 1|Address 2|Address 3",
            "10.0", "50.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false", "돼지찌개|7000|true"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );
        placeIdField.set(place2, 2L);

        place3 = new Place("Place 3",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", Category.CAFE.toString(),
            "Address 1|Address 2|Address 3",
            "10.0", "100.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false", "돼지찌개|7000|true"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );
        placeIdField.set(place3, 3L);

        place4 = new Place("Place 4",
            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
            "menuImg.url", Category.JAPANESE.toString(),
            "Address 1|Address 2|Address 3",
            "50.0", "50.0",
            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
            Arrays.asList("삼겹살|5000|false", "돼지찌개|7000|true"),
            LocalDateTime.of(2024, 3, 28, 5, 30)
        );
        placeIdField.set(place4, 4L);

        Field influencerIdField = Place.class.getDeclaredField("id");
        placeIdField.setAccessible(true);
        influencer1 = new Influencer("성시경", "가수", "img.url");
        influencer2 = new Influencer("아이유", "가수", "img.rul");

        video1 = new Video("video.url", influencer1, place1);
        video2 = new Video("video.url", influencer2, place4);
    }
/*
    @Test
    @DisplayName("필터링 없이 가까운 장소 조회")
    public void test1() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2));
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
    @DisplayName("카테고리 필터링(JAPANESE, CAFE)")
    public void test2() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2));

        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("JAPANESE, CAFE",
            null);

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(0).category()).isEqualTo(
            Category.JAPANESE.toString());
        assertThat(result.getContent().get(0).influencerName()).isEqualTo(null);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(1).category()).isEqualTo(
            Category.JAPANESE.toString());
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(2).category()).isEqualTo(Category.CAFE.toString());
        assertThat(result.getContent().get(2).influencerName()).isEqualTo("성시경");
    }

    @Test
    @DisplayName("카테고리 필터링(JAPANESE)")
    public void test3() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4), pageable, 2);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any(),
            any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2));
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("JAPANESE",
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
            placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any(),
                any(),
                any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1, video2));
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
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(),
            any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2));

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
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(),
            any(), any(),
            any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIdIn(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video2));

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
        Place place = video1.getPlace();
        Influencer influencer = video1.getInfluencer();
        PlaceDetailInfo expected = PlaceDetailInfo.of(place,
            influencer.getName(), video1.getVideoUrl());

        when(placeRepository.findById(place.getId()))
            .thenReturn(Optional.of(place1));
        when(videoRepository.findByPlaceId(place.getId()))
            .thenReturn(Optional.of(video1));

        // when
        PlaceDetailInfo result = placeService.getPlaceDetailInfo(1L);

        // then
        // menuInfos의 timeExp는 실시간으로 바껴서 테스트에서 제외함
        assertThat(result).isEqualToIgnoringGivenFields(expected, "menuInfos");
        assertThat(result.menuInfos().menuList()).isEqualTo(
            expected.menuInfos().menuList());
    }
    */
}
