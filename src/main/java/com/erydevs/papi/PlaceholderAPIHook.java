package com.erydevs.papi;

import com.erydevs.TaleEnd;
import com.erydevs.format.TimeUntilEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.utils.HexUtils;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final TaleEnd plugin;

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
            if (EndPortal.isOpen()) {
                return HexUtils.colorize("&aАктивен");
            } else {
                return HexUtils.colorize("&cНеактивен");
            }
        }
        
        if (params.equals("open_time")) {
            return TimeUntilEnd.getTimeUntilOpen(plugin);
        }

        if (params.equals("stop_time")) {
            if (!EndPortal.isOpen()) {
                return HexUtils.colorize("&bЗахват &fещё закрыт");
            }
            return TimeUntilEnd.getTimeUntilStop(plugin);
        }
        
        return null;
    }
}
