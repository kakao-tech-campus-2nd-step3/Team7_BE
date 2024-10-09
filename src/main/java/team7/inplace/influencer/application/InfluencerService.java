package team7.inplace.influencer.application;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.influencer.presentation.dto.InfluencerRequestParam;
import team7.inplace.userFavoriteInfluencer.domain.UserFavoriteInfluencer;
import team7.inplace.userFavoriteInfluencer.persistent.UserFavoriteInfluencerRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

@RequiredArgsConstructor
@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final UserFavoriteInfluencerRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<InfluencerInfo> getAllInfluencers() {
        return influencerRepository.findAll().stream()
            .map(InfluencerInfo::from)
            .toList();
    }

    public void likeToInfluencer(InfluencerRequestParam param){
        String username = AuthorizationUtil.getUsername();
        if(StringUtils.hasText(username)){
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }

        User user = userRepository.findByUsername(username).orElseThrow();
        Influencer influencer = influencerRepository.findById(param.influencerId()).orElseThrow();

        UserFavoriteInfluencer favorite = new UserFavoriteInfluencer(user, influencer);
        favorite.check(param.likes());
        favoriteRepository.save(favorite);
    }
}
