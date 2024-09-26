package team7.inplace.influencer.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.influencer.application.dto.InfluencerDto;
import team7.inplace.influencer.persistence.InfluencerRepository;

@RequiredArgsConstructor
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;

    @Transactional(readOnly = true)
    public List<InfluencerDto> getAllInfluencers() {
        return influencerRepository.findAll().stream()
            .map(influencer -> new InfluencerDto(
                influencer.getInfluencerId(),
                influencer.getName(),
                influencer.getImgUrl(),
                influencer.getJob(),
                false)
            )
            .toList();
    }
}
