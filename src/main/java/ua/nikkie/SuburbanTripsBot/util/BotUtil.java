package ua.nikkie.SuburbanTripsBot.util;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ua.nikkie.SuburbanTripsBot.entities.DriverTrip;
import ua.nikkie.SuburbanTripsBot.navigation.keyboard_menu.ReplyKeyboardStringBuilder;

public class BotUtil {
    public static final String RESOURCES_PATH = "src/main/resources/";

    public static class DateTime {

        private static final List<String> TIME_RANGES = asList(
            "00:00 - 04:00", "04:00 - 08:00", "08:00 - 12:00",
            "12:00 - 16:00", "16:00 - 20:00", "20:00 - 00:00");
        private static final List<String> HOURS_RANGES = asList(
            "00,01,02,03", "04,05,06,07", "08,09,10,11",
            "12,13,14,15", "16,17,18,19", "20,21,22,23");
        private static final List<String> MINUTES = asList("00", "10", "20", "30", "40", "50");
        private static final String HOUR_MINUTE_FORMAT = "%s:%s";
        private static final String DAY_MONTH_FORMAT = "%s.%s";
        private static final Map<DayOfWeek, String> DAY_OF_WEEK_MAP = new HashMap<>();

        static {
            DAY_OF_WEEK_MAP.put(DayOfWeek.MONDAY, "Понеділок");
            DAY_OF_WEEK_MAP.put(DayOfWeek.TUESDAY, "Вівторок");
            DAY_OF_WEEK_MAP.put(DayOfWeek.WEDNESDAY, "Середа");
            DAY_OF_WEEK_MAP.put(DayOfWeek.THURSDAY, "Четвер");
            DAY_OF_WEEK_MAP.put(DayOfWeek.FRIDAY, "П'ятниця");
            DAY_OF_WEEK_MAP.put(DayOfWeek.SATURDAY, "Субота");
            DAY_OF_WEEK_MAP.put(DayOfWeek.SUNDAY, "Неділя");
        }

        public static ReplyKeyboard getDateChoosingKeyboard() {
            LocalDateTime dateTime = LocalDateTime.now();

            if (dateTime.getHour() == 23 && dateTime.getMinute() >= 45) {
                return getDayIsOverDateChoosingKeyboard(dateTime);
            }
            return getRegularDateChoosingKeyboard(dateTime);
        }

        public static ReplyKeyboard getTimeRangeChoosingKeyboard(DriverTrip driverTrip) {
            if (driverTrip.getDate().getDayOfMonth()
                == LocalDate.now().getDayOfMonth()) {
                return getCurrentDayTimeRangeChoosingKeyboard();
            }
            return getAllTimeRangeChoosingKeyboard();
        }

        public static ReplyKeyboard getTimeChoosingKeyboard(DriverTrip driverTrip) {
            int now = LocalTime.now().getHour();
            String[] timeRange = driverTrip.getTimeRange().split(" - ");
            int timeSince = Integer.parseInt(timeRange[0].split(":")[0]);
            int timeTill = Integer.parseInt(timeRange[1].split(":")[0]);
            timeTill = timeTill == 0 ? 24 : timeTill;

            if (timeSince <= now && timeTill > now) {
                return getCurrentRangeTimeChoosingKeyboard();
            }
            return getRegularTimeChoosingKeyboard(driverTrip);
        }

        public static String getUkrainianDayOfWeek(DriverTrip driverTrip) {
            return DAY_OF_WEEK_MAP.get(driverTrip.getDate().getDayOfWeek());
        }


        //region Private methods

        private static ReplyKeyboard getCurrentRangeTimeChoosingKeyboard() {
            int nowHour = LocalTime.now().getHour();
            String hoursRange = HOURS_RANGES.get(nowHour / 4);
            List<String> actualHoursWithoutCurrent = Arrays.stream(hoursRange.split(","))
                .filter(hour -> Integer.parseInt(hour) > nowHour)
                .collect(Collectors.toList());

            int nowMinute = LocalTime.now().getMinute();
            List<String> actualMinutes = MINUTES.stream()
                .filter(minute -> Integer.parseInt(minute) > nowMinute + 5)
                .collect(Collectors.toList());

            List<String> actualTimeList = new ArrayList<>();
            actualMinutes.forEach(m -> actualTimeList.add(format(HOUR_MINUTE_FORMAT, nowHour, m)));

            if (!actualHoursWithoutCurrent.isEmpty()) {
                actualHoursWithoutCurrent.forEach(
                    h -> MINUTES.forEach(m -> actualTimeList.add(format(HOUR_MINUTE_FORMAT, h, m))));
            }

            return buildTimeKeyboardWithList(actualTimeList);
        }

