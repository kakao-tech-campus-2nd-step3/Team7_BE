package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    private Long price;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean recommend;

    @Column(length = 50, nullable = false)
    private String menuName;

    @Column(length = 50)
    private String menuImgUrl;
    
    private Menu(Long price, boolean recommend, String menuName) {
        this.price = price;
        this.recommend = recommend;
        this.menuName = menuName;
    }

    public static Menu of(String menu) {
        String[] menus = menu.split("\\|");

        return new Menu(Long.parseLong(menus[1]), Boolean.parseBoolean(menus[2]), menus[0]);
    }
}
