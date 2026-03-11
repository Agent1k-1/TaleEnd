package com.erydevs.format;

import com.erydevs.TaleEnd;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class Time {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String getCurrentTime(TaleEnd plugin) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        LocalDateTime now = LocalDateTime.now(zoneId);
        return TIME_FORMATTER.format(now);
    }

    public static String formatTime(TaleEnd plugin, LocalDateTime dateTime) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        return TIME_FORMATTER.format(dateTime.atZone(zoneId));
    }

    public static int getCurrentHour(TaleEnd plugin) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        return LocalDateTime.now(zoneId).getHour();
    }

    public static int getCurrentMinute(TaleEnd plugin) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        return LocalDateTime.now(zoneId).getMinute();
    }
}
