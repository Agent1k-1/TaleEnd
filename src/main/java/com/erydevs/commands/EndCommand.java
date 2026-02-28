package com.erydevs.commands;

import com.erydevs.TaleEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.utils.HexUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class EndCommand implements CommandExecutor {

    private final TaleEnd plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                if (!player.hasPermission("end.admin")) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getNoPermissionMessage()));
                    return true;
                }
                if (EndPortal.isOpen()) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getErrorEndStartMessage()));
                    return true;
                }
                EndPortal.setOpen(true);
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getEndAdminStartMessage()));
                for (String msg : plugin.getConfigs().getEndStartMessages()) {
                    broadcastToPlayers(HexUtils.colorize(msg));
                }
                playStartSound();
                break;
            case "stop":
                if (!player.hasPermission("end.admin")) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getNoPermissionMessage()));
                    return true;
                }
                if (!EndPortal.isOpen()) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getErrorEndStopMessage()));
                    return true;
                }
                EndPortal.setOpen(false);
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getEndAdminStopMessage()));
                for (String msg : plugin.getConfigs().getEndStopMessages()) {
                    broadcastToPlayers(HexUtils.colorize(msg));
                }
                break;
            case "open":
                if (!player.hasPermission("end.admin")) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getNoPermissionMessage()));
                    return true;
                }
                EndPortal.setOpen(true);
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getAdminOpenEndMessage()));
                broadcastToPlayers(HexUtils.colorize(plugin.getConfigs().getEndOpenMessage()));
                break;
            case "reload":
                if (!player.hasPermission("end.admin")) {
                    player.sendMessage(HexUtils.colorize(plugin.getConfigs().getNoPermissionMessage()));
                    return true;
                }
                plugin.getConfigs().reloadConfig();
                player.sendMessage(HexUtils.colorize(plugin.getConfigs().getConfigReloadMessage()));
                break;
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void sendHelp(Player player) {
        for (String msg : plugin.getConfigs().getEndHelpMessages()) {
            player.sendMessage(HexUtils.colorize(msg));
        }
    }

    private void broadcastToPlayers(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    private void playStartSound() {
        if (!plugin.getConfigs().isEndStartSoundEnabled()) {
            return;
        }
        try {
            String soundName = plugin.getConfigs().getEndStartSound();
            float pitch = plugin.getConfigs().getEndStartSoundPitch();
            float volume = plugin.getConfigs().getEndStartSoundVolume();
            Sound sound = Sound.valueOf(soundName);
            
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), sound, volume, pitch);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Неверный звук: " + plugin.getConfigs().getEndStartSound());
        }
    }
}
