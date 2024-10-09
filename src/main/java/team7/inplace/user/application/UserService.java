package team7.inplace.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.userFavoriteInfluencer.domain.UserFavoriteInfluencer;
import team7.inplace.userFavoriteInfluencer.persistent.UserFavoriteInfluencerRepository;
import team7.inplace.user.application.dto.UserCommand;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserFavoriteInfluencerRepository userFavoriteInfluencerRepository;

    @Transactional
    public void registerUser(UserCommand.Create userCreate) {
        User user = userCreate.toEntity();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public UserCommand.Info getUserByUsername(String username) {
        return UserCommand.Info.of(userRepository.findByUsername(username).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<Long> getInfluencerIdsByUsername(String username) {
        List<UserFavoriteInfluencer> likes = userFavoriteInfluencerRepository
                .findByUser(userRepository.findByUsername(username).orElseThrow());
         return likes.stream().map(UserFavoriteInfluencer::getInfluencer).map(Influencer::getId).toList();
    }
}
