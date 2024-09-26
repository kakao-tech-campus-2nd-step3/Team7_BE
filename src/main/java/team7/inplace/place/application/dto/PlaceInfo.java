package team7.inplace.place.application.dto;


public record PlaceInfo(Long placeId,
                        String placeName,
                        AddressInfo address,
                        String category,
                        String influencerName,
                        String longitude,
                        String latitude,
                        Boolean likes) {


    // influencer, likes 추가 예정
    public record AddressInfo(
        String address1,
        String address2,
        String address3
    ) {

    }
}
