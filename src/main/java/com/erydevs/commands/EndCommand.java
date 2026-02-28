package com.erydevs.commands;

import com.erydevs.TaleEnd;
import com.erydevs.portal.EndPortal;
import com.erydevs.sounds.Sounds;
import com.erydevs.utils.HexUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndCommand implements CommandExecutor {

    private final TaleEnd plugin;
    private final Sounds soundManager;

    public EndCommand(TaleEnd plugin) {
        this.plugin = plugin;
        this.soundManager = new Sounds(plugin);
    }

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
                soundManager.playStartSound();
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

}
