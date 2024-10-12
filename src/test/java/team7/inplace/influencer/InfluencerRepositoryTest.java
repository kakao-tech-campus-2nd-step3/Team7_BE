package team7.inplace.influencer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;

@DataJpaTest
public class InfluencerRepositoryTest {

    @Autowired
    private InfluencerRepository influencerRepository;

    @Test
    public void findAllTest() {
        Influencer influencer1 = new Influencer("influencer1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer("influencer2", "imgUrl2", "job2");

        influencerRepository.save(influencer1);
        influencerRepository.save(influencer2);

        List<Influencer> savedInfluencers = influencerRepository.findAll();

        assertThat(savedInfluencers.get(0)).usingRecursiveComparison().isEqualTo(influencer1);
        assertThat(savedInfluencers.get(1)).usingRecursiveComparison().isEqualTo(influencer2);
    }

}
