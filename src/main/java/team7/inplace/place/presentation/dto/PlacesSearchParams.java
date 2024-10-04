package team7.inplace.place.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlacesSearchParams {

    private String longitude;

    private String latitude;

    private String categories;

    private String influencers;

    private String topLeftLongitude;

    private String topLeftLatitude;

    private String bottomRightLongitude;

    private String bottomRightLatitude;

}
