package team7.inplace.place.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Page<PlaceInfo> getPlacesWithinRadius(
        PlacesCoordinateCommand placesCoordinateCommand) {

        // 주어진 좌표로 장소를 찾고, 해당 페이지의 결과를 가져옵니다.
        Page<Place> placesPage = getPlacesByDistance(placesCoordinateCommand);

        return placesPage.map(PlaceInfo::of);
    }
    
    private Page<Place> getPlacesByDistance(PlacesCoordinateCommand comm) {
        return placeRepository.getPlacesByDistance(
            comm.latitude(),
            comm.longitude(),
            comm.pageable());
    }

}
