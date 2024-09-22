package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Menu {

    @Column(columnDefinition = "NUMBER")
    private Long price;

    @ColumnDefault("false")
    @Column
    private boolean recommend;

    @Column(length = 50)
    private String menuName;

}
