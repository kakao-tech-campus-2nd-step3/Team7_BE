package team7.inplace.place.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceTime;

@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Test
    @DisplayName("Save and Find Test")
    public void 장소_저장_및_조회_테스트 () {
        // Given
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
        Optional<Place> foundPlace = placeRepository.findById(savedPlace.getPlaceId());

        // Then
        assertThat(foundPlace).isPresent();
        assertThat(foundPlace.get().getName()).isEqualTo(savedPlace.getName());
        assertThat(foundPlace.get().getCategory()).isEqualTo(savedPlace.getCategory());
        assertThat(foundPlace.get().getLongitude()).isEqualTo(savedPlace.getLongitude());
        assertThat(foundPlace.get().getMenuList()).isEqualTo(savedPlace.getMenuList());
        assertThat(foundPlace.get().getTimeList()).isEqualTo(savedPlace.getTimeList());
    }
}
