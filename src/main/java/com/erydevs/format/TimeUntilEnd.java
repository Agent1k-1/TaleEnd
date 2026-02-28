package com.erydevs.format;

import com.erydevs.TaleEnd;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TimeUntilEnd {

    private static final ZoneId MOSCOW_ZONE = ZoneId.of("Europe/Moscow");

    public static String getTimeUntilOpen(TaleEnd plugin) {
        List<String> timeFormats = plugin.getConfigs().getEndStartingTimes();
        
        if (timeFormats.isEmpty()) {
            return "Не установлено";
        }

        LocalDateTime now = LocalDateTime.now(MOSCOW_ZONE);
        LocalDateTime nextOpen = findNextOpenTime(now, timeFormats);

        if (nextOpen == null) {
            return "Не установлено";
        }

        return formatTimeDifference(now, nextOpen);
    }

    public static String getTimeUntilStop(TaleEnd plugin) {
        List<String> timeFormats = plugin.getConfigs().getEndStoppingTimes();
        
        if (timeFormats.isEmpty()) {
            return "Не установлено";
        }

        LocalDateTime now = LocalDateTime.now(MOSCOW_ZONE);
        LocalDateTime nextStop = findNextStopTime(now, timeFormats);

        if (nextStop == null) {
            return "Не установлено";
        }

        return formatTimeDifference(now, nextStop);
    }

    private static LocalDateTime findNextOpenTime(LocalDateTime now, List<String> timeFormats) {
        LocalDateTime nextOpen = null;

        for (String format : timeFormats) {
            String[] parts = format.split(":");
            if (parts.length == 3) {
                String day = parts[0].trim();
                int hour = Integer.parseInt(parts[1].trim());
                int minute = Integer.parseInt(parts[2].trim());

                DayOfWeek targetDay = getDayOfWeek(day);
                if (targetDay == null) continue;

                LocalDateTime candidateTime = findNextDayWithTime(now, targetDay, hour, minute);
                
                if (nextOpen == null || candidateTime.isBefore(nextOpen)) {
                    nextOpen = candidateTime;
                }
            }
        }

        return nextOpen;
    }

    private static LocalDateTime findNextStopTime(LocalDateTime now, List<String> timeFormats) {
        LocalDateTime nextStop = null;

        for (String format : timeFormats) {
            String[] parts = format.split(":");
            if (parts.length == 3) {
                String day = parts[0].trim();
                int hour = Integer.parseInt(parts[1].trim());
                int minute = Integer.parseInt(parts[2].trim());

                DayOfWeek targetDay = getDayOfWeek(day);
                if (targetDay == null) continue;

                LocalDateTime candidateTime = findNextDayWithTime(now, targetDay, hour, minute);
                
                if (nextStop == null || candidateTime.isBefore(nextStop)) {
                    nextStop = candidateTime;
                }
            }
        }

        return nextStop;
    }

    private static LocalDateTime findNextDayWithTime(LocalDateTime now, DayOfWeek targetDay, int hour, int minute) {
        LocalDateTime candidate = now.withHour(hour).withMinute(minute).withSecond(0);

        if (now.getDayOfWeek() == targetDay && now.isBefore(candidate)) {
            return candidate;
        }

        int daysUntilTarget = ((targetDay.getValue() - now.getDayOfWeek().getValue() + 7) % 7);
        if (daysUntilTarget == 0 && now.isAfter(candidate)) {
            daysUntilTarget = 7;
        }

        return candidate.plusDays(daysUntilTarget);
    }

    private static String formatTimeDifference(LocalDateTime from, LocalDateTime to) {
        long days = ChronoUnit.DAYS.between(from, to);
        long hours = ChronoUnit.HOURS.between(from.plusDays(days), to);
        long minutes = ChronoUnit.MINUTES.between(from.plusDays(days).plusHours(hours), to);
        long seconds = ChronoUnit.SECONDS.between(from.plusDays(days).plusHours(hours).plusMinutes(minutes), to);

        StringBuilder result = new StringBuilder();

        if (days > 0) {
            result.append(days).append("д ");
        }
        if (hours > 0) {
            result.append(hours).append("ч ");
        }
        if (minutes > 0) {
            result.append(minutes).append("м ");
        }
        if (seconds > 0 || result.length() == 0) {
            result.append(seconds).append("с");
        }

        return result.toString().trim();
    }

    private static DayOfWeek getDayOfWeek(String day) {
        switch (day) {
            case "Понедельник":
                return DayOfWeek.MONDAY;
            case "Вторник":
                return DayOfWeek.TUESDAY;
            case "Среда":
                return DayOfWeek.WEDNESDAY;
            case "Четверг":
                return DayOfWeek.THURSDAY;
            case "Пятница":
                return DayOfWeek.FRIDAY;
            case "Суббота":
                return DayOfWeek.SATURDAY;
            case "Воскресенье":
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }
}
