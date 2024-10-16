package team7.inplace.influencer.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.influencer.application.InfluencerService;
import team7.inplace.influencer.application.dto.InfluencerCommand;
import team7.inplace.influencer.application.dto.InfluencerInfo;
import team7.inplace.influencer.presentation.dto.InfluencerLikeRequest;
import team7.inplace.influencer.presentation.dto.InfluencerListResponse;
import team7.inplace.influencer.presentation.dto.InfluencerRequest;
import team7.inplace.influencer.presentation.dto.InfluencerResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/influencers")
public class InfluencerController implements InfluencerControllerApiSpec {

    private final InfluencerService influencerService;

    @GetMapping()
    public ResponseEntity<InfluencerListResponse> getAllInfluencers() {
        List<InfluencerInfo> influencersDtoList = influencerService.getAllInfluencers();
        List<InfluencerResponse> influencers = influencersDtoList.stream()
            .map(InfluencerResponse::from)
            .toList();
        InfluencerListResponse response = new InfluencerListResponse(influencers);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> createInfluencer(@RequestBody InfluencerRequest request) {
        InfluencerCommand influencerCommand = InfluencerRequest.to(request);
        Long savedId = influencerService.createInfluencer(influencerCommand);

        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInfluencer(
        @PathVariable Long id,
        @RequestBody InfluencerRequest request
    ) {
        InfluencerCommand influencerCommand = new InfluencerCommand(
            request.influencerName(),
            request.influencerImgUrl(),
            request.influencerJob()
        );
        Long updatedId = influencerService.updateInfluencer(id, influencerCommand);

        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteInfluencer(@PathVariable Long id) {
        influencerService.deleteInfluencer(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/likes")
    public ResponseEntity<Void> likeToInfluencer(@RequestBody InfluencerLikeRequest param) {
        influencerService.likeToInfluencer(param);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
