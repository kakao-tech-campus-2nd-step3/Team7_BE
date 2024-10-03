package team7.inplace.video.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByInfluencerIdIn(List<Long> influencerIds);

    Video findByPlaceId(Long placeId);

    List<Video> findAllByOrderByIdDesc();

    Video findTopByPlaceOrderByIdDesc(Place place);

}
