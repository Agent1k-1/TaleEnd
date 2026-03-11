package com.erydevs;

import com.erydevs.commands.EndCommand;
import com.erydevs.commands.EndCompleter;
import com.erydevs.config.Configs;
import com.erydevs.listeners.EndListener;
import com.erydevs.listeners.PlayerDeathListener;
import com.erydevs.papi.PlaceholderAPIHook;
import com.erydevs.scheduler.EndScheduler;
import com.erydevs.utils.HexUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TaleEnd extends JavaPlugin {

    private static TaleEnd instance;
    private Configs configs;
    private EndScheduler scheduler;

    @Override
    public void onEnable() {
        instance = this;
        
        configs = new Configs(this);
        configs.loadConfigs();

        registerCommands();
        registerListeners();
        
        scheduler = new EndScheduler(this);
        scheduler.startScheduler();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(this).register();
        }

        Bukkit.getConsoleSender().sendMessage("TaleEnd");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Плагин: " + ChatColor.GREEN + "включен");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Версия плагина: " + ChatColor.YELLOW + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Ядро: " + ChatColor.YELLOW + Bukkit.getVersion());

    }

    private void registerCommands() {
        getCommand("end").setExecutor(new EndCommand(this));
        getCommand("end").setTabCompleter(new EndCompleter());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new EndListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("TaleEnd");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Плагин: " + ChatColor.RED + "выключен");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Версия плагина: " + ChatColor.YELLOW + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "Ядро: " + ChatColor.YELLOW + Bukkit.getVersion());
    }
    public static TaleEnd getInstance() {
        return instance;
    }
}
