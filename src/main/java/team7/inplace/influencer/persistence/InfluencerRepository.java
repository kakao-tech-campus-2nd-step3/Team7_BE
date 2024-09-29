package team7.inplace.influencer.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.influencer.domain.Influencer;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
    // 더미 데이터 입니다!!

    @Override
    List<Influencer> findAllById(Iterable<Long> longs);

    List<Influencer> findByNameIn(List<String> names);
}
