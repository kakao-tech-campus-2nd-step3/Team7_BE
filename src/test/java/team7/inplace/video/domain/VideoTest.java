package team7.inplace.video.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Place;

public class VideoTest {
    @Test
    @DisplayName("Video Entity Test")
    void test1() {
        // given
        Influencer influencer = new Influencer("성시경", "가수", "url");
        Place place = new Place();

        // when
        Video video = new Video("url", influencer, place);

        // then
        Assertions.assertThat(video).isNotNull();
        Assertions.assertThat(video.getVideoUrl()).isEqualTo("url");
        Assertions.assertThat(video.getInfluencer()).isEqualTo(influencer);
        Assertions.assertThat(video.getPlace()).isEqualTo(place);
    }
}
