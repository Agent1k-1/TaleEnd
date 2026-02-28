package com.erydevs.listeners;

import com.erydevs.TaleEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.utils.HexUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

@AllArgsConstructor
public class EndListener implements Listener {

    private final TaleEnd plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEndPortalEnter(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        World targetWorld = event.getTo().getWorld();

        if (targetWorld == null) {
            return;
        }

        if (targetWorld.getEnvironment() == World.Environment.THE_END) {
            boolean hasPermission = player.hasPermission("end.portal");
            boolean isOpen = EndPortal.isOpen();
            
            if (!hasPermission && !isOpen) {
                event.setCancelled(true);
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getEndClosedMessage()));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
            boolean hasPermission = player.hasPermission("end.portal");
            boolean isOpen = EndPortal.isOpen();
            
            if (!hasPermission && !isOpen) {
                World spawn = Bukkit.getWorlds().get(0);
                player.teleport(spawn.getSpawnLocation());
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getEndClosedMessage()));
            }
        }
    }
}
