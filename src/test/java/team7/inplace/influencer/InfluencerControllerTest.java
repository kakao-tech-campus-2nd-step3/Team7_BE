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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.presentation.InfluencerController;
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
        PageableHandlerMethodArgumentResolver pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
        mockMvc = MockMvcBuilders.standaloneSetup(influencerController)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllInfluencersTest() throws Exception {
        InfluencerInfo influencerInfo1 = new InfluencerInfo(1L, "influencer1",
            "imgUrl1", "job1", false);
        InfluencerInfo influencerInfo2 = new InfluencerInfo(2L, "influencer2",
            "imgUrl2", "job2", false);

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(0, 10);

        // Page 객체 생성 (서비스에서 반환될 객체)
        Page<InfluencerInfo> influencerInfoPage = new PageImpl<>(
            List.of(influencerInfo1, influencerInfo2), pageable, 2);

        // influencerService.getAllInfluencers(pageable)의 반환값을 모킹
        given(influencerService.getAllInfluencers(pageable)).willReturn(influencerInfoPage);

        // 예상 json 값
        Page<InfluencerResponse> responsePage = influencerInfoPage.map(InfluencerResponse::from);
        String expectedJson = objectMapper.writeValueAsString(responsePage);

        // API 호출 및 검증
        mockMvc.perform(get("/influencers")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0") // 페이지 번호
                .param("size", "10")) // 페이지 크기
            .andDo(print())
            .andExpect(status().isOk()) // HTTP 200 OK 응답 확인
            .andExpect(content().json(expectedJson)); // JSON 응답이 예상값과 일치하는지 확인

        // influencerService.getAllInfluencers(pageable) 메서드가 한 번 호출됐는지 검증
        verify(influencerService, times(1)).getAllInfluencers(pageable);
    }


}
