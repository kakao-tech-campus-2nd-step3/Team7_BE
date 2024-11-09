package team7.inplace.favoriteInfluencer.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerCommand;
import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerListCommand;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;
import team7.inplace.favoriteInfluencer.persistent.FavoriteInfluencerRepository;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.AuthorizationErrorCode;
import team7.inplace.global.exception.code.UserErrorCode;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

@RequiredArgsConstructor
@Service
public class FavoriteInfluencerService {

    private final InfluencerRepository influencerRepository;
    private final FavoriteInfluencerRepository favoriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeToInfluencer(FavoriteInfluencerCommand command) {
        if (AuthorizationUtil.isNotLoginUser()) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }

        Long userId = AuthorizationUtil.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> InplaceException.of(UserErrorCode.NOT_FOUND));
        Influencer influencer = influencerRepository.findById(command.influencerId()).orElseThrow();

        FavoriteInfluencer favorite = favoriteRepository.findByUserIdAndInfluencerId(userId,
                influencer.getId())
            .orElseGet(() -> new FavoriteInfluencer(user, influencer)); // 존재하지 않으면 새로 생성

        favorite.updateLike(command.likes());
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void likeToManyInfluencer(FavoriteInfluencerListCommand command) {
        if (AuthorizationUtil.isNotLoginUser()) {
            throw InplaceException.of(AuthorizationErrorCode.TOKEN_IS_EMPTY);
        }

        Long userId = AuthorizationUtil.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> InplaceException.of(UserErrorCode.NOT_FOUND));

        List<Influencer> influencers = influencerRepository.findAllById(command.influencerIds());

        for (Influencer influencer : influencers) {

            FavoriteInfluencer favorite = favoriteRepository.findByUserIdAndInfluencerId(userId,
                    influencer.getId())
                .orElseGet(() -> new FavoriteInfluencer(user, influencer)); // 존재하지 않으면 새로 생성

            favorite.updateLike(command.likes());
            favoriteRepository.save(favorite);
        }
    }
}
