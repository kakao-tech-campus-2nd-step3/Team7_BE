package team7.inplace.place.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OffDay {

    private String holidayName;
    private String weekAndDay;
    private String temporaryHolidays;

    private OffDay(String holydayName, String weekAndDay, String temporaryHolidays) {
        this.holydayName = holydayName;
        this.weekAndDay = weekAndDay;
        this.temporaryHolidays = temporaryHolidays;
    }

    public static OffDay of(String offDay) {
        String[] offDayArray = offDay.split("\\|");
        return new OffDay(offDayArray[0], offDayArray[1], offDayArray[2]);
    }
}
