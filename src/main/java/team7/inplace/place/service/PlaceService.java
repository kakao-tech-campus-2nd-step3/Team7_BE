package team7.inplace.place.service;

import java.util.List;
import org.springframework.stereotype.Service;
import team7.inplace.place.domain.CategoryListDTO;

public interface PlaceService {
    CategoryListDTO getCategories();
}
