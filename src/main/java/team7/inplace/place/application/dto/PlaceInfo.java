package team7.inplace.place.application.dto;


import team7.inplace.place.domain.Address;
import team7.inplace.place.domain.Place;

public record PlaceInfo(
    Long placeId,
    String placeName,
    AddressInfo address,
    String category,
    String influencerName,
    String longitude,
    String latitude,
    Boolean likes
) {


    // influencer, likes 추가 예정
    public record AddressInfo(
        String address1,
        String address2,
        String address3
    ) {

        public static AddressInfo of(Address address) {
            return new PlaceInfo.AddressInfo(
                address.getAddress1(),
                address.getAddress2(),
                address.getAddress3()
            );
        }
    }

    public static PlaceInfo of(Place place) {
        return new PlaceInfo(
            place.getId(),
            place.getName(),
            AddressInfo.of(place.getAddress()),
            place.getCategory().toString(),
            null,
            place.getCoordinate().getLongitude(),
            place.getCoordinate().getLatitude(),
            null
        );
    }
}
