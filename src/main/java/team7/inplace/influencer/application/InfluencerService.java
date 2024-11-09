package team7.inplace.influencer.application;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.favoriteInfluencer.persistent.FavoriteInfluencerRepository;
import team7.inplace.influencer.application.dto.InfluencerCommand;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.application.dto.InfluencerNameInfo;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.persistence.UserRepository;

@RequiredArgsConstructor
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final FavoriteInfluencerRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<InfluencerInfo> getAllInfluencers(Pageable pageable) {
        Page<Influencer> influencersPage = influencerRepository.findAll(pageable);

        // 로그인 안된 경우, likes를 모두 false로 설정
        if (AuthorizationUtil.isNotLoginUser()) {
            return influencersPage.map(influencer -> InfluencerInfo.from(influencer, false));
        }

        // 로그인 된 경우
        Long userId = AuthorizationUtil.getUserId();
        Set<Long> likedInfluencerIds = favoriteRepository.findLikedInfluencerIdsByUserId(userId);

        List<InfluencerInfo> influencerInfos = influencersPage.stream()
            .map(influencer -> {
                boolean isLiked = likedInfluencerIds.contains(influencer.getId());
                return InfluencerInfo.from(influencer, isLiked);
            })
            .sorted((a, b) -> Boolean.compare(b.likes(), a.likes()))
            .toList();

        return new PageImpl<>(influencerInfos, pageable, influencersPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<InfluencerNameInfo> getAllInfluencerNames() {
        List<String> names = influencerRepository.findAllInfluencerNames();
        return names.stream()
            .map(InfluencerNameInfo::new)
            .toList();
    }

    @Transactional
    public Long createInfluencer(InfluencerCommand command) {
        Influencer influencer = InfluencerCommand.to(command);
        return influencerRepository.save(influencer).getId();
    }

    @Transactional
    public Long updateInfluencer(Long id, InfluencerCommand command) {
        Influencer influencer = influencerRepository.findById(id).orElseThrow();
        influencer.update(command.influencerName(), command.influencerImgUrl(),
            command.influencerJob());

        return influencer.getId();
    }

    @Transactional
    public void deleteInfluencer(Long id) {
        Influencer influencer = influencerRepository.findById(id).orElseThrow();

        influencerRepository.delete(influencer);
    }
}
