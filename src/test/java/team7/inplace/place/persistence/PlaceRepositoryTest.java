package team7.inplace.place.persistence;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PlaceRepositoryTest {
/*
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlaceRepository placeRepository;


//     * 테스트 Place 좌표 (longitude, latitude)
//     * (10.0, 10.0) -> video1 -> 성시경
//     * (10.0, 50.0)
//     * (10.0, 100.0)
//     * (50.0, 50.0) -> video2 -> 아이유
//     *
//     * 테스트 좌표
//     * (10.0, 51.0)
//     *
//     * boundary 좌표
//     * 좌상단: (10.0, 60.0)
//     * 우하단: (50.0, 10.0)

    String topLeftLongitude = "10.0";
    String topLeftLatitude = "60.0";
    String bottomRightLongitude = "50.0";
    String bottomRightLatitude = "10.0";
    String longitude = "10.0";
    String latitude = "51.0";
    Pageable pageable = PageRequest.of(0, 10);

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
            .coordinate(new Coordinate("10.0", "10.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee", "menuImg.url"),
                new Menu(7000L, false, "Cake", "menuImg.url")
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
            .coordinate(new Coordinate("10.0", "50.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee", "menuImg.url"),
                new Menu(7000L, false, "Cake", "menuImg.url")
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
            .category(Category.CAFE)
            .coordinate(new Coordinate("10.0", "100.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee", "menuImg.url"),
                new Menu(7000L, false, "Cake", "menuImg.url")
            ))
            .build();

        Place place4 = Place.builder()
            .name("Place 4")
            .pet(false)
            .wifi(true)
            .parking(false)
            .fordisabled(true)
            .nursery(false)
            .smokingroom(false)
            .address(new Address("Address 1", "Address 2", "Address 3"))
            .menuImgUrl("menu.jpg")
            .category(Category.JAPANESE)
            .coordinate(new Coordinate("50.0", "50.0"))
            .timeList(Arrays.asList(
                new PlaceOpenTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceOpenTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .offdayList(Arrays.asList(
                new PlaceCloseTime("한글날", "월~금", false)
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee", "menuImg.url"),
                new Menu(7000L, false, "Cake", "menuImg.url")
            ))
            .build();

        entityManager.persist(place1);
        entityManager.persist(place2);
        entityManager.persist(place3);
        entityManager.persist(place4);

        Influencer influencer1 = new Influencer("성시경", "가수", "img.url");
        Influencer influencer2 = new Influencer("아이유", "가수", "img.rul");
        entityManager.persist(influencer1);
        entityManager.persist(influencer2);

        Video video1 = new Video("video.url", influencer1, place1);
        Video video2 = new Video("video.url", influencer2, place4);

        entityManager.persist(video1);
        entityManager.persist(video2);
    }

    @Test
    @DisplayName("거리 기반 장소 조회")
    public void test1() {
        // given

        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistance(
                longitude,
                latitude,
                pageable
        );
        System.out.println(foundPlaces);

        // Then
        assertThat(foundPlaces).hasSize(4);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 4");
        assertThat(foundPlaces.getContent().get(2).getName()).isEqualTo("Place 1");
        assertThat(foundPlaces.getContent().get(3).getName()).isEqualTo("Place 3");
    }

    @Test
    @DisplayName("필터링 NULL, boundary[(10, 60), (50, 10)]")
    public void test2() {

//         * Place 3(10.0, 100.0) 제외
        // given
        List<String> categories = null;
        List<String> influencers = null;

        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            categories,
            influencers,
            pageable
        );
        // Then
        assertThat(foundPlaces).hasSize(3);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 4");
        assertThat(foundPlaces.getContent().get(2).getName()).isEqualTo("Place 1");

    }

    @Test
    @DisplayName("카테고리(japan, cafe) 필터링")
    public void test3() {
        // given
        List<String> categories = Stream.of(Category.CAFE, Category.JAPANESE)
            .map(Enum::toString)  // Enum 값을 문자열로 변환
            .toList();
        List<String> influencers = null;
        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            categories,
            influencers,
            pageable
        );
        // Then
        assertThat(foundPlaces).hasSize(3);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(0).getCategory()).isEqualTo(Category.JAPANESE);
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 4");
        assertThat(foundPlaces.getContent().get(1).getCategory()).isEqualTo(Category.JAPANESE);
        assertThat(foundPlaces.getContent().get(2).getName()).isEqualTo("Place 1");
        assertThat(foundPlaces.getContent().get(2).getCategory()).isEqualTo(Category.CAFE);
    }

    @Test
    @DisplayName("카테고리(japan) 필터링")
    public void test4() {
        // given
        List<String> categories = Stream.of(Category.JAPANESE)
            .map(Enum::toString)
            .toList();
        List<String> influencers = null;
        // when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            categories,
            influencers,
            pageable
        );
        // Then
        assertThat(foundPlaces).hasSize(2);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 2");
        assertThat(foundPlaces.getContent().get(0).getCategory()).isEqualTo(Category.JAPANESE);
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 4");
        assertThat(foundPlaces.getContent().get(1).getCategory()).isEqualTo(Category.JAPANESE);
    }

    @Test
    @DisplayName("인플루언서(아이유, 성시경) 필터링")
    public void test5() {
        // given
        List<String> categories = null;
        List<String> influencers = List.of("성시경", "아이유");

        //when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            categories,
            influencers,
            pageable
        );

        //then
        assertThat(foundPlaces).hasSize(2);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 4");
        assertThat(foundPlaces.getContent().get(1).getName()).isEqualTo("Place 1");
    }

    @Test
    @DisplayName("인플루언서(성시경) 필터링")
    public void test6() {
        // given
        List<String> categories = null;
        List<String> influencers = List.of("성시경");

        //when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
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

    @Test
    @DisplayName("카테고리(Japanese), 인플루언서(아이유) 필터링")
    public void test7() {
        // given
        List<String> categories = Stream.of(Category.JAPANESE)
            .map(Enum::toString)
            .toList();
        List<String> influencers = List.of("아이유");

        //when
        Page<Place> foundPlaces = placeRepository.getPlacesByDistanceAndFilters(
            topLeftLongitude,
            topLeftLatitude,
            bottomRightLongitude,
            bottomRightLatitude,
            longitude,
            latitude,
            categories,
            influencers,
            pageable
        );

        //then
        assertThat(foundPlaces).hasSize(1);
        assertThat(foundPlaces.getContent().get(0).getName()).isEqualTo("Place 4");
    }

 */
}
