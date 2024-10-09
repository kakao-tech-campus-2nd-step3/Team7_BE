package team7.inplace.place.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
public class Coordinate {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String latitude;

    private Coordinate(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static Coordinate of(String longitude, String latitude) {
        return new Coordinate(longitude, latitude);
    }
}
