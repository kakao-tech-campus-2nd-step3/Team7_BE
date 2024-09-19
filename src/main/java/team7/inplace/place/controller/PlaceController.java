package team7.inplace.place.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team7.inplace.place.domain.Category;
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
    public ResponseEntity<Map<String, CategoryListDTO>> getCategories() {
        CategoryListDTO categories = placeService.getCategories();

        Map<String, CategoryListDTO> response = new HashMap<>();
        response.put("categories", categories);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