        private static ReplyKeyboard getRegularTimeChoosingKeyboard(DriverTrip driverTrip) {
            String hoursRange = HOURS_RANGES.get(TIME_RANGES.indexOf(driverTrip.getTimeRange()));
            List<String> hoursList = Arrays.stream(hoursRange.split(","))
                .collect(Collectors.toList());

            List<String> actualTimeList = new ArrayList<>();
            hoursList.forEach(h -> MINUTES.forEach(m -> actualTimeList.add(format(HOUR_MINUTE_FORMAT, h, m))));

            return buildTimeKeyboardWithList(actualTimeList);
        }

        private static ReplyKeyboard buildTimeKeyboardWithList(List<String> actualTimeList) {
            ReplyKeyboardStringBuilder keyboardBuilder = ReplyKeyboardStringBuilder.builder();
            int actualTimeListCounter = 0;
            if (actualTimeList.size() >= 3) {
                for (int i = 0; i < actualTimeList.size() / 3; i++) {
                    keyboardBuilder.addRow(
                        actualTimeList.get(actualTimeListCounter++),
                        actualTimeList.get(actualTimeListCounter++),
                        actualTimeList.get(actualTimeListCounter++)
                    );
                }
            }

            if (actualTimeList.size() % 3 == 1) {
                keyboardBuilder.addRow(actualTimeList.get(actualTimeListCounter));
            } else if (actualTimeList.size() % 3 == 2) {
                keyboardBuilder.addRow(
                    actualTimeList.get(actualTimeListCounter++),
                    actualTimeList.get(actualTimeListCounter)
                );
            }
            return keyboardBuilder.build();
        }

        private static ReplyKeyboard getDayIsOverDateChoosingKeyboard(LocalDateTime dateTime) {
            return ReplyKeyboardStringBuilder.builder()
                .addRow(
                    format(DAY_MONTH_FORMAT, dateTime.plusDays(1).getDayOfMonth(), dateTime.plusDays(1).getMonthValue()),
                    format(DAY_MONTH_FORMAT, dateTime.plusDays(2).getDayOfMonth(), dateTime.plusDays(2).getMonthValue()))
                .build();
        }

        private static ReplyKeyboard getRegularDateChoosingKeyboard(LocalDateTime dateTime) {
            return ReplyKeyboardStringBuilder.builder()
                .addRow(
                    format(DAY_MONTH_FORMAT, dateTime.getDayOfMonth(), dateTime.getMonthValue()),
                    format(DAY_MONTH_FORMAT, dateTime.plusDays(1).getDayOfMonth(), dateTime.plusDays(1).getMonthValue()),
                    format(DAY_MONTH_FORMAT, dateTime.plusDays(2).getDayOfMonth(), dateTime.plusDays(2).getMonthValue()))
                .build();
        }

        private static ReplyKeyboard getCurrentDayTimeRangeChoosingKeyboard() {
            ReplyKeyboardStringBuilder keyboardBuilder = ReplyKeyboardStringBuilder.builder();
            int rangesToSkip = LocalDateTime.now().getHour() / 4;
            List<String> actualRanges = TIME_RANGES.subList(rangesToSkip, TIME_RANGES.size());

            if (actualRanges.size() == TIME_RANGES.size()) {
                return getAllTimeRangeChoosingKeyboard();
            }

            int actualRangeCounter = 0;
            for (int i = 0; i < actualRanges.size() / 2; i++) {
                keyboardBuilder.addRow(actualRanges.get(actualRangeCounter++), actualRanges.get(actualRangeCounter++));
            }
            if (actualRanges.size() % 2 != 0) {
                keyboardBuilder.addRow(actualRanges.get(actualRangeCounter));
            }
            return keyboardBuilder.build();
        }

        private static ReplyKeyboard getAllTimeRangeChoosingKeyboard() {
            return ReplyKeyboardStringBuilder.builder()
                .addRow(TIME_RANGES.get(0), TIME_RANGES.get(1))
                .addRow(TIME_RANGES.get(2), TIME_RANGES.get(3))
                .addRow(TIME_RANGES.get(4), TIME_RANGES.get(5))
                .build();
        }
        //endregion
    }
}
