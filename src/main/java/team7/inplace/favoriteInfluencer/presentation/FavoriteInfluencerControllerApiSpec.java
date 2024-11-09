package team7.inplace.favoriteInfluencer.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import team7.inplace.favoriteInfluencer.presentation.dto.InfluencerLikeRequest;
import team7.inplace.favoriteInfluencer.presentation.dto.InfluencerListLikeRequest;

public interface FavoriteInfluencerControllerApiSpec {

    @Operation(summary = "인플루언서 좋아요/좋아요 취소", description = "인플루언서를 좋아요하거나 좋아요 취소합니다.")
    ResponseEntity<Void> likeToInfluencer(@RequestBody InfluencerLikeRequest param);

    @Operation(summary = "여러 인플루언서 좋아요/좋아요 취소", description = "여러 인플루언서를 모두 좋아요하거나 모든 좋아요를 취소합니다.")
    ResponseEntity<Void> likeToManyInfluencer(@RequestBody InfluencerListLikeRequest request);
}
