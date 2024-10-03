package team7.inplace.place.application;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Page<PlaceInfo> getPlacesWithinRadius(
        PlacesCoordinateCommand placesCoordinateCommand,
        PlacesFilterParamsCommand placesFilterParamsCommand) {

        // categories와 influencers 필터 처리
        List<String> categoryFilters = null;
        List<String> influencerFilters = null;

        // 필터 값이 있을 경우에만 split 처리
        if (placesFilterParamsCommand.categories() != null
            && !placesFilterParamsCommand.categories().isEmpty()) {
            categoryFilters = Arrays.stream(placesFilterParamsCommand.categories().split(","))
                .toList();
        }

        if (placesFilterParamsCommand.influencers() != null
            && !placesFilterParamsCommand.influencers().isEmpty()) {
            influencerFilters = Arrays.stream(placesFilterParamsCommand.influencers().split(","))
                .toList();
        }
        // 주어진 좌표로 장소를 찾고, 해당 페이지의 결과를 가져옵니다.
        Page<Place> placesPage = getPlacesByDistance(placesCoordinateCommand, categoryFilters,
            influencerFilters);

        return placesPage.map(PlaceInfo::of);
    }

    private Page<Place> getPlacesByDistance(
        PlacesCoordinateCommand placesCoordinateCommand,
        List<String> categoryFilters,
        List<String> influencerFilters
    ) {
        return placeRepository.getPlacesByDistanceAndFilters(
            placesCoordinateCommand.latitude(),
            placesCoordinateCommand.longitude(),
            categoryFilters != null && !categoryFilters.isEmpty() ? categoryFilters : null,
            influencerFilters != null && !influencerFilters.isEmpty() ? influencerFilters : null,
            placesCoordinateCommand.pageable());
    }

}
