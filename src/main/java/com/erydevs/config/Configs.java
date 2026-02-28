package com.erydevs.config;

import com.erydevs.TaleEnd;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@Getter
public class Configs {

    private final TaleEnd plugin;
    private FileConfiguration config;

    public Configs(TaleEnd plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public List<String> getEndStartMessages() {
        return config.getStringList("message.end-start");
    }

    public List<String> getEndStopMessages() {
        return config.getStringList("message.end-stop");
    }

    public String getEndClosedMessage() {
        return config.getString("message.end-closed");
    }

    public String getEndAdminStartMessage() {
        return config.getString("message.end-admin-start");
    }

    public String getEndAdminStopMessage() {
        return config.getString("message.end-admin-stop");
    }

    public String getAdminOpenEndMessage() {
        return config.getString("message.admin-open-end");
    }

    public String getEndOpenMessage() {
        return config.getString("message.end-open");
    }

    public String getNoPermissionMessage() {
        return config.getString("message.no-permission");
    }

    public String getErrorEndStartMessage() {
        return config.getString("message.error-end-start");
    }

    public String getErrorEndStopMessage() {
        return config.getString("message.error-end-stop");
    }

    public String getConfigReloadMessage() {
        return config.getString("message.config-reload");
    }

    public List<String> getEndHelpMessages() {
        return config.getStringList("message.end-help");
    }

    public List<String> getEndStartingTimes() {
        return config.getStringList("configuration-settings.end-starting");
    }

    public List<String> getEndStoppingTimes() {
        return config.getStringList("configuration-settings.end-stopping");
    }

    public String getEndStartSound() {
        return config.getString("sounds.END-START");
    }

    public boolean isEndStartSoundEnabled() {
        return config.getBoolean("sounds.sound", true);
    }

    public float getEndStartSoundPitch() {
        return (float) config.getDouble("sounds.pitch");
    }

    public float getEndStartSoundVolume() {
        return (float) config.getDouble("sounds.volume");
    }

    public boolean isKillerBarEnabled() {
        return config.getBoolean("killer-bar.killer", true);
    }

    public List<String> getKillerBarTexts() {
        return config.getStringList("killer-bar.text");
    }

    public List<String> getKillerBarActions() {
        return config.getStringList("killer-bar.action");
    }
}
