package team7.inplace.user.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;
import team7.inplace.favoriteInfluencer.persistent.FavoriteInfluencerRepository;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.UserErrorCode;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.security.util.AuthorizationUtil;
import team7.inplace.user.application.dto.UserCommand;
import team7.inplace.user.application.dto.UserCommand.Info;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FavoriteInfluencerRepository favoriteInfluencerRepository;

    @Transactional
    public UserCommand.Info registerUser(UserCommand.Create userCreate) {
        User user = userCreate.toEntity();
        userRepository.save(user);
        return UserCommand.Info.of(user);
    }

    @Transactional(readOnly = true)
    public UserCommand.Info getUserByUsername(String username) {
        return UserCommand.Info.of(userRepository.findByUsername(username)
            .orElseThrow(() -> InplaceException.of(UserErrorCode.NOT_FOUND)));
    }

    @Transactional
    public Optional<Info> findUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(Info::of);
    }

    @Transactional(readOnly = true)
    public List<Long> getInfluencerIdsByUsername(Long userId) {
        List<FavoriteInfluencer> likes = favoriteInfluencerRepository.findByUserId(userId);
        return likes.stream().map(FavoriteInfluencer::getInfluencer).map(Influencer::getId)
            .toList();
    }

    @Transactional
    public void updateNickname(String nickname){
        User user = userRepository.findByUsername(AuthorizationUtil.getUsername()).orElseThrow(
                ()-> InplaceException.of(UserErrorCode.NOT_FOUND)
        );

        user.updateInfo(nickname);
    }
}
