package team7.inplace.influencer;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerDto;
import team7.inplace.influencer.presentation.InfluencerController;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(InfluencerController.class)
public class InfluencerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfluencerService influencerService;

    @Test
    public void getAllInfluencersTest() throws Exception {
        InfluencerDto influencerDto1 = new InfluencerDto(1L, "influencer1", "imgUrl1", "job1",
            false);
        InfluencerDto influencerDto2 = new InfluencerDto(2L, "influencer2", "imgUrl2", "job2",
            false);
        List<InfluencerDto> influencersDtoList = Arrays.asList(influencerDto1, influencerDto2);
        given(influencerService.getAllInfluencers()).willReturn(influencersDtoList);

        mockMvc.perform(get("/influencers")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].influencerId").value(1L))
            .andExpect(jsonPath("$[0].influencerName").value("influencer1"))
            .andExpect(jsonPath("$[0].influencerImgUrl").value("imgUrl1"))
            .andExpect(jsonPath("$[0].influencerJob").value("job1"))
            .andExpect(jsonPath("$[0].likes").value(false))
            .andExpect(jsonPath("$[1].influencerId").value(2L))
            .andExpect(jsonPath("$[1].influencerName").value("influencer2"))
            .andExpect(jsonPath("$[1].influencerImgUrl").value("imgUrl2"))
            .andExpect(jsonPath("$[1].influencerJob").value("job2"))
            .andExpect(jsonPath("$[1].likes").value(false));
    }
}
