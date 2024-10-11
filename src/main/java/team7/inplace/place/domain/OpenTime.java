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
public class OpenTime {

    private String timeName;
    private String timeSE;
    private String dayOfWeek;

    private OpenTime(String timeName, String timeSE, String dayOfWeek) {
        this.timeName = timeName;
        this.timeSE = timeSE;
        this.dayOfWeek = dayOfWeek;
    }

    public static OpenTime of(String openTime) {
        String[] time = openTime.split("\\|");
        return new OpenTime(time[0], time[1], time[2]);
    }
}
