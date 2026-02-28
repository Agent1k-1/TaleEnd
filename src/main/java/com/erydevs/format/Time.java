package com.erydevs.format;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class Time {

    private static final ZoneId MOSCOW_ZONE = ZoneId.of("Europe/Moscow");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm").withZone(MOSCOW_ZONE);

    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now(MOSCOW_ZONE);
        return TIME_FORMATTER.format(now);
    }

    public static String formatTime(LocalDateTime dateTime) {
        return TIME_FORMATTER.format(dateTime.atZone(MOSCOW_ZONE));
    }

    public static int getCurrentHour() {
        return LocalDateTime.now(MOSCOW_ZONE).getHour();
    }

    public static int getCurrentMinute() {
        return LocalDateTime.now(MOSCOW_ZONE).getMinute();
    }
}
