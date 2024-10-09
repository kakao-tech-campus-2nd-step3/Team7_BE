package team7.inplace.influencer.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;
import team7.inplace.influencer.presentation.dto.InfluencerResponse;

@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final InfluencerService influencerService;

    @GetMapping("/influencers")
    public ResponseEntity<InfluencerListResponse> getAllInfluencers() {
        List<InfluencerInfo> influencersDtoList = influencerService.getAllInfluencers();
        List<InfluencerResponse> influencers = influencersDtoList.stream()
            .map(InfluencerResponse::from)
            .toList();
        InfluencerListResponse response = new InfluencerListResponse(influencers);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
