package team7.inplace.place.application.command;

import org.springframework.data.domain.Pageable;

public record PlacesCommand() {

    public record PlacesCoordinateCommand(
        String longitude,
        String latitude,
        Pageable pageable
    ) {

    }

    public record PlacesFilterParamsCommand(
        String categories,
        String influencers
    ) {

    }
}
