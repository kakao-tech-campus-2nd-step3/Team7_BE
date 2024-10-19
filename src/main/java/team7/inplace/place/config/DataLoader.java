package team7.inplace.place.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team7.inplace.place.persistence.PlaceRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final PlaceRepository placeRepository;

    public DataLoader(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        Place place1 = new Place("Place 1",
//            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
//            "menuImg.url", "카페",
//            "Address 1|Address 2|Address 3",
//            "10.0", "10.0",
//            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
//            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
//            Arrays.asList("삼겹살|5000|false|menu.url|description",
//                "돼지찌개|7000|true|menu.url|description"),
//            LocalDateTime.of(2024, 3, 28, 5, 30),
//            Arrays.asList(
//                "menuBoard1.url",
//                "menuBoard2.url"
//            )
//        );
//
//        Place place2 = new Place("Place 2",
//            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
//            "menuImg.url", "일식",
//            "Address 1|Address 2|Address 3",
//            "10.0", "50.0",
//            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
//            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
//            Arrays.asList("삼겹살|5000|false|menu.url|description",
//                "돼지찌개|7000|true|menu.url|description"),
//            LocalDateTime.of(2024, 3, 28, 5, 30),
//            Arrays.asList(
//                "menuBoard1.url",
//                "menuBoard2.url"
//            )
//        );
//
//        Place place3 = new Place("Place 3",
//            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
//            "menuImg.url", "카페",
//            "Address 1|Address 2|Address 3",
//            "10.0", "100.0",
//            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
//            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
//            Arrays.asList("삼겹살|5000|false|menu.url|description",
//                "돼지찌개|7000|true|menu.url|description"),
//            LocalDateTime.of(2024, 3, 28, 5, 30),
//            Arrays.asList(
//                "menuBoard1.url",
//                "menuBoard2.url"
//            )
//        );
//
//        Place place4 = new Place("Place 4",
//            "\"wifi\": true, \"pet\": false, \"parking\": false, \"forDisabled\": true, \"nursery\": false, \"smokingRoom\": false}",
//            "menuImg.url", "일식",
//            "Address 1|Address 2|Address 3",
//            "50.0", "50.0",
//            Arrays.asList("한글날|수|N", "크리스마스|수|Y"),
//            Arrays.asList("오픈 시간|9:00 AM|월", "닫는 시간|6:00 PM|월"),
//            Arrays.asList("삼겹살|5000|false|menu.url|description",
//                "돼지찌개|7000|true|menu.url|description"),
//            LocalDateTime.of(2024, 3, 28, 5, 30),
//            Arrays.asList(
//                "menuBoard1.url",
//                "menuBoard2.url"
//            )
//        );
//
//        placeRepository.save(place1);
//        placeRepository.save(place2);
//        placeRepository.save(place3);
//        placeRepository.save(place4);
    }
}
