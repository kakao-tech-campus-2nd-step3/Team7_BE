package team7.inplace.place.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Coordinate;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceTime;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private PlaceRepository placeRepository;
    @InjectMocks
    private PlaceService placeService;

    private Place place1, place2, place3;
    private Video video1;
    private Pageable pageable;

    @BeforeEach
    public void init() {
        place1 = Place.builder()
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

        place2 = Place.builder()
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

        place3 = Place.builder()
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

        video1 = new Video("video.url", new Influencer("성시경", "가수", "img.url"), place1);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    @DisplayName("필터링 없이 가까운 장소 조회")
    public void getPlacesWithinRadius_NoFilter() {
        // Mock repository responses
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place3, place1), pageable, 3);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIds(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1));

        // Prepare filter params
        PlacesCoordinateCommand coordinateCommand = new PlacesCoordinateCommand("1.0", "51.0",
            pageable);
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(null, null);

        // Call the service
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // Assert results
        assertThat(result).hasSize(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 3");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 1");
    }

    @Test
    @DisplayName("카테고리 필터링(JAPANESE, CAFE)")
    public void getPlacesWithinRadius_FullCategoryFilter() {
        // Mock repository responses
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place3, place1), pageable, 3);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIds(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1));

        // Prepare filter params
        PlacesCoordinateCommand coordinateCommand = new PlacesCoordinateCommand("1.0", "51.0",
            pageable);
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("JAPANESE, CAFE",
            null);

        // Call the service
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // Assert results
        assertThat(result).hasSize(3);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 3");
        assertThat(result.getContent().get(2).placeName()).isEqualTo("Place 1");
    }

    @Test
    @DisplayName("카테고리 필터링(JAPANESE)")
    public void getPlacesWithinRadius_JapCategoryFilter() {
        // Mock repository responses
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place2, place3), pageable, 2);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIds(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1));

        // Prepare filter params
        PlacesCoordinateCommand coordinateCommand = new PlacesCoordinateCommand("1.0", "51.0",
            pageable);
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand("JAPANESE",
            null);

        // Call the service
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // Assert results
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 2");
        assertThat(result.getContent().get(1).placeName()).isEqualTo("Place 3");
    }

    @Test
    @DisplayName("인플루언서 필터링")
    public void getPlacesWithinRadius_InfluencerFilter() {
        // Mock repository responses
        Page<Place> placesPage = new PageImpl<>(Arrays.asList(place1), pageable, 1);
        when(placeRepository.getPlacesByDistanceAndFilters(any(), any(), any(), any(), any()))
            .thenReturn(placesPage);
        when(videoRepository.findByPlaceIds(
            placesPage.getContent().stream().map(Place::getId).toList())).thenReturn(
            Arrays.asList(video1));
        // Prepare filter params
        PlacesCoordinateCommand coordinateCommand = new PlacesCoordinateCommand("1.0", "51.0",
            pageable);
        PlacesFilterParamsCommand filterParams = new PlacesFilterParamsCommand(null, "성시경");

        // Call the service
        Page<PlaceInfo> result = placeService.getPlacesWithinRadius(coordinateCommand,
            filterParams);

        // Assert results
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).placeName()).isEqualTo("Place 1");
        assertThat(result.getContent().get(0).influencerName()).isEqualTo("성시경");
    }

}
