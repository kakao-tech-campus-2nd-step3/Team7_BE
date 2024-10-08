package team7.inplace.like.persistent;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.like.domain.Like;
import team7.inplace.user.domain.User;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByUser(User user);
}
