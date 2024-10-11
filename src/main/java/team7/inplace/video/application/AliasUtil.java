package team7.inplace.video.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import team7.inplace.place.domain.Category;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliasUtil {
    public static String makeAlias(String influencerName, Category category) {
        String alias = mapTemplateToCategory(category);
        return influencerName + " " + alias;
    }

    // 카테고리에 따라 템플릿을 매핑하는 메서드
    private static String mapTemplateToCategory(Category category) {
        return switch (category) {
            case CAFE -> Template.CAFE.getRandomTemplate();
            case WESTERN -> Template.WESTERN.getRandomTemplate();
            case JAPANESE -> Template.JAPANESE.getRandomTemplate();
            case KOREAN -> Template.KOREAN.getRandomTemplate();
            case RESTAURANT -> Template.RESTAURANT.getRandomTemplate();
            default -> Template.NONE.getRandomTemplate();
        };
    }
}
