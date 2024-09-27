package team7.inplace.place.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.place.application.CategoryService;
import team7.inplace.place.application.PlaceService;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.presentation.dto.CategoriesResponse;
import team7.inplace.place.presentation.dto.PlacesResponse;
import team7.inplace.place.presentation.dto.PlacesSearchParams;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PlacesResponse> getPlaces(
        @ModelAttribute PlacesSearchParams searchParams,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PlaceInfo> placeInfos = placeService.getPlacesWithinRadius(
            new PlacesCoordinateCommand(searchParams.getLongitude(), searchParams.getLatitude(),
                pageable));
        return new ResponseEntity<>(new PlacesResponse(placeInfos), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoriesResponse> getCategories() {
        List<CategoryInfo> categories = categoryService.getCategories();
        CategoriesResponse response = new CategoriesResponse(categories);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
