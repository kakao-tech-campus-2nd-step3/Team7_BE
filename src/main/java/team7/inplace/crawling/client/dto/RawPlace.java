package team7.inplace.crawling.client.dto;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class RawPlace {
    public record Info(
            String placeName,
            String facility,
            String menuImgUrl,
            String category,
            String address,
            String x,
            String y,
            List<OffDay> offDays,
            List<OpenTime> openPeriods,
            List<Menu> menus
    ) {
        public static Info from(JsonNode locationNode, JsonNode placeNode) {
            var basicInfo = placeNode.get("basicInfo");

            String placeName =
                    basicInfo.has("placenamefull") ? basicInfo.get("placenamefull").asText() : "Unknown Place";
            String facility = basicInfo.has("facilityInfo")
                    ? basicInfo.get("facilityInfo").toString() : "N/A";

            String menuImgUrl = basicInfo.has("mainphotourl") ? basicInfo.get("mainphotourl").asText() : "";
            String category = basicInfo.has("category") && basicInfo.get("category").has("catename")
                    ? basicInfo.get("category").get("catename").asText() : "Unknown Category";
            String address =
                    basicInfo.has("address") && basicInfo.get("address").has("region") && basicInfo.get("address")
                            .get("region").has("newaddrfullname")
                            ? basicInfo.get("address").get("region").get("newaddrfullname").asText()
                            : "Unknown Address";
            String addressDetail =
                    basicInfo.has("address") && basicInfo.get("address").has("newaddr") && basicInfo.get("address")
                            .get("newaddr").has("newaddrfull")
                            ? basicInfo.get("address").get("newaddr").get("newaddrfull").asText() : "";

            String x = locationNode.has("documents") && locationNode.get("documents").get(0).has("x")
                    ? locationNode.get("documents").get(0).get("x").asText() : "0.0";
            String y = locationNode.has("documents") && locationNode.get("documents").get(0).has("y")
                    ? locationNode.get("documents").get(0).get("y").asText() : "0.0";

            var timeList = basicInfo.has("openHour") ? basicInfo.get("openHour") : null;
            var openPeriods = extractOpenPeriods(timeList.has("periodList") ? timeList.get("periodList") : null);
            var offDays = extractOffDays(timeList.has("offdayList") ? timeList.get("offdayList") : null);
            var menus = extractMenus(placeNode.has("menuInfo") ? placeNode.get("menuInfo") : null);

            return new Info(placeName, facility, menuImgUrl, category, address + " " + addressDetail, x, y, offDays,
                    openPeriods, menus);
        }

        private static List<RawPlace.OpenTime> extractOpenPeriods(JsonNode openTimeList) {
            if (openTimeList == null) {
                return new ArrayList<>();
            }
            List<RawPlace.OpenTime> openTimes = new ArrayList<>();
            for (JsonNode openTimeNode : openTimeList) {
                for (JsonNode timeNode : openTimeNode.get("timeList")) {
                    openTimes.add(OpenTime.from(timeNode));
                }
            }
            return openTimes;
        }

        private static List<RawPlace.OffDay> extractOffDays(JsonNode offdayList) {
            if (offdayList == null) {
                return new ArrayList<>(); // 빈 리스트를 반환하여 null 회피
            }
            List<RawPlace.OffDay> offDays = new ArrayList<>();
            for (JsonNode offDayNode : offdayList) {
                offDays.add(OffDay.from(offDayNode));
            }
            return offDays;
        }

        private static List<RawPlace.Menu> extractMenus(JsonNode menuList) {
            if (menuList == null) {
                return new ArrayList<>();
            }
            List<RawPlace.Menu> menus = new ArrayList<>();
            for (JsonNode menuNode : menuList.get("menuList")) {
                menus.add(Menu.from(menuNode));
            }
            return menus;
        }
    }

    public record OffDay(
            String holidayName,
            String weekAndDay,
            String temporaryHolidays
    ) {
        public static OffDay from(JsonNode offDayNode) {
            String holidayName = offDayNode != null && offDayNode.has("holidayName")
                    ? offDayNode.get("holidayName").asText() : "Unknown Holiday";
            String weekAndDay = offDayNode != null && offDayNode.has("weekAndDay")
                    ? offDayNode.get("weekAndDay").asText() : "Unknown Week And Day";
            String temporaryHolidays = offDayNode != null && offDayNode.has("temporaryHolidays")
                    ? offDayNode.get("temporaryHolidays").asText() : "No Temporary Holidays";
            return new OffDay(holidayName, weekAndDay, temporaryHolidays);
        }
    }

    public record OpenTime(
            String timeName,
            String timeSE,
            String dayOfWeek
    ) {
        public static OpenTime from(JsonNode openTimeNode) {
            String timeName = openTimeNode != null && openTimeNode.has("timeName")
                    ? openTimeNode.get("timeName").asText() : "Unknown Time Name";
            String timeSE = openTimeNode != null && openTimeNode.has("timeSE")
                    ? openTimeNode.get("timeSE").asText() : "Unknown Time Range";
            String dayOfWeek = openTimeNode != null && openTimeNode.has("dayOfWeek")
                    ? openTimeNode.get("dayOfWeek").asText() : "Unknown Day Of Week";
            return new OpenTime(timeName, timeSE, dayOfWeek);
        }
    }

    public record Menu(
            String menuName,
            String menuPrice,
            boolean recommend
    ) {
        public static Menu from(JsonNode menuNode) {
            String menuName = menuNode != null && menuNode.has("menu")
                    ? menuNode.get("menu").asText() : "Unknown Menu";
            String menuPrice = menuNode != null && menuNode.has("price")
                    ? menuNode.get("price").asText() : "0";
            boolean recommend = menuNode != null && menuNode.has("recommend")
                    && menuNode.get("recommend").asBoolean();
            return new Menu(menuName, menuPrice, recommend);
        }
    }
}