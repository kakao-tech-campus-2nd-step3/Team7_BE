package team7.inplace.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team7.inplace.place.application.dto.PlaceInfo;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Address {

    @Column(nullable = false, length = 50)
    private String address1;

    @Column(nullable = false, length = 50)
    private String address2;

    @Column(nullable = false, length = 50)
    private String address3;

    public PlaceInfo.AddressInfo getAddressInfo() {
        return new PlaceInfo.AddressInfo(address1, address2, address3);
    }
}
