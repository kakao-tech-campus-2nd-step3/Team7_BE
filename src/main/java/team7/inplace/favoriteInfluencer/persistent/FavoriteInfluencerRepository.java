package team7.inplace.favoriteInfluencer.persistent;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;

public interface FavoriteInfluencerRepository extends JpaRepository<FavoriteInfluencer, Long> {
    List<FavoriteInfluencer> findByUserId(Long userId);
}
