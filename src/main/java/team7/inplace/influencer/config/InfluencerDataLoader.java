package team7.inplace.influencer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;

@RequiredArgsConstructor
@Component
public class InfluencerDataLoader implements ApplicationRunner {

    private final InfluencerRepository influencerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Influencer influencer1 = new Influencer("Influencer 1", "imgUrl1", "job1");
        Influencer influencer2 = new Influencer("Influencer 2", "imgUrl2", "job2");
        Influencer influencer3 = new Influencer("Influencer 3", "imgUrl3", "job3");

        influencerRepository.save(influencer1);
        influencerRepository.save(influencer2);
        influencerRepository.save(influencer3);
    }
}
