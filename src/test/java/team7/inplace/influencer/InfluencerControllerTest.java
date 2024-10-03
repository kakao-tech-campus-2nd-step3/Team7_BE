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
import team7.inplace.influencer.application.dto.InfluencerInfo;
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
        InfluencerInfo influencerInfo1 = new InfluencerInfo(1L, "influencer1", "imgUrl1", "job1",
            false);
        InfluencerInfo influencerInfo2 = new InfluencerInfo(2L, "influencer2", "imgUrl2", "job2",
            false);
        List<InfluencerInfo> influencerInfoList = List.of(influencerInfo1, influencerInfo2);
        given(influencerService.getAllInfluencers()).willReturn(influencerInfoList);

        // 예상 json 값
        List<InfluencerResponse> responseList = influencerInfoList.stream()
            .map(InfluencerResponse::from)
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
