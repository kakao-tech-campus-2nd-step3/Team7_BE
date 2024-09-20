package team7.inplace.place.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import team7.inplace.place.domain.Category;
import team7.inplace.place.domain.CategoryListDTO;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Override
    public CategoryListDTO getCategories() {
        List<Category>  categories = Arrays.stream(Category.values()).toList();
        return new CategoryListDTO(categories);
    }
}
