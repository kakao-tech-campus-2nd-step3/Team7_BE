package team7.inplace.favoriteInfluencer.persistent;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.user.domain.User;

public interface FavoriteInfluencerRepository extends JpaRepository<FavoriteInfluencer, Long> {

    List<FavoriteInfluencer> findByUserId(Long userId);

    Optional<FavoriteInfluencer> findByUserAndInfluencer(User user, Influencer influencer);
}
