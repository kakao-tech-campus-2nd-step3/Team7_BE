package team7.inplace.place.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.place.presentation.dto.PlaceDetailResponse;
import team7.inplace.place.presentation.dto.PlaceLikeRequest;
import team7.inplace.place.presentation.dto.PlacesResponse;
import team7.inplace.place.presentation.dto.ReviewRequest;
import team7.inplace.place.presentation.dto.ReviewResponse;

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

    @Operation(summary = "장소에 좋아요 누르기", description = "userId와 placeId를 연동하여 장소에 좋아요를 표시합니다.")
    public ResponseEntity<Void> likeToPlace(
        @RequestBody PlaceLikeRequest param
    );

    @Operation(summary = "특정 장소 리뷰 추가", description = "특정 장소에 리뷰를 추가합니다. 사용자 별로 한 장소에 하나의 리뷰만 가능합니다.")
    public ResponseEntity<Void> createReview(
        @PathVariable("id") Long placeId,
        @RequestBody ReviewRequest request
    );

    @Operation(summary = "특정 장소 리뷰 조회", description = "페이지네이션이 적용된 특정 장소 리뷰를 조회합니다.")
    public ResponseEntity<Page<ReviewResponse>> getReviews(
        @PathVariable("id") Long placeId,
        @PageableDefault(page = 0, size = 10) Pageable pageable
    );
}
