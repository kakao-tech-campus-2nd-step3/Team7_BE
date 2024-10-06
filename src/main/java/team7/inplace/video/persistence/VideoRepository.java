package team7.inplace.video.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByInfluencerIdIn(List<Long> influencerIds);

    List<Video> findAllByOrderByIdDesc();

    Video findTopByPlaceOrderByIdDesc(Place place);

    List<Video> findByPlaceIdIn(List<Long> placeIds);

    Optional<Video> findByPlaceId(Long placeId);
}
