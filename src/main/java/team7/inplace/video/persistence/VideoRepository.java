package team7.inplace.video.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findVideosByInfluencerIdIn(List<Long> influencerIds);

    @Query("SELECT v FROM Video v WHERE v.place.id IN :placeIds")
    List<Video> findByPlaceIds(@Param("placeIds") List<Long> placeIds);

    List<Video> findAllByOrderByIdDesc();

    Video findTopByPlaceOrderByIdDesc(Place place);

}
