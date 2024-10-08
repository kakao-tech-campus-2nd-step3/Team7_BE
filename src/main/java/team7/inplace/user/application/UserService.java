package team7.inplace.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.like.domain.Like;
import team7.inplace.like.persistent.LikeRepository;
import team7.inplace.user.application.dto.UserCommand;
import team7.inplace.user.domain.User;
import team7.inplace.user.persistence.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

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
        List<Like> likes = likeRepository.findByUser(userRepository.findByUsername(username).orElseThrow());
         return likes.stream().map(Like::getInfluencer).map(Influencer::getId).toList();
    }
}
