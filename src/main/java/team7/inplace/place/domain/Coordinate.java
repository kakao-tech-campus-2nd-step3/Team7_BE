package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Coordinate {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longitude;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String latitude;
}
