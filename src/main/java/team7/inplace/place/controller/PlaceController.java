package team7.inplace.place.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.place.domain.CategoryListDTO;
import team7.inplace.place.service.PlaceService;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }
    @GetMapping("/categories")
    public ResponseEntity<CategoryListDTO> getCategories() {
        CategoryListDTO response = placeService.getCategories();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
