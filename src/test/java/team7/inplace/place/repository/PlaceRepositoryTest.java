package team7.inplace.place.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.influencer.persistence.InfluencerRepository;
import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Coordinate;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceTime;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

@DataJpaTest
@Transactional
class PlaceRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private InfluencerRepository influencerRepository;

    @Autowired
    private VideoRepository videoRepository;

    @BeforeEach
    public void init() {
        Place place1 = Place.builder()
            .name("Place 1")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.CAFE)
            .coordinate(new Coordinate("1.0", "1.0"))
            .timeList(Arrays.asList(
                new PlaceTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        Place place2 = Place.builder()
            .name("Place 2")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("1.0", "50.0"))
            .timeList(Arrays.asList(
                new PlaceTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        Place place3 = Place.builder()
            .name("Place 3")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("1.0", "100.0"))
            .timeList(Arrays.asList(
                new PlaceTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        entityManager.persist(place1);
        entityManager.persist(place2);
        entityManager.persist(place3);

        Influencer influencer1 = new Influencer("성시경", "가수", "img.url");

        entityManager.persist(influencer1);

        Video video1 = new Video("video.url", influencer1, place1);

        entityManager.persist(video1);
    }

    @Test
    public void 거리기반_장소조회_테스트() {
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
    public void 필터링_NULL인_거리기반_장소조회_테스트() {
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
    public void 카테고리_필터링_거리기반_장소조회_테스트() {
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
    public void 인플루언서_필터링() {
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
