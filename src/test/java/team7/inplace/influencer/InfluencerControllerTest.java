package team7.inplace.influencer;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerDto;
import team7.inplace.influencer.presentation.InfluencerController;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;
import team7.inplace.influencer.presentation.dto.InfluencerResponse;

@ExtendWith(MockitoExtension.class)
public class InfluencerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InfluencerService influencerService;

    @InjectMocks
    private InfluencerController influencerController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(influencerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllInfluencersTest() throws Exception {
        InfluencerDto influencerDto1 = new InfluencerDto(1L, "influencer1", "imgUrl1", "job1",
            false);
        InfluencerDto influencerDto2 = new InfluencerDto(2L, "influencer2", "imgUrl2", "job2",
            false);
        List<InfluencerDto> influencerDtoList = List.of(influencerDto1, influencerDto2);
        given(influencerService.getAllInfluencers()).willReturn(influencerDtoList);

        // 예상 json 값
        List<InfluencerResponse> responseList = influencerDtoList.stream()
            .map(InfluencerResponse::convertToResponse)
            .toList();
        InfluencerListResponse expectedResponse = new InfluencerListResponse(responseList);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/influencers")
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(expectedJson));
        verify(influencerService, times(1)).getAllInfluencers(); // 서비스 호출 확인
    }
}
