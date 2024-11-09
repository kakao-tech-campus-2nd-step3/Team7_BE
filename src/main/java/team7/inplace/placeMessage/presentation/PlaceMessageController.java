package team7.inplace.placeMessage.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.placeMessage.application.PlaceMessageFacade;

@RestController
@RequiredArgsConstructor
public class PlaceMessageController implements PlaceMessageControllerApiSpec {

    private final PlaceMessageFacade placeMessageFacade;

    @GetMapping("/place-message/{place-id}")
    public ResponseEntity<Void> test(@PathVariable("place-id") Long placeId) {
        placeMessageFacade.sendPlaceMessage(placeId);
        return ResponseEntity.ok().build();
    }
}
