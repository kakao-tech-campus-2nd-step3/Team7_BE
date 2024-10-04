package team7.inplace.influencer.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.persistence.InfluencerRepository;

@RequiredArgsConstructor
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;

    @Transactional(readOnly = true)
    public List<InfluencerInfo> getAllInfluencers() {
        return influencerRepository.findAll().stream()
            .map(InfluencerInfo::from)
            .toList();
    }
}
