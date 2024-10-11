package team7.inplace.place.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    CAFE("카페"),
    WESTERN("양식"),
    JAPANESE("일식"),
    KOREAN("한식"),
    RESTAURANT("음식점"),
    NONE("없음");

    private final String name;

    public static Category of(String name) {
        for (Category category : values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }
        return NONE;
    }
}
