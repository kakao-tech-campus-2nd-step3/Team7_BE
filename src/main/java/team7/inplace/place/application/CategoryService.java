package team7.inplace.place.application;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.domain.Category;
import team7.inplace.place.persistence.PlaceRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final PlaceRepository placeRepository;

    public List<CategoryInfo> getCategories() {
        return Arrays.stream(Category.values()).map(category -> new CategoryInfo(category.name()))
            .toList();
    }
}
