package team7.inplace.favorite.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.favorite.domain.Favorite;
import team7.inplace.user.domain.User;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);

    Favorite save(Favorite favorite);
}
