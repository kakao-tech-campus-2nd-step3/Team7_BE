package team7.inplace.influencer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;

@SpringBootTest
public class InfluencerRepositoryTest {

    @Autowired
    private InfluencerRepository influencerRepository;

    @Test
    public void findAllTest() {
        Influencer influencer4 = new Influencer("influencer4", "imgUrl1", "job1");
        Influencer influencer5 = new Influencer("influencer5", "imgUrl2", "job2");

        influencerRepository.save(influencer4);
        influencerRepository.save(influencer5);

        List<Influencer> savedInfluencers = influencerRepository.findAll();

        assertThat(savedInfluencers.get(3)).usingRecursiveComparison().isEqualTo(influencer4);
        assertThat(savedInfluencers.get(4)).usingRecursiveComparison().isEqualTo(influencer5);
    }

}
