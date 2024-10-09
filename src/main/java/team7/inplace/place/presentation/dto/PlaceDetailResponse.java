package team7.inplace.place.presentation.dto;

import com.fasterxml.jackson.databind.JsonNode;
import team7.inplace.place.application.dto.PlaceDetailInfo;
import team7.inplace.place.application.dto.PlaceDetailInfo.MenuInfos;
import team7.inplace.place.application.dto.PlaceDetailInfo.OpenHour;
import team7.inplace.place.application.dto.PlaceDetailInfo.PlaceLikes;
import team7.inplace.place.application.dto.PlaceInfo.AddressInfo;

public record PlaceDetailResponse(
    Long placeId,
    String placeName,
    AddressInfo address,
    String category,
    String influencerName,
    String longitude,
    String latitude,
    Boolean likes,
    JsonNode facilityInfo,
    MenuInfos menuInfos,
    OpenHour openHour,
    PlaceLikes placeLikes,
    String videoUrl
) {

    public static PlaceDetailResponse from(PlaceDetailInfo info) {
        return new PlaceDetailResponse(
            info.placeInfo().placeId(),
            info.placeInfo().placeName(),
            info.placeInfo().address(),
            info.placeInfo().category(),
            info.placeInfo().influencerName(),
            info.placeInfo().longitude(),
            info.placeInfo().latitude(),
            info.placeInfo().likes(),
            info.facilityInfo(),
            info.menuInfos(),
            info.openHour(),
            info.placeLikes(),
            info.videoUrl()
        );
    }
}
