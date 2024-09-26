package team7.inplace.influencer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.influencer.domain.Influencer;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {

}
