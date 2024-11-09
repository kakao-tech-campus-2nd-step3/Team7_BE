package team7.inplace.influencer.persistence;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team7.inplace.influencer.domain.Influencer;

public interface InfluencerRepository extends JpaRepository<Influencer, Long> {

    @Override
    List<Influencer> findAllById(Iterable<Long> longs);

    List<Influencer> findByNameIn(List<String> names);

    Page<Influencer> findAll(Pageable pageable);

    @Query("SELECT i.name FROM Influencer i")
    List<String> findAllInfluencerNames();
}
