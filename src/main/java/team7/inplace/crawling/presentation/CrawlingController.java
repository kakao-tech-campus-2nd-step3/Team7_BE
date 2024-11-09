package team7.inplace.crawling.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.crawling.application.CrawlingFacade;

@RestController
@RequestMapping("/crawling")
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingFacade crawlingFacade;

    @PostMapping("/video/{videoId}/place/{placeUUID}")
    public ResponseEntity<Void> addPlaceInfo(@PathVariable Long videoId, @PathVariable Long placeUUID) {
        crawlingFacade.addPlaceInfo(videoId, placeUUID);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
