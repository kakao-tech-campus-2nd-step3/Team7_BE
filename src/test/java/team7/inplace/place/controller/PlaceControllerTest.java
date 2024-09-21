package team7.inplace.place.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.CategoryListResponse;
import team7.inplace.place.service.PlaceService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment =  WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PlaceControllerTest {
    @Mock
    private PlaceService placeService;

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
        // given
        List<Category> expectedCategories = List.of(Category.CAFE, Category.WESTERN, Category.JAPANESE, Category.KOREAN);
        when(placeService.getCategories()).thenReturn(expectedCategories);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/places/categories")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(result -> {
                String jsonResponse = result.getResponse().getContentAsString();
                CategoryListResponse responseDTO = objectMapper.readValue(jsonResponse, CategoryListResponse.class);
                assertThat(responseDTO.categories()).isEqualTo(expectedCategories);
            });
    }
}
