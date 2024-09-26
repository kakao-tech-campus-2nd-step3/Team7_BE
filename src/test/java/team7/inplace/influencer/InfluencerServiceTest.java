package team7.inplace.influencer;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerDto;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;

@ExtendWith(MockitoExtension.class)
public class InfluencerServiceTest {

    @Mock
    private InfluencerRepository influencerRepository;

    @InjectMocks
    private InfluencerService influencerService;

    @Test
    public void getAllInfluencersTest() {
        Influencer influencer1 = Influencer.builder()
            .name("influencer1")
            .job("job1")
            .imgUrl("imgUrl1")
            .build();
        Influencer influencer2 = Influencer.builder()
            .name("influencer2")
            .job("job2")
            .imgUrl("imgUrl2")
            .build();
        given(influencerRepository.findAll()).willReturn(Arrays.asList(influencer1, influencer2));

        List<InfluencerDto> influencerDtoList = influencerService.getAllInfluencers();

        assertThat(influencerDtoList).hasSize(2);
        assertThat(influencerDtoList.get(0).influencerName()).isEqualTo("influencer1");
        assertThat(influencerDtoList.get(0).likes()).isFalse();
        assertThat(influencerDtoList.get(1).influencerName()).isEqualTo("influencer2");
        assertThat(influencerDtoList.get(1).likes()).isFalse();
    }
}
