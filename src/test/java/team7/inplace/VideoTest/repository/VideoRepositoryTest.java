package team7.inplace.VideoTest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import team7.inplace.influencer.entity.Influencer;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.video.entity.Video;
import team7.inplace.video.repository.VideoRepository;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 각 메서드 실행마다 이전 결과 초기화
public class VideoRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private VideoRepository videoRepository;

    @BeforeEach
    @Transactional
    void init(){
        Place place = Place.builder()
                .name("Test Place")
                .pet(false)
                .wifi(true)
                .parking(false)
                .fordisabled(true)
                .nursery(false)
                .smokingroom(false)
                .address1("Address 1")
                .address2("Address 2")
                .address3("Address 3")
                .menuImgUrl("menu.jpg")
                .category(Category.CAFE)
                .longitude("127.0")
                .latitude("37.0")
                .build();
        entityManager.persist(place);

        Influencer influencer1 = new Influencer("name1", "job1", "imgUrl");
        Influencer influencer2 = new Influencer("name2", "job2", "imgUrl");
        entityManager.persist(influencer1);
        entityManager.persist(influencer2);


        Video video1 = new Video( "url1", influencer1, place);
        Video video2 = new Video( "url2", influencer1, place);
        Video video3 = new Video( "url3", influencer1, place);
        Video video4 = new Video( "url4", influencer2, place);
        Video video5 = new Video( "url5", influencer2, place);
        entityManager.persist(video1);
        entityManager.persist(video2);
        entityManager.persist(video3);
        entityManager.persist(video4);
        entityManager.persist(video5);
    }

    @Test
    @DisplayName("findVideosByInfluencerIdIn Test")
    void test1(){
        // given
        /* Before Each */
        // when
        List<Long> influencerIds = new ArrayList<>();
        influencerIds.add(1L);

        List<Video> savedVideos = videoRepository.findVideosByInfluencerIdIn(influencerIds);
        // then
        Assertions.assertThat(savedVideos.size()).isEqualTo(3);
    }
}
