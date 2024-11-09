package team7.inplace.video.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import team7.inplace.global.exception.InplaceException;
import team7.inplace.global.exception.code.PlaceErrorCode;
import team7.inplace.global.exception.code.VideoErrorCode;
import team7.inplace.global.queryDsl.QueryDslConfig;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.domain.Video;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@Import(QueryDslConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 각 메서드 실행마다 이전 결과 초기화
public class VideoRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private PlaceRepository placeRepository;
    private Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void init() {
        Place place1 = new Place("Place 1",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "카페",
                "Address 1|Address 2|Address 3",
                "10.0", "10.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );
        Place place2 = new Place("Place 2",
                "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
                "menuImg.url", "일식",
                "Address 1|Address 2|Address 3",
                "10.0", "50.0",
                Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
                Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
                Arrays.asList("삼겹살|5000|false|menu.url|description",
                        "돼지찌개|7000|true|menu.url|description"),
                LocalDateTime.of(2024, 3, 28, 5, 30),
                Arrays.asList(
                        "menuBoard1.url",
                        "menuBoard2.url"
                )
        );
        entityManager.persist(place1);
        entityManager.persist(place2);

        Influencer influencer1 = new Influencer("name1", "job1", "imgUrl");
        Influencer influencer2 = new Influencer("name2", "job2", "imgUrl");
        entityManager.persist(influencer1);
        entityManager.persist(influencer2);

        Video video1 = Video.from(influencer1, place1, "url1");
        Video video2 = Video.from(influencer1, place1, "url2");
        Video video3 = Video.from(influencer1, place1, "url3");
        Video video4 = Video.from(influencer2, place2, "url4");
        Video video5 = Video.from(influencer2, place2, "url5");
        Video video6 = Video.from(influencer1, null, "url6");
        Video video7 = Video.from(influencer2, null, "url7");
        entityManager.persist(video1);
        entityManager.persist(video2);
        entityManager.persist(video3);
        entityManager.persist(video4);
        entityManager.persist(video5);
        entityManager.persist(video6);
        entityManager.persist(video7);
    }

    @Test
    @DisplayName("findVideosByInfluencerIdIn Test")
    void test1() {
        // given
        /* Before Each */

        // when
        List<Long> influencerIds = new ArrayList<>();
        influencerIds.add(1L);

        Page<Video> savedVideos = videoRepository.findVideosByInfluencerIdIn(
                influencerIds,
                pageable
        );
        // then
        Assertions.assertThat(savedVideos.getContent().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("findAllByOrderByIdDesc Test")
    void test2() {
        // given
        /* Before Each */

        // when
        Page<Video> videos = videoRepository.findAllByOrderByIdDesc(pageable);

        // then
        Long number = 5L;
        for (Video video : videos.getContent()) {
            Assertions.assertThat(video.getId()).isEqualTo(number);
            number -= 1L;
        }
    }

    @Test
    @DisplayName("findAllByPlaceIsNull Test")
    void test3(){
        // given

        // when
        Page<Video> videos = videoRepository.findAllByPlaceIsNull(pageable);
        // then
        Assertions.assertThat(videos.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findTopByPlaceOrderByIdDesc Test")
    void test4() {
        // given

        // when
        Place place = placeRepository.findById(1L).orElseThrow(()->InplaceException.of(PlaceErrorCode.NOT_FOUND));
        Video video = videoRepository.findTopByPlaceOrderByIdDesc(place).orElseThrow(NoSuchFieldError::new);

        // then
        Assertions.assertThat(video).isNotNull();
        Assertions.assertThat(video.getId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("findByPlaceIdIn Test")
    void test5(){
        // given

        // when
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Video> videos = videoRepository.findByPlaceIdIn(ids);
        // then
        Assertions.assertThat(videos.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("findByPlaceId Test")
    void test6(){
        // given

        // when
        List<Video> videos = videoRepository.findByPlaceId(1L);
        // then
        Assertions.assertThat(videos.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("findVideosByOrderByViewCountIncreaseDesc Test")
    void test7(){
        // given

        // when
        Video video1 = videoRepository.findById(1L).orElseThrow(() -> InplaceException.of(VideoErrorCode.NOT_FOUND));
        Video video2 = videoRepository.findById(2L).orElseThrow(() -> InplaceException.of(VideoErrorCode.NOT_FOUND));
        video1.updateViewCount(10L);
        video2.updateViewCount(100L);

        Page<Video> videos = videoRepository.findVideosByOrderByViewCountIncreaseDesc(pageable);
        // then
        for (Video video : videos) {
            System.out.println("video = " + video.getId() + " " + video.getVideoUrl() + " " + video.getViewCountIncrease());
        }
        Assertions.assertThat(videos.getContent().size()).isEqualTo(7);
        Assertions.assertThat(videos.getContent().get(0)).isEqualTo(video2);
    }
}
