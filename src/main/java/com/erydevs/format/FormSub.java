package com.erydevs.format;

import com.erydevs.TaleEnd;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.DayOfWeek;

@Getter
public class FormSub {

    public static String getDayOfWeek(TaleEnd plugin) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        LocalDateTime now = LocalDateTime.now(zoneId);
        DayOfWeek day = now.getDayOfWeek();
        return getDayName(day);
    }

    public static String getDayName(DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return "Понедельник";
            case TUESDAY:
                return "Вторник";
            case WEDNESDAY:
                return "Среда";
            case THURSDAY:
                return "Четверг";
            case FRIDAY:
                return "Пятница";
            case SATURDAY:
                return "Суббота";
            case SUNDAY:
                return "Воскресенье";
            default:
                return "Неизвестно";
        }
    }

    public static boolean isSaturday(TaleEnd plugin) {
        ZoneId zoneId = ZoneId.of(plugin.getConfigs().getTimeZone());
        LocalDateTime now = LocalDateTime.now(zoneId);
        return now.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}
