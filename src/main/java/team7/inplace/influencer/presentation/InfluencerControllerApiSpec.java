package team7.inplace.influencer.presentation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;
import team7.inplace.influencer.presentation.dto.InfluencerRequest;

public interface InfluencerControllerApiSpec {

    @Operation(summary = "인플루언서들 리스트 반환", description = "토큰 유무에 따라 좋아요한 인플루언서 반영 여부가 다릅니다.")
    ResponseEntity<InfluencerListResponse> getAllInfluencers();

    @Operation(summary = "인플루언서 등록", description = "새 인플루언서를 등록합니다.")
    ResponseEntity<Long> createInfluencer(@RequestBody InfluencerRequest request);

    @Operation(summary = "인플루언서 수정", description = "인플루언서를 수정합니다.")
    ResponseEntity<Long> updateInfluencer(@PathVariable Long id,
        @RequestBody InfluencerRequest request);

    @Operation(summary = "인플루언서 삭제", description = "인플루언서를 삭제합니다.")
    ResponseEntity<Long> deleteInfluencer(@PathVariable Long id);
}
