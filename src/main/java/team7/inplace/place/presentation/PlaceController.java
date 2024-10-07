package team7.inplace.place.presentation;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.place.application.CategoryService;
import team7.inplace.place.application.PlaceService;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.place.presentation.dto.PlaceDetailResponse;
import team7.inplace.place.presentation.dto.PlacesResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;
    private final CategoryService categoryService;

    @Operation(summary = "장소 조회", description = "위치 기반으로 반경 내의 장소 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<PlacesResponse> getPlaces(
        @RequestParam String longitude,
        @RequestParam String latitude,
        @RequestParam String topLeftLongitude,
        @RequestParam String topLeftLatitude,
        @RequestParam String bottomRightLongitude,
        @RequestParam String bottomRightLatitude,
        @RequestParam(required = false) String categories,
        @RequestParam(required = false) String influencers,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        // 위치기반 조회
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaceInfo> placeInfos = placeService.getPlacesWithinRadius(
            new PlacesCoordinateCommand(
                topLeftLongitude,
                topLeftLatitude,
                bottomRightLongitude,
                bottomRightLatitude,
                longitude,
                latitude,
                pageable
            ),
            new PlacesFilterParamsCommand(
                categories,
                influencers
            )
        );
        return new ResponseEntity<>(PlacesResponse.of(placeInfos), HttpStatus.OK);
    }

    @Operation(summary = "카테고리 조회", description = "장소의 카테고리 목록을 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<CategoriesResponse> getCategories() {
        List<CategoryInfo> categories = categoryService.getCategories();
        CategoriesResponse response = new CategoriesResponse(categories);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "장소 상세 조회", description = "장소 ID를 통해 특정 장소의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDetailResponse> getPlaceDetail(
        @PathVariable("id") Long placeId
    ) {
        PlaceDetailResponse response = PlaceDetailResponse.from(
            placeService.getPlaceDetailInfo(placeId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
