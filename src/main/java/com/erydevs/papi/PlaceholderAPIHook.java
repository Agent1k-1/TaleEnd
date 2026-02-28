package com.erydevs.papi;

import com.erydevs.TaleEnd;
import com.erydevs.format.TimeUntilEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.utils.HexUtils;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final TaleEnd plugin;
    private static final ThreadLocal<String> mobName = ThreadLocal.withInitial(() -> "Unknown");

    @Override
    @NotNull
    public String getIdentifier() {
        return "end";
    }

    @Override
    @NotNull
    public String getAuthor() {
        return "Agent1k";
    }

    @Override
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equals("status")) {
            return EndPortal.isOpen() ? HexUtils.colorize("&aАктивен") : HexUtils.colorize("&cНеактивен");
        }
        
        if (params.equals("open_time")) {
            return format(TimeUntilEnd.getTimeUntilOpen(plugin), player);
        }

        if (params.equals("stop_time")) {
            if (!EndPortal.isOpen()) {
                return format(HexUtils.colorize("&bЗахват &fещё закрыт"), player);
            }
            return format(TimeUntilEnd.getTimeUntilStop(plugin), player);
        }

        if (params.equals("mobs")) {
            return mobName.get();
        }
        
        return null;
    }

    private String format(String text, Player player) {
        if (text == null) return null;
        return player != null ? PlaceholderAPI.setPlaceholders(player, text) : text;
    }

    public static void setMobName(String name) {
        mobName.set(name);
    }

    public static void clearMobName() {
        mobName.remove();
    }
}
