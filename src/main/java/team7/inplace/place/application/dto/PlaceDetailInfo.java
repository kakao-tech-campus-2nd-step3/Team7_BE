package team7.inplace.place.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.OpenTime;
import team7.inplace.place.domain.Place;

public record PlaceDetailInfo(
    PlaceInfo placeInfo,
    String facilityInfo,
    MenuInfos menuInfos,
    OpenHour openHour,
    PlaceLikes placeLikes,
    String videoUrl
) {

    public static PlaceDetailInfo of(Place place, String influencerName, String videoUrl) {
        return new PlaceDetailInfo(
            PlaceInfo.of(place, influencerName),
            place.getFacility(),
            MenuInfos.of(place.getMenus()),
            OpenHour.of(place.getOpenPeriods(), place.getOffDays()),
            PlaceLikes.of(null), //추후 추가 예정
            videoUrl
        );
    }

    /*
        public record FacilityInfo(
            @JsonProperty("wifi")
            Boolean wifi,
            @JsonProperty("pet")
            Boolean pet,
            @JsonProperty("parking")
            Boolean parking,
            @JsonProperty("forDisabled")
            Boolean forDisabled,
            @JsonProperty("nursery")
            Boolean nursery,
            @JsonProperty("smokingRoom")
            Boolean smokingRoom
        ) {

            @JsonCreator
            public FacilityInfo(
                @JsonProperty("wifi") Boolean wifi,
                @JsonProperty("pet") Boolean pet,
                @JsonProperty("parking") Boolean parking,
                @JsonProperty("forDisabled") Boolean forDisabled,
                @JsonProperty("nursery") Boolean nursery,
                @JsonProperty("smokingRoom") Boolean smokingRoom
            ) {
                this.wifi = wifi;
                this.pet = pet;
                this.parking = parking;
                this.forDisabled = forDisabled;
                this.nursery = nursery;
                this.smokingRoom = smokingRoom;
            }

            private static final ObjectMapper objectMapper = new ObjectMapper();

            public static FacilityInfo of(Place place) {
                try {
                    // facility가 JSON 형식의 문자열로 저장되어 있을 경우 이를 역직렬화
                    return objectMapper.readValue(place.getFacility(), FacilityInfo.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to parse facility JSON", e);
                }
            }
        }
    */
    public record MenuInfos(
        List<MenuInfo> menuList,
        LocalDateTime timeExp
    ) {

        public static MenuInfos of(List<Menu> menus) {
            List<MenuInfo> menuList = menus.stream()
                .map(menu -> new MenuInfo(menu.getPrice().intValue(), menu.isRecommend(),
                    menu.getMenuName(),
                    menu.getMenuImgUrl()))
                .toList();

            return new MenuInfos(menuList, LocalDateTime.now());
        }

        public record MenuInfo(
            Integer price,
            Boolean recommend,
            String menuName,
            String menuImgUrl
        ) {

        }
    }

    public record OpenHour(
        List<Period> periodList,
        List<OffDay> offdayList
    ) {

        public static OpenHour of(List<OpenTime> openTimes,
            List<team7.inplace.place.domain.OffDay> closeTimes) {
            List<Period> periods = openTimes.stream()
                .map(time -> new Period(time.getTimeName(), time.getTimeSE(), time.getDayOfWeek()))
                .toList();

            List<OffDay> offDays = closeTimes.stream()
                .map(closeTime -> new OffDay(closeTime.getHolidayName(), closeTime.getWeekAndDay(),
                    closeTime.getTemporaryHolidays()))
                .toList();

            return new OpenHour(periods, offDays);
        }

        public record Period(
            String timeName,
            String timeSE,
            String dayOfWeek
        ) {

        }

        public record OffDay(
            String holidayName,
            String weekAndDay,
            String temporaryHolidays
        ) {

        }
    }

    public record PlaceLikes(
        Integer like,
        Integer dislike
    ) {

        public static PlaceLikes of(Boolean likes) {
            if (likes == null) {
                return new PlaceLikes(0, 0);
            }
            if (likes == true) {
                return new PlaceLikes(1, 0);
            }
            return new PlaceLikes(0, 1);
        }
    }
}
