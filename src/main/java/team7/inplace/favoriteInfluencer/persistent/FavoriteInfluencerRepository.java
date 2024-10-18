package team7.inplace.favoriteInfluencer.persistent;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team7.inplace.favoriteInfluencer.domain.FavoriteInfluencer;

public interface FavoriteInfluencerRepository extends JpaRepository<FavoriteInfluencer, Long> {

    List<FavoriteInfluencer> findByUserId(Long userId);

    Optional<FavoriteInfluencer> findByUserIdAndInfluencerId(Long userId, Long influencerId);

    @Query("SELECT f.influencer.id FROM FavoriteInfluencer f WHERE f.user.id = :userId AND f.isLiked = true")
    Set<Long> findLikedInfluencerIdsByUserId(@Param("userId") Long userId);
}
