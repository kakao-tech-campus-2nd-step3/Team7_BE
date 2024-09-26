package team7.inplace.place.application;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public Page<PlaceInfo> getPlacesWithinRadius(
        PlacesCoordinateCommand placesCoordinateCommand) {

        // 주어진 좌표로 장소를 찾고, 해당 페이지의 결과를 가져옵니다.
        Page<Place> placesPage = getPlacesByDistance(placesCoordinateCommand);

        // Place 객체를 PlaceInfo로 변환합니다.
        List<PlaceInfo> placeInfoList = placesPage.getContent().stream()
            .map(Place::getPlaceInfo)
            .toList();

        // PageImpl을 사용하여 Page<PlaceInfo>로 감쌉니다.
        return new PageImpl<>(placeInfoList, placesPage.getPageable(),
            placesPage.getTotalElements());
    }
/*
    private List<PlaceInfo> getPlaceInfos(List<Long> placeIds) {
        List<PlaceInfo> placeInfos = new ArrayList<>();

        for (Long placeId : placeIds) {
            Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("placeId 없음."));

            AddressInfo addressInfo = place.getAddress().getAddressInfo();

            CoordinateInfo coordinateInfo = place.getCoordinate().getCoordinateInfo();

            PlaceInfo placeInfo = new PlaceInfo(placeId,
                place.getName(),
                addressInfo,
                place.getCategory().toString(),
                coordinateInfo);
            placeInfos.add(placeInfo);
        }
        return placeInfos;
    }
*/

    private Page<Place> getPlacesByDistance(PlacesCoordinateCommand comm) {
        return placeRepository.getPlacesByDistance(
            comm.latitude(),
            comm.longitude(),
            comm.pageable());
    }

    public List<CategoryInfo> getCategories() {
        return Arrays.stream(Category.values()).map(category -> new CategoryInfo(category.name()))
            .toList();
    }

}
