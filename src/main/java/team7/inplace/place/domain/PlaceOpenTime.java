package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class PlaceTime {

    @Column(length = 50)
    private String timeName;

    @Column(length = 50)
    private String timeSe;

    @Column(length = 50)
    private String dayOfWeek;
}
