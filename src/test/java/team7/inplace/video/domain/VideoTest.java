package team7.inplace.video.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class VideoTest {

    @Test
    @DisplayName("처음 업데이트 되는 비디오일 경우, viewCountIncrease는 0")
    void viewCountIncreaseTest_whenFirstUpdated() {
        // given
        Video video = Video.from(null, null, "UUID");
        final Long updateCount = 10000L;

        // when
        video.updateViewCount(updateCount);

        // then
        assertThat(video.getViewCountIncrease()).isZero();
    }

    @Test
    @DisplayName("처음 업데이트가 되지 않는 비디오의 경우, viewCountIncrease는 이전 viewCount와의 차이")
    void viewCountIncreaseTest_whenNotFirstUpdated() {
        // given
        Video video = Video.from(null, null, "UUID");
        final Long updateCount = 10000L;
        final Long updateCount2 = 20000L;
        video.updateViewCount(updateCount);
        final Long expected = updateCount2 - updateCount;

        // when
        video.updateViewCount(updateCount2);
        var result = video.getViewCountIncrease();
        // then
        assertThat(result).isEqualTo(expected);
    }
}
