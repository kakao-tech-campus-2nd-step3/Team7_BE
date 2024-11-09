package team7.inplace.place.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.likedPlace.domain.LikedPlace;
import team7.inplace.likedPlace.persistence.LikedPlaceRepository;
import team7.inplace.place.application.command.PlaceLikeCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.PlaceDetailInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.Role;
import team7.inplace.user.domain.User;
import team7.inplace.user.domain.UserType;
import team7.inplace.user.persistence.UserRepository;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikedPlaceRepository likedPlaceRepository;
    @InjectMocks
    private PlaceService placeService;

    private Place place1, place2, place3, place4;
    private Video video1, video2, video3;
    private Influencer influencer1, influencer2;
    private User user1, user2;

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

    private MockedStatic<AuthorizationUtil> authorizationUtil;

    ObjectMapper objectMapper = new ObjectMapper();

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
            LocalDateTime.of(2024, 3, 28, 5, 30),
            Arrays.asList(
                "menuBoard1.url",
                "menuBoard2.url"
            )
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
            LocalDateTime.of(2024, 3, 28, 5, 30),
            Arrays.asList(
                "menuBoard1.url",
                "menuBoard2.url"
            )
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
            LocalDateTime.of(2024, 3, 28, 5, 30),
            Arrays.asList(
                "menuBoard1.url",
                "menuBoard2.url"
            )
        );

        place4 = new Place("Place 4",
            "",
            "menuImg.url", "일식",
            "Address 1|Address 2|Address 3",
            "50.0", "50.0",
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

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        user1 = new User("user1", "pass1", "nick1", UserType.KAKAO, Role.USER);
        userIdField.set(user1, 1L);
        user2 = new User("user2", "pass2", "nick2", UserType.KAKAO, Role.USER);
        userIdField.set(user2, 2L);

        authorizationUtil = mockStatic(AuthorizationUtil.class);
    }

    @AfterEach
    void tearDown() {
        authorizationUtil.close();
    }

    @Test
    @DisplayName("필터링 없이 가까운 장소 조회")
    public void test1() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3);
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        assertThat(result.getContent().get(0).likes()).isEqualTo(false);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("성시경");
        assertThat(result.getContent().get(1).likes()).isEqualTo(false);
    }

    @Test
    @DisplayName("인플루언서(아이유) 필터링")
    public void test5() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4), pageable, 1);
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        given(AuthorizationUtil.getUsername()).willReturn(null);
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
        given(AuthorizationUtil.getUsername()).willReturn(null);

        PlaceDetailInfo expected = PlaceDetailInfo.from(place4,
            influencer2, video2, false);

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
        assertThat(result.placeInfo().likes()).isEqualTo(
            expected.placeInfo().likes());
        assertThat(result.facilityInfo()).isEqualTo(
            objectMapper.createObjectNode().put("message", "NO DATA"));
    }

    @Test
    @DisplayName("user2 로그인, 인플루언서(아이유, 성시경) 필터링")
    public void test8() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4, place1), pageable, 1);
        given(AuthorizationUtil.getUserId()).willReturn(2L);
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

        when(likedPlaceRepository.findByUserIdAndPlaceId(user2.getId(), place4.getId()))
            .thenReturn(Optional.empty());

        // when
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(0).likes()).isEqualTo(false);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("성시경");
        assertThat(result.getContent().get(1).likes()).isEqualTo(false);
    }

    @Test
    @DisplayName("user2 로그인, place4 좋아요, 인플루언서(아이유, 성시경) 필터링")
    public void test9() {
        // given
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place4, place1), pageable, 1);
        given(AuthorizationUtil.getUserId()).willReturn(2L);
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

        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        when(placeRepository.findById(place4.getId())).thenReturn(Optional.of(place4));
        when(likedPlaceRepository.findByUserIdAndPlaceId(user2.getId(), place4.getId()))
            .thenReturn(Optional.empty());
        doAnswer(invocation -> {
            LikedPlace saved = invocation.getArgument(0);
            when(likedPlaceRepository.findByUserIdAndPlaceId(user2.getId(),
                saved.getPlace().getId()))
                .thenReturn(Optional.of(saved));
            return saved;
        }).when(likedPlaceRepository).save(any(LikedPlace.class));

        // when
        PlaceLikeCommand command = new PlaceLikeCommand(place4.getId(), true);
        placeService.likeToPlace(command);

        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 4");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("아이유");
        assertThat(result.getContent().get(0).likes()).isEqualTo(true);
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(1).influencerName()).isEqualTo("성시경");
        assertThat(result.getContent().get(1).likes()).isEqualTo(false);
    }

    @Test
    @DisplayName("user1 로그인, 장소 세부정보 조회")
    public void test10() {
        // given
        given(AuthorizationUtil.getUserId()).willReturn(1L);

        PlaceDetailInfo expected = PlaceDetailInfo.from(place4,
            influencer2, video2, false);

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
        assertThat(result.placeInfo().likes()).isEqualTo(
            expected.placeInfo().likes());
    }

    @Test
    @DisplayName("user1 로그인, place4 좋아요, 장소 세부정보 조회")
    public void test11() {
        // given
        given(AuthorizationUtil.getUserId()).willReturn(user1.getId());

        PlaceDetailInfo expected = PlaceDetailInfo.from(place4,
            influencer2, video2, true);

        when(placeRepository.findById(place4.getId()))
            .thenReturn(Optional.of(place4));
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(videoRepository.findByPlaceId(place4.getId()))
            .thenReturn(Arrays.asList(video2, video3));
        when(likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(), place4.getId()))
            .thenReturn(Optional.empty());
        doAnswer(invocation -> {
            LikedPlace saved = invocation.getArgument(0);
            when(likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(),
                saved.getPlace().getId()))
                .thenReturn(Optional.of(saved));
            return saved;
        }).when(likedPlaceRepository).save(any(LikedPlace.class));

        // when
        PlaceLikeCommand placeLikeCommand = new PlaceLikeCommand(place4.getId(), true);
        placeService.likeToPlace(placeLikeCommand);

        PlaceDetailInfo result = placeService.getPlaceDetailInfo(4L);

        // then
        // menuInfos의 timeExp는 실시간으로 바껴서 테스트에서 제외함
        //assertThat(result).isEqualToIgnoringGivenFields(expected, "menuInfos");
        assertThat(result).isEqualToIgnoringGivenFields(expected, "menuInfos");
        assertThat(result.menuInfos().menuList()).isEqualTo(
            expected.menuInfos().menuList());
        assertThat(result.placeInfo().influencerName()).isEqualTo(
            expected.placeInfo().influencerName());
        assertThat(result.placeInfo().likes()).isEqualTo(
            expected.placeInfo().likes());
    }

    // LikedPlace 테스트
    @Test
    @DisplayName("장소 좋아요 추가 및 업데이트 테스트")
    public void likedTest1() {
        // given
        // 사용자 인증 정보 설정
        given(AuthorizationUtil.getUserId()).willReturn(user1.getId());

        PlaceLikeCommand command = new PlaceLikeCommand(place1.getId(), true);

//        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user1));
//        when(placeRepository.findById(any())).thenReturn(Optional.of(place1));
        when(likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(), place1.getId()))
            .thenReturn(Optional.of(new LikedPlace(user1, place1)));

        // when
        placeService.likeToPlace(command);  // 좋아요 기능 호출

        // then
        verify(likedPlaceRepository, times(1)).save(any(LikedPlace.class));  // likedPlace 저장 확인
        assertThat(
            likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(), place1.getId())
                .get()
                .isLiked())
            .isTrue();  // 좋아요 상태 확인
    }

    @Test
    @DisplayName("좋아요한 장소 취소하기")
    public void likedTest2() {
        // given
        // 사용자 인증 정보 설정
        given(AuthorizationUtil.getUserId()).willReturn(user1.getId());

        PlaceLikeCommand command1 = new PlaceLikeCommand(place1.getId(), true);
        PlaceLikeCommand command2 = new PlaceLikeCommand(place1.getId(), false);

        // 초기 조회 시 LikedPlace 반환 설정
        LikedPlace likedPlace = new LikedPlace(user1, place1);
        likedPlace.updateLike(true); // 초기 상태를 좋아요(true)로 설정

        when(likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(), place1.getId()))
            .thenReturn(Optional.of(likedPlace));

        // save 동작 이후 liked 상태를 업데이트
        doAnswer(invocation -> {
            LikedPlace saved = invocation.getArgument(0);
            likedPlace.updateLike(saved.isLiked());
            return saved;
        }).when(likedPlaceRepository).save(any(LikedPlace.class));

        // when
        // 좋아요 활성화
        placeService.likeToPlace(command1);
        // 좋아요 취소
        placeService.likeToPlace(command2);

        // then
        // 좋아요가 저장되었는지 확인
        verify(likedPlaceRepository, times(2)).save(any(LikedPlace.class));
        // 좋아요 취소 상태 확인
        assertThat(likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(), place1.getId())
            .get()
            .isLiked())
            .isFalse();
    }
}
