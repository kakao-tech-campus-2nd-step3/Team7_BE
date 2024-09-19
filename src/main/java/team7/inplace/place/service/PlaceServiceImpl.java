package team7.inplace.place.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import team7.inplace.place.domain.Category;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Override
    public List<String> getCategories() {
        return Arrays.stream(Category.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }
}
