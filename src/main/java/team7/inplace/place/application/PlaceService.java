package team7.inplace.place.application;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.dto.CategoryInfo;
import team7.inplace.place.domain.Category;

@Service
public class PlaceService {

    public List<CategoryInfo> getCategories() {
        return Arrays.stream(Category.values())
            .map(category -> new CategoryInfo(category.name()))
            .toList();
    }
}
