package team7.inplace.place.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.place.presentation.dto.PlaceDetailResponse;
import team7.inplace.place.presentation.dto.PlacesResponse;

public interface PlaceControllerApiSpec {

    @Operation(summary = "장소 조회", description = "위치 기반으로 반경 내의 장소 목록을 조회합니다.")
    public ResponseEntity<PlacesResponse> getPlaces(
        @RequestParam String longitude,
        @RequestParam String latitude,
        @RequestParam String topLeftLongitude,
        @RequestParam String topLeftLatitude,
        @RequestParam String bottomRightLongitude,
        @RequestParam String bottomRightLatitude,
        @RequestParam(required = false) String categories,
        @RequestParam(required = false) String influencers,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @Operation(summary = "카테고리 조회", description = "장소의 카테고리 목록을 조회합니다.")
    public ResponseEntity<CategoriesResponse> getCategories();

    @Operation(summary = "장소 상세 조회", description = "장소 ID를 통해 특정 장소의 상세 정보를 조회합니다.")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(
        @PathVariable("id") Long placeId
    );
}
