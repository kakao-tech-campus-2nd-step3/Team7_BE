package team7.inplace.place.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OffDay {
    private String holydayName;
    private String weekAndDay;
    private String temporaryHolidays;
}
