package team7.inplace.video.persistence;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.video.domain.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findVideosByInfluencerIdIn(List<Long> influencerIds);
    List<Video> findAllByOrderByIdDesc();
}
