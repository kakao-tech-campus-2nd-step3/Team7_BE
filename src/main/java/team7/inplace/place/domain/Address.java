package team7.inplace.place.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class Address {
    @Column(nullable = false, length = 50)
    private String address1;

    @Column(nullable = false, length = 50)
    private String address2;

    @Column(nullable = false, length = 50)
    private String address3;

    private Address(String address1, String address2, String address3) {
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
    }

    public static Address of(String address) {
        String[] split = address.split("\\|");
        return new Address(split[0], split[1], split[2]);
    }
}
