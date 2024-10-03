package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlaceCloseTime {

    @Column(length = 50, nullable = false)
    private String holidayName;

    @Column(length = 50, nullable = false)
    private String weekAndDay;

    @ColumnDefault("false")
    @Column(nullable = false)
    private boolean temporaryHolidays;
}
