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
import team7.inplace.influencer.application.dto.InfluencerInfo;
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
        Influencer influencer1 = new Influencer("influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer("influencer2", "imgUrl2", "job2");

        given(influencerRepository.findAll()).willReturn(Arrays.asList(influencer1, influencer2));

        List<InfluencerInfo> influencerInfoList = influencerService.getAllInfluencers();

        assertThat(influencerInfoList).hasSize(2);
        assertThat(influencerInfoList.get(0).influencerName()).isEqualTo("influencer1");
        assertThat(influencerInfoList.get(0).likes()).isFalse();
        assertThat(influencerInfoList.get(1).influencerName()).isEqualTo("influencer2");
        assertThat(influencerInfoList.get(1).likes()).isFalse();
    }
}
