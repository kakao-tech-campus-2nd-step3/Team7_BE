package team7.inplace.video.presentation.dto;

import lombok.Getter;

@Getter
public record VideoSearchParams(
        String longitude,
        String latitude
) {
}
