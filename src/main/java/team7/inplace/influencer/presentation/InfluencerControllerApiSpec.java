package team7.inplace.influencer.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import team7.inplace.influencer.presentation.dto.InfluencerLikeRequest;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;
import team7.inplace.influencer.presentation.dto.InfluencerRequest;

public interface InfluencerControllerApiSpec {

    @Operation(summary = "인플루언서들 리스트 반환", description = "토큰이 있는 경우 좋아요된 인플루언서가 먼저 반환됩니다.")
    ResponseEntity<InfluencerListResponse> getAllInfluencers();

    @Operation(summary = "인플루언서 등록", description = "새 인플루언서를 등록합니다.")
    ResponseEntity<Long> createInfluencer(@RequestBody InfluencerRequest request);

    @Operation(summary = "인플루언서 수정", description = "인플루언서를 수정합니다.")
    ResponseEntity<Long> updateInfluencer(@PathVariable Long id,
        @RequestBody InfluencerRequest request);

    @Operation(summary = "인플루언서 삭제", description = "인플루언서를 삭제합니다.")
    ResponseEntity<Long> deleteInfluencer(@PathVariable Long id);

    @Operation(summary = "인플루언서 좋아요/좋아요 취소", description = "인플루언서를 좋아요하거나 좋아요 취소합니다.")
    ResponseEntity<Void> likeToInfluencer(@RequestBody InfluencerLikeRequest param);
        
}
