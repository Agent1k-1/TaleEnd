package com.erydevs.listeners;

import com.erydevs.TaleEnd;
import com.erydevs.action.Actions;
import com.erydevs.papi.PlaceholderAPIHook;
import lombok.AllArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.erydevs.utils.HexUtils;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private final TaleEnd plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getWorld().getEnvironment() != World.Environment.THE_END) {
            return;
        }

        if (!plugin.getConfigs().isKillerBarEnabled()) {
            return;
        }

        String mobType = getMobType(event.getEntity().getLastDamageCause());
        PlaceholderAPIHook.setMobName(mobType);

        for (String text : plugin.getConfigs().getKillerBarTexts()) {
            String formatted = PlaceholderAPI.setPlaceholders(player, HexUtils.colorize(text));
            
            for (String action : plugin.getConfigs().getKillerBarActions()) {
                Actions.executeToAll(action, formatted);
            }
        }

        PlaceholderAPIHook.clearMobName();
    }

    private String getMobType(org.bukkit.event.entity.EntityDamageEvent cause) {
        if (cause == null) {
            return "Unknown";
        }

        if (cause instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
            org.bukkit.entity.Entity damager = ((org.bukkit.event.entity.EntityDamageByEntityEvent) cause).getDamager();
            return damager.getType().name();
        }

        return cause.getCause().name();
    }
}
