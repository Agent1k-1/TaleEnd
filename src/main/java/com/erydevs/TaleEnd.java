package com.erydevs;

import com.erydevs.commands.EndCommand;
import com.erydevs.commands.EndCompleter;
import com.erydevs.config.Configs;
import com.erydevs.listeners.EndListener;
import com.erydevs.papi.PlaceholderAPIHook;
import com.erydevs.scheduler.EndScheduler;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
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

        PluginCommand endCommand = Bukkit.getPluginCommand("end");
        if (endCommand != null) {
            endCommand.setExecutor(new EndCommand(this));
            endCommand.setTabCompleter(new EndCompleter());
        }

        Bukkit.getPluginManager().registerEvents(new EndListener(this), this);

        scheduler = new EndScheduler(this);
        scheduler.startScheduler();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook(this).register();
        }

        getLogger().info("TaleEnd плагин успешно загружен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TaleEnd плагин выключен!");
    }

    public static TaleEnd getInstance() {
        return instance;
    }
}
