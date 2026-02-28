package com.erydevs.format;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.DayOfWeek;

@Getter
public class FormSub {

    private static final ZoneId MOSCOW_ZONE = ZoneId.of("Europe/Moscow");

    public static String getDayOfWeek() {
        LocalDateTime now = LocalDateTime.now(MOSCOW_ZONE);
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

    public static boolean isSaturday() {
        LocalDateTime now = LocalDateTime.now(MOSCOW_ZONE);
        return now.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}
