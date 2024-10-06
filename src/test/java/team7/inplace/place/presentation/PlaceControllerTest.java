package team7.inplace.place.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.application.CategoryService;
import team7.inplace.place.application.PlaceService;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Coordinate;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceCloseTime;
import team7.inplace.place.domain.PlaceOpenTime;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.video.domain.Video;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Autowired
    private PlaceService placeService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PlaceController placeController;

    @Mock
    private PlaceRepository placeRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    /*
     * 테스트 Place 좌표 (longitude, latitude)
     * (10.0, 10.0) -> video1 -> 성시경
     * (10.0, 50.0)
     * (10.0, 100.0)
     * (50.0, 50.0) -> video2 -> 아이유
     *
     * 테스트 좌표
     * (10.0, 51.0)
     *
     * boundary 좌표
     * 좌상단: (10.0, 60.0)
     * 우하단: (50.0, 10.0)
     *
     */
    String topLeftLongitude = "10.0";
    String topLeftLatitude = "60.0";
    String bottomRightLongitude = "50.0";
    String bottomRightLatitude = "10.0";
    String longitude = "10.0";
    String latitude = "51.0";
    Pageable pageable = PageRequest.of(0, 10);
    private Place place1, place2, place3, place4;
    private Video video1, video2;
    private Influencer influencer1, influencer2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void init() {
        place1 = Place.builder()
            .id(1L)
            .name("Place 1")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.CAFE)
            .coordinate(new Coordinate("10.0", "10.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        place2 = Place.builder()
            .id(2L)
            .name("Place 2")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("10.0", "50.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        place3 = Place.builder()
            .id(3L)
            .name("Place 3")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.CAFE)
            .coordinate(new Coordinate("10.0", "100.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        place4 = Place.builder()
            .id(4L)
            .name("Place 4")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("50.0", "50.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        influencer1 = new Influencer("성시경", "가수", "img.url");
        influencer2 = new Influencer("아이유", "가수", "img.rul");

        video1 = new Video("video.url", influencer1, place1);
        video2 = new Video("video.url", influencer2, place4);
    }

    @Test
    @DisplayName("카테고리 목록 조회")
    public void testGetCategories() throws Exception {
        // given
        List<CategoryInfo> expectedCategories = Arrays.stream(Category.values())
            .map(category -> new CategoryInfo(category.name()))
            .toList();
        when(categoryService.getCategories()).thenReturn(expectedCategories);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/places/categories")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String jsonResponse = result.getResponse().getContentAsString();
                CategoriesResponse response = objectMapper.readValue(jsonResponse,
                    CategoriesResponse.class);
                assertThat(response.categories()).isEqualTo(expectedCategories);
            });
    }
/*
    @Test
    @DisplayName("위치기반 장소 정보 조회")
    public void getPlacesTest() throws Exception {
        //given
        PlaceInfo info1 = PlaceInfo.of(place1, influencer1.getName());
        PlaceInfo info2 = PlaceInfo.of(place2, null);
        PlaceInfo info3 = PlaceInfo.of(place3, null);
        PlaceInfo info4 = PlaceInfo.of(place4, influencer2.getName());

        Page<PlaceInfo> placeInfos = new PageImpl<>(Arrays.asList(info2, info4, info1), pageable,
            3);
        PlacesResponse expectedResponse = PlacesResponse.of(placeInfos);

        PlacesCoordinateCommand coordinateComm = new PlacesCoordinateCommand(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            pageable);
        PlacesFilterParamsCommand filterParamsComm = new PlacesFilterParamsCommand(null, null);

        when(placeRepository.getPlacesByDistanceAndFilters(
            anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),
            anyList(), anyList(), any(Pageable.class))
        ).thenReturn(new PageImpl<>(Arrays.asList(place2, place4, place1), pageable, 3));

        //when
        mockMvc.perform(MockMvcRequestBuilders
                .get("/places")
                .accept(MediaType.APPLICATION_JSON)
                .param("topLeftLongitude", topLeftLongitude)
                .param("topLeftLatitude", topLeftLatitude)
                .param("bottomRightLongitude", bottomRightLongitude)
                .param("bottomRightLatitude", bottomRightLatitude)
                .param("longitude", longitude)
                .param("latitude", latitude))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String jsonResponse = result.getResponse().getContentAsString();
                PlacesResponse response = objectMapper.readValue(jsonResponse,
                    PlacesResponse.class);
                assertThat(response).isEqualTo(expectedResponse);
            });
        //then
    }
 */

}
