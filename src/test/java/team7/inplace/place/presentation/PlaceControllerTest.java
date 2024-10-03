package team7.inplace.place.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import team7.inplace.place.application.CategoryService;
import team7.inplace.place.application.PlaceService;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetCategories() throws Exception {
        /*
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
         */
    }

}
