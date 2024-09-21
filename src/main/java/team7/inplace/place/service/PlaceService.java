package team7.inplace.place.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import team7.inplace.place.domain.Category;

@Service
public class PlaceService {

    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}
