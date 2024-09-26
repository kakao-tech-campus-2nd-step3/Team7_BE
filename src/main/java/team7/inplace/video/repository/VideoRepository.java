package team7.inplace.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
//    List<Video> findVideosByInfluencerIdIn(List<Long> influencerIds);
}
