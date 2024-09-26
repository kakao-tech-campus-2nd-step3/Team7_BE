package team7.inplace.influencer.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerDto;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;

@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final InfluencerService influencerService;

    @GetMapping("/influencers")
    public ResponseEntity<List<InfluencerListResponse>> getAllInfluencers() {
        List<InfluencerDto> influencersDtoList = influencerService.getAllInfluencers();
        List<InfluencerListResponse> influencers = influencersDtoList.stream()
            .map(InfluencerListResponse::convertToResponse)
            .toList();
        return new ResponseEntity<>(influencers, HttpStatus.OK);
    }

}
