package team7.inplace.favoriteInfluencer.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.favoriteInfluencer.application.FavoriteInfluencerService;
import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerCommand;
import team7.inplace.favoriteInfluencer.application.dto.FavoriteInfluencerListCommand;
import team7.inplace.favoriteInfluencer.presentation.dto.InfluencerLikeRequest;
import team7.inplace.favoriteInfluencer.presentation.dto.InfluencerListLikeRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/influencers")
public class FavoriteInfluencerController implements FavoriteInfluencerControllerApiSpec {

    private final FavoriteInfluencerService favoriteInfluencerService;

    @PostMapping("/likes")
    public ResponseEntity<Void> likeToInfluencer(@RequestBody InfluencerLikeRequest request) {
        FavoriteInfluencerCommand command = request.toCommand();
        favoriteInfluencerService.likeToInfluencer(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/multiple/likes")
    public ResponseEntity<Void> likeToManyInfluencer(
        @RequestBody InfluencerListLikeRequest request) {
        FavoriteInfluencerListCommand command = request.toCommand();
        favoriteInfluencerService.likeToManyInfluencer(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
