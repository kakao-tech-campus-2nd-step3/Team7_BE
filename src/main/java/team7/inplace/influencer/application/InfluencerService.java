package team7.inplace.influencer.application;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;
import team7.inplace.favoriteInfluencer.persistent.FavoriteInfluencerRepository;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.influencer.application.dto.InfluencerCommand;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.influencer.presentation.dto.InfluencerLikeRequest;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

@RequiredArgsConstructor
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final FavoriteInfluencerRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<InfluencerInfo> getAllInfluencers() {
        List<Influencer> influencers = influencerRepository.findAll();
        Long userId = AuthorizationUtil.getUserId();

        // 로그인 안된 경우, likes를 모두 false로 설정
        if (userId == null) {
            return influencers.stream()
                .map(influencer -> InfluencerInfo.from(influencer, false))
                .toList();
        }

        // 로그인 된 경우
        Set<Long> likedInfluencerIds = favoriteRepository.findLikedInfluencerIdsByUserId(userId);

        List<InfluencerInfo> influencerInfos = influencers.stream()
            .map(influencer -> {
                boolean isLiked = likedInfluencerIds.contains(influencer.getId());
                return InfluencerInfo.from(influencer, isLiked);
            })
            .sorted((a, b) -> Boolean.compare(b.likes(), a.likes()))
            .toList();

        return influencerInfos;
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

    @Transactional
    public void likeToInfluencer(InfluencerLikeRequest param) {
        String username = AuthorizationUtil.getUsername();
        if (!StringUtils.hasText(username)) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }

        User user = userRepository.findByUsername(username).orElseThrow();
        Influencer influencer = influencerRepository.findById(param.influencerId()).orElseThrow();

        FavoriteInfluencer favorite = favoriteRepository.findByUserIdAndInfluencerId(user.getId(),
                influencer.getId())
            .orElseGet(() -> new FavoriteInfluencer(user, influencer)); // 존재하지 않으면 새로 생성

        favorite.updateLike(param.likes());
        favoriteRepository.save(favorite);
    }
}
