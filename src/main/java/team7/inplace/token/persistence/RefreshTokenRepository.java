package team7.inplace.token.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team7.inplace.token.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

}
