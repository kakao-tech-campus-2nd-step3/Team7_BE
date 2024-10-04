package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlaceOpenTime {

    @Column(length = 50, nullable = false)
    private String timeName;

    @Column(length = 50, nullable = false)
    private String timeSe;

    @Column(length = 50, nullable = false)
    private String dayOfWeek;
}
