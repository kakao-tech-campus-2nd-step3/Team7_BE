package team7.inplace.oauthToken.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team7.inplace.oauthToken.domain.OauthToken;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthToken, Long> {

    Optional<OauthToken> findByUserId(Long userId);
}
