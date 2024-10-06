package team7.inplace.place.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.Place;
import team7.inplace.place.domain.PlaceCloseTime;
import team7.inplace.place.domain.PlaceOpenTime;

public record PlaceDetailInfo(
    PlaceInfo placeInfo,
    FacilityInfo facilityInfo,
    MenuInfos menuInfos,
    OpenHour openHour,
    PlaceLikes placeLikes,
    String videoUrl
) {

    public static PlaceDetailInfo of(Place place, String influencerName, String videoUrl) {
        return new PlaceDetailInfo(
            PlaceInfo.of(place, influencerName),
            FacilityInfo.of(place),
            MenuInfos.of(place.getMenuList()),
            OpenHour.of(place.getTimeList(), place.getOffdayList()),
            PlaceLikes.of(null), //추후 추가 예정
            videoUrl
        );
    }

    public record FacilityInfo(
        Boolean wifi,
        Boolean pet,
        Boolean parking,
        Boolean forDisabled,
        Boolean nursery,
        Boolean smokingRoom
    ) {

        public static FacilityInfo of(Place place) {
            return new FacilityInfo(
                place.isWifi(),
                place.isPet(),
                place.isParking(),
                place.isFordisabled(),
                place.isNursery(),
                place.isSmokingroom()
            );
        }
    }

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

        public static OpenHour of(List<PlaceOpenTime> openTimes, List<PlaceCloseTime> closeTimes) {
            List<Period> periods = openTimes.stream()
                .map(time -> new Period(time.getTimeName(), time.getTimeSe(), time.getDayOfWeek()))
                .toList();

            List<OffDay> offDays = closeTimes.stream()
                .map(closeTime -> new OffDay(closeTime.getHolidayName(), closeTime.getWeekAndDay(),
                    closeTime.isTemporaryHolidays()))
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
            Boolean temporaryHolidays
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
