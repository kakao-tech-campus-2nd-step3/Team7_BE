package team7.inplace.place.application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import team7.inplace.place.application.command.PlacesCommand.PlacesCoordinateCommand;
import team7.inplace.place.application.command.PlacesCommand.PlacesFilterParamsCommand;
import team7.inplace.place.application.dto.PlaceDetailInfo;
import team7.inplace.place.application.dto.PlaceInfo;
import team7.inplace.place.domain.Place;
import team7.inplace.place.persistence.PlaceRepository;
import team7.inplace.video.domain.Video;
import team7.inplace.video.persistence.VideoRepository;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final VideoRepository videoRepository;

    public Page<PlaceInfo> getPlacesWithinRadius(
        PlacesCoordinateCommand placesCoordinateCommand,
        PlacesFilterParamsCommand placesFilterParamsCommand) {

        // categories와 influencers 필터 처리
        List<String> categoryFilters = null;
        List<String> influencerFilters = null;

        // 필터 값이 있을 경우에만 split 처리
        if (placesFilterParamsCommand.isCategoryFilterExists()) {
            categoryFilters = Arrays.stream(placesFilterParamsCommand.categories().split(","))
                .toList();
        }

        if (placesFilterParamsCommand.isInfluencerFilterExists()) {
            influencerFilters = Arrays.stream(placesFilterParamsCommand.influencers().split(","))
                .toList();
        }

        // 주어진 좌표로 장소를 찾고, 해당 페이지의 결과를 가져옵니다.
        Page<Place> placesPage = getPlacesByDistance(placesCoordinateCommand, categoryFilters,
            influencerFilters);

        // Place ID 목록 추출
        List<Long> placeIds = placesPage.getContent().stream()
            .map(Place::getId)
            .toList();

        // influencer 조회와 PlaceInfo 변환
        List<Video> videos = videoRepository.findByPlaceIdIn(placeIds);
        Map<Long, String> placeIdToInfluencerName = videos.stream()
            .collect(Collectors.toMap(
                video -> video.getPlace().getId(),
                video -> video.getInfluencer().getName()
            ));

        // PlaceInfo 생성
        List<PlaceInfo> placeInfos = placesPage.getContent().stream()
            .map(place -> {
                // map에서 조회되지 않은 placeId는 null로 처리
                String influencerName = placeIdToInfluencerName.getOrDefault(place.getId(), null);
                return PlaceInfo.of(place, influencerName);
            })
            .toList();

        // PlaceInfo 리스트를 Page로 변환하여 반환
        return new PageImpl<>(placeInfos, placesPage.getPageable(), placeInfos.size());
    }

    private Page<Place> getPlacesByDistance(
        PlacesCoordinateCommand placesCoordinateCommand,
        List<String> categoryFilters,
        List<String> influencerFilters
    ) {
        return placeRepository.getPlacesByDistanceAndFilters(
            placesCoordinateCommand.topLeftLongitude(),
            placesCoordinateCommand.topLeftLatitude(),
            placesCoordinateCommand.bottomRightLongitude(),
            placesCoordinateCommand.bottomRightLatitude(),
            placesCoordinateCommand.longitude(),
            placesCoordinateCommand.latitude(),
            categoryFilters,
            influencerFilters,
            placesCoordinateCommand.pageable()
        );
    }

    public PlaceDetailInfo getPlaceDetailInfo(Long placeId) {
        Place place = placeRepository.findById(placeId)
            .orElseThrow(() -> new IllegalArgumentException("PlaceService.getPlaceDetailInfo(): "
                + "Place Id가 존재하지 않습니다."));
        Video video = videoRepository.findByPlaceId(placeId)
            .orElseThrow(() -> new IllegalArgumentException("PlaceService.getPlaceDetailInfo(): "
                + "Place Id가 존재하지 않습니다."));

        return PlaceDetailInfo.from(place, video.getInfluencer(), video);
    }
}
