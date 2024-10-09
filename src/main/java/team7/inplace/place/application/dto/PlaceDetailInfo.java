package team7.inplace.place.application.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import team7.inplace.influencer.domain.Influencer;
import team7.inplace.place.domain.Menu;
import team7.inplace.place.domain.OpenTime;
import team7.inplace.place.domain.Place;
import team7.inplace.video.domain.Video;

public record PlaceDetailInfo(
        PlaceInfo placeInfo,
        JsonNode facilityInfo,
        MenuInfos menuInfos,
        OpenHour openHour,
        PlaceLikes placeLikes,
        String videoUrl
) {

    public static PlaceDetailInfo from(Place place, Influencer influencer, Video video) {
        return new PlaceDetailInfo(
                PlaceInfo.of(place, influencer.getName()),
                facilityTree(place.getFacility()),
                MenuInfos.of(place.getMenus()),
                OpenHour.of(place.getOpenPeriods(), place.getOffDays()),
                PlaceLikes.of(null), //추후 추가 예정
                video.getVideoUrl()
        );
    }

    private static JsonNode facilityTree(String facility) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(facility);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse facility JSON", e);
        }
    }


    public record MenuInfos(
            List<String> menuImgUrls,
            List<MenuInfo> menuList,
            LocalDateTime timeExp
    ) {

        public static MenuInfos of(List<Menu> menus) {
            List<String> menuImgUrls = menus.stream()
                    .map(Menu::getMenuImgUrl) // 메뉴 이미지 URL을 추출
                    .toList();

            List<MenuInfo> menuList = menus.stream()
                    .map(menu -> new MenuInfo(menu.getPrice().intValue(), menu.isRecommend(),
                            menu.getMenuName()))
                    .toList();

            return new MenuInfos(menuImgUrls, menuList, LocalDateTime.now());
        }

        public record MenuInfo(
                Integer price,
                Boolean recommend,
                String menuName
        ) {

        }
    }

    public record OpenHour(
            List<Period> periodList,
            List<OffDay> offdayList
    ) {

        public static OpenHour of(List<OpenTime> openTimes,
                                  List<team7.inplace.place.domain.OffDay> closeTimes
        ) {
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
