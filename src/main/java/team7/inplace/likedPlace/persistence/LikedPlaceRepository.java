package team7.inplace.likedPlace.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.likedPlace.domain.LikedPlace;

public interface LikedPlaceRepository extends JpaRepository<LikedPlace, Long> {

    Optional<LikedPlace> findByUserIdAndPlaceId(Long userId, Long placeId);
}
