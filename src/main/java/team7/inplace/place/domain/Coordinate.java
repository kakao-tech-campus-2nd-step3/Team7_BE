package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {
    @Column(nullable = false, columnDefinition = "TEXT")
    private String longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String latitude;
}
