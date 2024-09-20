package team7.inplace.place.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;

@DataJpaTest
class PlaceRepositoryTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Test
    @DisplayName("Save and Find Test")
    public void 장소_저장_및_조회_테스트 () {
        // Given: Place 객체를 생성하고 저장
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

        Place savedPlace = placeRepository.save(place);

        // When: 저장된 ID로 조회
        Optional<Place> foundPlace = placeRepository.findById(savedPlace.getPlaceId());

        // Then: 조회된 Place가 저장된 Place와 일치하는지 확인
        assertThat(foundPlace).isPresent();
        assertThat(foundPlace.get().getName()).isEqualTo(savedPlace.getName());
        assertThat(foundPlace.get().getCategory()).isEqualTo(savedPlace.getCategory());
        assertThat(foundPlace.get().getLongitude()).isEqualTo(savedPlace.getLongitude());
    }
}
