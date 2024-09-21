package team7.inplace.place.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.CategoryListResponse;
import team7.inplace.place.service.PlaceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/categories")
    public ResponseEntity<CategoryListResponse> getCategories() {
        List<Category> categories = placeService.getCategories();
        CategoryListResponse response = new CategoryListResponse(categories);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
