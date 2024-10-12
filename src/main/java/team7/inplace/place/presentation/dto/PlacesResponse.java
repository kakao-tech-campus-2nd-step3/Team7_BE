package team7.inplace.place.presentation.dto;

import java.util.List;
import org.springframework.data.domain.Page;
import team7.inplace.place.application.dto.PlaceInfo;

public record PlacesResponse(List<PlaceInfo> places) {

    public static PlacesResponse of(Page<PlaceInfo> places) {
        return new PlacesResponse(places.getContent());
    }
}
