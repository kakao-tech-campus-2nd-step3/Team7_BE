package team7.inplace.influencer.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import team7.inplace.influencer.presentation.dto.InfluencerNameResponse;
import team7.inplace.influencer.presentation.dto.InfluencerRequest;
import team7.inplace.influencer.presentation.dto.InfluencerResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/influencers")
public class InfluencerController implements InfluencerControllerApiSpec {

    private final InfluencerService influencerService;

    @GetMapping()
    public ResponseEntity<Page<InfluencerResponse>> getAllInfluencers(
        @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<InfluencerResponse> influencers = influencerService.getAllInfluencers(pageable)
            .map(InfluencerResponse::from);

        return new ResponseEntity<>(influencers, HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<List<InfluencerNameResponse>> getAllInfluencerNames() {
        List<InfluencerNameResponse> names = influencerService.getAllInfluencerNames().stream()
            .map(InfluencerNameResponse::from)
            .toList();
        return new ResponseEntity<>(names, HttpStatus.OK);
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
}
