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

}
