package team7.inplace.userFavoriteInfluencer.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.userFavoriteInfluencer.domain.UserFavoriteInfluencer;
import team7.inplace.user.domain.User;

import java.util.List;

public interface UserFavoriteInfluencerRepository extends JpaRepository<UserFavoriteInfluencer, Long> {
    List<UserFavoriteInfluencer> findByUser(User user);

    UserFavoriteInfluencer save(UserFavoriteInfluencer favorite);
}
