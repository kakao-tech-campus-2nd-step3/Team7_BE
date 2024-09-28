package team7.inplace.place.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Coordinate;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceTime;
import team7.inplace.place.persistence.PlaceRepository;

@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;

    @Test
    @DisplayName("Save and Find Test")
    public void 장소_저장_및_조회_테스트() {
        // Given
        Place place = Place.builder()
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
                new PlaceTime("Opening Hours", "9:00 AM", "Monday"),
                new PlaceTime("Closing Hours", "10:00 PM", "Monday")
            ))
            .menuList(Arrays.asList(
                new Menu(5000L, true, "Coffee"),
                new Menu(7000L, false, "Cake")
            ))
            .build();

        Place savedPlace = placeRepository.save(place);

        // When
        Optional<Place> foundPlace = placeRepository.findById(savedPlace.getId());

        // Then
        assertThat(foundPlace).isPresent();
        assertThat(foundPlace.get().getName()).isEqualTo(savedPlace.getName());
        assertThat(foundPlace.get().getCategory()).isEqualTo(savedPlace.getCategory());
        assertThat(foundPlace.get().getCoordinate()).isEqualTo(savedPlace.getCoordinate());
        assertThat(foundPlace.get().getMenuList()).isEqualTo(savedPlace.getMenuList());
        assertThat(foundPlace.get().getTimeList()).isEqualTo(savedPlace.getTimeList());
    }
}
