package team7.inplace.likedPlace.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.likedPlace.domain.LikedPlace;
import team7.inplace.place.domain.Place;
import team7.inplace.user.domain.Role;
import team7.inplace.user.domain.User;
import team7.inplace.user.domain.UserType;

@SpringBootTest
@Transactional
class LikedPlaceRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private LikedPlaceRepository likedPlaceRepository;

    Place place1;

    User user1;

    @BeforeEach
    public void init() throws NoSuchFieldException, IllegalAccessException {
        likedPlaceRepository.deleteAll();

        place1 = new Place("Place 1",
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
        entityManager.persist(place1);

        user1 = new User("user1", "pass1", "nick1", UserType.KAKAO, Role.USER);
        entityManager.persist(user1);
    }

    @Test
    @DisplayName("FindByUserIdAndPlaceId 테스트")
    public void test1() {
        //given
        LikedPlace expected = new LikedPlace(user1, place1);

        //when
        LikedPlace actual = likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(),
                place1.getId())
            .orElseGet(() -> new LikedPlace(user1, place1));

        //then
        assertThat(actual.getUser().getUsername()).isEqualTo(expected.getUser().getUsername());
        assertThat(actual.getPlace().getName()).isEqualTo(expected.getPlace().getName());
    }

    @Test
    @DisplayName("좋아요 및 상태 업데이트 테스트")
    public void test2() {
        // given
        LikedPlace likedPlace = likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(),
                place1.getId())
            .orElseGet(() -> new LikedPlace(user1, place1));

        // 처음에는 좋아요 상태가 false임을 가정
        assertThat(likedPlace.isLiked()).isFalse();

        // when: 좋아요 상태를 true로 업데이트
        likedPlace.updateLike(true);
        likedPlaceRepository.save(likedPlace);

        // then: 업데이트 후 좋아요 상태가 true인지 확인
        LikedPlace updatedLikedPlace = likedPlaceRepository.findByUserIdAndPlaceId(user1.getId(),
            place1.getId()).get();
        assertThat(updatedLikedPlace.isLiked()).isTrue();
    }
}
