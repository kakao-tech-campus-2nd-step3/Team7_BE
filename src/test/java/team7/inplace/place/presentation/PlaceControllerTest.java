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
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.video.domain.Video;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PlaceControllerTest {

    @Mock
    private PlaceService placeService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PlaceController placeController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Place place1, place2, place3;
    private Video video1;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void init() {
        place1 = Place.builder()
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
            .coordinate(new Coordinate("1.0", "1.0"))
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
            .coordinate(new Coordinate("1.0", "50.0"))
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
            .name("Place 3")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("1.0", "100.0"))
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

        video1 = new Video("video.url", new Influencer("성시경", "가수", "img.url"), place1);

        pageable = PageRequest.of(0, 10);
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
    public void getPlacesTest() {
        //given
        PlaceInfo.of(place1, )
        PlacesCoordinateCommand coordinateComm = new PlacesCoordinateCommand("1.0", "51.0",
            pageable);
        PlacesFilterParamsCommand filterParamsComm = new PlacesFilterParamsCommand(null, null);

        when(placeService.getPlacesWithinRadius(coordinateComm, filterParamsComm))
            .thenReturn()
        //when

        //then
    }
*/
}
