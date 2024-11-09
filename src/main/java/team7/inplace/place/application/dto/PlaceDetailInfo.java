package team7.inplace.place.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public static PlaceDetailInfo from(Place place, Influencer influencer, Video video,
        boolean isLiked) {
        String influencerName = (influencer != null) ? influencer.getName() : "";
        String videoUrl = (video != null) ? video.getVideoUrl() : "";

        return new PlaceDetailInfo(
            PlaceInfo.of(place, influencerName, isLiked),
            facilityTree(place.getFacility()),
            MenuInfos.of(
                place.getMenuboardphotourlList(),
                place.getMenus(),
                place.getMenuUpdatedAt()),
            OpenHour.of(place.getOpenPeriods(), place.getOffDays()),
            PlaceLikes.of(isLiked),
            videoUrl
        );
    }

    private static JsonNode facilityTree(String facility) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (facility == null || facility.isBlank()) {
            ObjectNode noDataNode = JsonNodeFactory.instance.objectNode();
            noDataNode.put("message", "NO DATA");
            return noDataNode;
        }
        try {
            return objectMapper.readTree(facility);
        } catch (Exception e) {
            ObjectNode noDataNode = JsonNodeFactory.instance.objectNode();
            noDataNode.put("message", "NO DATA");
            return noDataNode;
        }
    }

    public record MenuInfos(
        List<String> menuImgUrls,
        List<MenuInfo> menuList,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timeExp
    ) {

        public static MenuInfos of(
            List<String> menuImgUrls,
            List<Menu> menus,
            LocalDateTime menuUpdatedAt) {

            List<MenuInfo> menuList = menus.stream()
                .map(menu -> new MenuInfo(menu.getPrice(), menu.isRecommend(),
                    menu.getMenuName(), menu.getMenuImgUrl(), menu.getDescription()))
                .toList();

            return new MenuInfos(menuImgUrls, menuList, menuUpdatedAt);
        }

        public record MenuInfo(
            String price,
            Boolean recommend,
            String menuName,
            String menuImgUrl,
            String description
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
            if (likes) {
                return new PlaceLikes(1, 0);
            }
            return new PlaceLikes(0, 1);
        }
    }
}
