package com.erydevs.sounds;

import com.erydevs.TaleEnd;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class Sounds {

    private final TaleEnd plugin;

    public void playStartSound() {
        if (!plugin.getConfigs().isEndStartSoundEnabled()) {
            return;
        }
        
        playSoundToAll(
            plugin.getConfigs().getEndStartSound(),
            plugin.getConfigs().getEndStartSoundPitch(),
            plugin.getConfigs().getEndStartSoundVolume()
        );
    }

    public void playSoundToAll(String soundName, float pitch, float volume) {
        try {
            org.bukkit.Sound sound = org.bukkit.Sound.valueOf(soundName);
            
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), sound, volume, pitch);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Неверный звук: " + soundName);
        }
    }

    public void playSoundToPlayer(Player player, String soundName, float pitch, float volume) {
        try {
            org.bukkit.Sound sound = org.bukkit.Sound.valueOf(soundName);
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (Exception e) {
            plugin.getLogger().warning("Неверный звук: " + soundName);
        }
    }
}
