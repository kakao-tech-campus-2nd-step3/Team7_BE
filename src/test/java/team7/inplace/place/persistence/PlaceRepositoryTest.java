package team7.inplace.place.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;

@DataJpaTest
class PlaceRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    public void init() {
//        Place place1 = Place.builder()
//            .name("Place 1")
//            .pet(false)
//            .wifi(true)
//            .parking(false)
//            .fordisabled(true)
//            .nursery(false)
//            .smokingroom(false)
//            .address(new Address("Address 1", "Address 2", "Address 3"))
//            .menuImgUrl("menu.jpg")
//            .category(Category.CAFE)
//            .coordinate(new Coordinate("1.0", "1.0"))
//            .timeList(Arrays.asList(
//                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
//                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
//            ))
//            .offdayList(Arrays.asList(
//                new PlaceCloseTime("한글날", "월~금", false)
//            ))
//            .menuList(Arrays.asList(
//                new Menu(5000L, true, "Coffee"),
//                new Menu(7000L, false, "Cake")
//            ))
//            .build();
//
//        Place place2 = Place.builder()
//            .name("Place 2")
//            .pet(false)
//            .wifi(true)
//            .parking(false)
//            .fordisabled(true)
//            .nursery(false)
//            .smokingroom(false)
//            .address(new Address("Address 1", "Address 2", "Address 3"))
//            .menuImgUrl("menu.jpg")
//            .category(Category.JAPANESE)
//            .coordinate(new Coordinate("1.0", "50.0"))
//            .timeList(Arrays.asList(
//                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
//                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
//            ))
//            .offdayList(Arrays.asList(
//                new PlaceCloseTime("한글날", "월~금", false)
//            ))
//            .menuList(Arrays.asList(
//                new Menu(5000L, true, "Coffee"),
//                new Menu(7000L, false, "Cake")
//            ))
//            .build();
//
//        Place place3 = Place.builder()
//            .name("Place 3")
//            .pet(false)
//            .wifi(true)
//            .parking(false)
//            .fordisabled(true)
//            .nursery(false)
//            .smokingroom(false)
//            .address(new Address("Address 1", "Address 2", "Address 3"))
//            .menuImgUrl("menu.jpg")
//            .category(Category.JAPANESE)
//            .coordinate(new Coordinate("1.0", "100.0"))
//            .timeList(Arrays.asList(
//                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
//                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
//            ))
//            .offdayList(Arrays.asList(
//                new PlaceCloseTime("한글날", "월~금", false)
//            ))
//            .menuList(Arrays.asList(
//                new Menu(5000L, true, "Coffee"),
//                new Menu(7000L, false, "Cake")
//            ))
//            .build();
//
//        entityManager.persist(place1);
//        entityManager.persist(place2);
//        entityManager.persist(place3);
//
//        Influencer influencer1 = new Influencer("성시경", "가수", "img.url");
//
//        entityManager.persist(influencer1);
//
//        Video video1 = new Video("video.url", influencer1, place1);
//
//        entityManager.persist(video1);
    }

    @Test
    @DisplayName("거리 기반 장소 조회")
    public void test1() {
        // given
        /* Before Each */
        String longitude = "1.0";
        String latitude = "51.0";
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistance(
                longitude,
                latitude,
                pageable
        );
        System.out.println(foundPlaces);
        // Then
        assertThat(foundPlaces).hasSize(3);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 3");
        assertThat(foundPlaces.getContent().get(2).getName()).isEqualTo("Place 1");
    }

    @Test
    @DisplayName("필터링 NULL인 거리기반 장소조회")
    public void test2() {
        // given
        /* Before Each */
        String longitude = "1.0";
        String latitude = "51.0";
        List<String> categories = null;
        List<String> influencers = null;
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
                longitude,
                latitude,
                categories,
                influencers,
                pageable
        );
        // Then
        assertThat(foundPlaces).hasSize(3);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 3");
        assertThat(foundPlaces.getContent().get(2).getName()).isEqualTo("Place 1");

    }

    @Test
    @DisplayName("카테고리 필터링 거리기반 장소조회")
    public void test3() {
        // given
        /* Before Each */
        String longitude = "1.0";
        String latitude = "51.0";
        List<String> categories = Stream.of(Category.CAFE)
                .map(Enum::toString)  // Enum 값을 문자열로 변환
                .toList();
        List<String> influencers = null;
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
                longitude,
                latitude,
                categories,
                influencers,
                pageable
        );
        // Then
        assertThat(foundPlaces).hasSize(1);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 1");
    }

    @Test
    @DisplayName("인플루언서 필터링")
    public void test4() {
        // given
        /* Before Each */
        String longitude = "1.0";
        String latitude = "51.0";
        List<String> categories = null;
        List<String> influencers = List.of("성시경");
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
                longitude,
                latitude,
                categories,
                influencers,
                pageable
        );

        //then
        assertThat(foundPlaces).hasSize(1);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 1");
    }
}
