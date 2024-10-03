package team7.inplace.place.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("가까운 장소 조회")
    public void 가까운_장소_조회() throws Exception {
        /*
        // given
        // then: 반환된 결과 검증
        Pageable pageable = PageRequest.of(0, 10);
        PlacesCoordinateCommand comm = new PlacesCoordinateCommand("123", "123", pageable);
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(comm);

        // 결과 값 검증
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 3");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 2");
        */
    }
}
