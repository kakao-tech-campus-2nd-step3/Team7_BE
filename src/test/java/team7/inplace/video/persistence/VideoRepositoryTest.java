package team7.inplace.video.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 각 메서드 실행마다 이전 결과 초기화
public class VideoRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private VideoRepository videoRepository;
    private Place place;

    @BeforeEach
    void init() {
        /*
        place = Place.builder()
            .name("Test Place")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.CAFE)
            .coordinate(new Coordinate("127.0", "37.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee", "menuImg.url"),
                new Menu(7000L, false, "Cake", "menuImg.url")
            ))
            .build();

        entityManager.persist(place);

        Influencer influencer1 = new Influencer("name1", "job1", "imgUrl");
        Influencer influencer2 = new Influencer("name2", "job2", "imgUrl");
        entityManager.persist(influencer1);
        entityManager.persist(influencer2);

        Video video1 = new Video("url1", influencer1, place);
        Video video2 = new Video("url2", influencer1, place);
        Video video3 = new Video("url3", influencer1, place);
        Video video4 = new Video("url4", influencer2, place);
        Video video5 = new Video("url5", influencer2, place);
        entityManager.persist(video1);
        entityManager.persist(video2);
        entityManager.persist(video3);
        entityManager.persist(video4);
        entityManager.persist(video5);

         */
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
                influencerIds, PageRequest.of(0, 5)
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
        Page<Video> videos = videoRepository.findAllByOrderByIdDesc(PageRequest.of(0, 5));

        // then
        Long number = 5L;
        for (Video video : videos.getContent()) {
            Assertions.assertThat(video.getId()).isEqualTo(number);
            number -= 1L;
        }
    }

    @Test
    @DisplayName("findTopByPlaceOrderByIdDesc Test")
    void test3() {
        // given

        // when
        Video video = videoRepository.findTopByPlaceOrderByIdDesc(place).orElseThrow(NoSuchFieldError::new);

        // then
        Assertions.assertThat(video).isNotNull();
        Assertions.assertThat(video.getId()).isEqualTo(5L);
    }
}
