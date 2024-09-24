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
public class Address {

    @Column(nullable = false, length = 50)
    private String address1;

    @Column(nullable = false, length = 50)
    private String address2;

    @Column(nullable = false, length = 50)
    private String address3;
}
