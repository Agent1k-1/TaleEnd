package com.erydevs.scheduler;

import com.erydevs.TaleEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.sounds.Sounds;
import com.erydevs.utils.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class EndScheduler {

    private final TaleEnd plugin;
    private final Sounds soundManager;
    private boolean eventTriggered = false;

    public EndScheduler(TaleEnd plugin) {
        this.plugin = plugin;
        this.soundManager = new Sounds(plugin);
    }

    public void startScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
            String currentTime = String.format("%02d:%02d", now.getHour(), now.getMinute());
            
            List<String> startingTimes = plugin.getConfigs().getEndStartingTimes();
            List<String> stoppingTimes = plugin.getConfigs().getEndStoppingTimes();
            
            checkStartingTime(now, currentTime, startingTimes);
            checkStoppingTime(now, currentTime, stoppingTimes);
        }, 0L, 20L);
    }

    private void checkStartingTime(LocalDateTime now, String currentTime, List<String> timeFormats) {
        for (String format : timeFormats) {
            String[] parts = format.split(":");
            if (parts.length == 3) {
                String day = parts[0].trim();
                String hour = parts[1].trim();
                String minute = parts[2].trim();
                String formatTime = String.format("%s:%s", hour, minute);
                
                if (getDayOfWeek(now.getDayOfWeek()).equals(day) && currentTime.equals(formatTime)) {
                    if (!eventTriggered) {
                        startEndEvent();
                        eventTriggered = true;
                    }
                } else {
                    eventTriggered = false;
                }
            }
        }
    }

    private void checkStoppingTime(LocalDateTime now, String currentTime, List<String> timeFormats) {
        for (String format : timeFormats) {
            String[] parts = format.split(":");
            if (parts.length == 3) {
                String day = parts[0].trim();
                String hour = parts[1].trim();
                String minute = parts[2].trim();
                String formatTime = String.format("%s:%s", hour, minute);
                
                if (getDayOfWeek(now.getDayOfWeek()).equals(day) && currentTime.equals(formatTime)) {
                    if (EndPortal.isOpen()) {
                        stopEndEvent();
                    }
                }
            }
        }
    }

    private void startEndEvent() {
        EndPortal.setOpen(true);
        for (String msg : plugin.getConfigs().getEndStartMessages()) {
            broadcastToPlayers(HexUtils.colorize(msg));
        }
        soundManager.playStartSound();
    }

    private void stopEndEvent() {
        EndPortal.setOpen(false);
        for (String msg : plugin.getConfigs().getEndStopMessages()) {
            broadcastToPlayers(HexUtils.colorize(msg));
        }
    }

    private void broadcastToPlayers(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    private String getDayOfWeek(DayOfWeek day) {
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
}
