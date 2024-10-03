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
        // 더미 데이터 생성
//        Place place1 = Place.builder()
//                .name("Place 1")
//                .pet(false)
//                .wifi(true)
//                .parking(false)
//                .fordisabled(true)
//                .nursery(false)
//                .smokingroom(false)
//                .address(new Address("Address 1", "Address 2", "Address 3"))
//                .menuImgUrl("menu.jpg")
//                .category(Category.CAFE)
//                .coordinate(new Coordinate("127.0", "37.0"))
//                .timeList(Arrays.asList(
//                        new OpenPeriod("Opening Hours", "9:00 AM", "Monday"),
//                        new OpenPeriod("Closing Hours", "10:00 PM", "Monday")
//                ))
//                .menuList(Arrays.asList(
//                        new Menu(5000L, true, "Coffee"),
//                        new Menu(7000L, false, "Cake")
//                ))
//                .build();
//
//        Place place2 = Place.builder()
//                .name("Place 2")
//                .pet(false)
//                .wifi(true)
//                .parking(false)
//                .fordisabled(true)
//                .nursery(false)
//                .smokingroom(false)
//                .address(new Address("Address 1", "Address 2", "Address 3"))
//                .menuImgUrl("menu.jpg")
//                .category(Category.CAFE)
//                .coordinate(new Coordinate("137.0", "10.0"))
//                .timeList(Arrays.asList(
//                        new OpenPeriod("Opening Hours", "9:00 AM", "Monday"),
//                        new OpenPeriod("Closing Hours", "10:00 PM", "Monday")
//                ))
//                .menuList(Arrays.asList(
//                        new Menu(5000L, true, "Coffee"),
//                        new Menu(7000L, false, "Cake")
//                ))
//                .build();
//
//        Place place3 = Place.builder()
//                .name("Place 3")
//                .pet(false)
//                .wifi(true)
//                .parking(false)
//                .fordisabled(true)
//                .nursery(false)
//                .smokingroom(false)
//                .address(new Address("Address 1", "Address 2", "Address 3"))
//                .menuImgUrl("menu.jpg")
//                .category(Category.CAFE)
//                .coordinate(new Coordinate("126.0", "30.0"))
//                .timeList(Arrays.asList(
//                        new OpenPeriod("Opening Hours", "9:00 AM", "Monday"),
//                        new OpenPeriod("Closing Hours", "10:00 PM", "Monday")
//                ))
//                .menuList(Arrays.asList(
//                        new Menu(5000L, true, "Coffee"),
//                        new Menu(7000L, false, "Cake")
//                ))
//                .build();
//
//        // 저장
//        placeRepository.save(place1);
//        placeRepository.save(place2);
//        placeRepository.save(place3);
    }
}
