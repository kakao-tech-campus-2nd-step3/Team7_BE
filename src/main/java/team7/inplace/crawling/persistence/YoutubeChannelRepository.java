package team7.inplace.crawling.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import team7.inplace.crawling.domain.YoutubeChannel;

public interface YoutubeChannelRepository extends JpaRepository<YoutubeChannel, Long> {
}
