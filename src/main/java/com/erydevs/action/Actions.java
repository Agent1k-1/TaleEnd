package com.erydevs.action;

import com.erydevs.utils.ActionBar;
import org.bukkit.entity.Player;

public class Actions {

    public static void execute(Player player, String action, String message) {
        if (action.equalsIgnoreCase("[actionbar]")) {
            ActionBar.sendActionBar(player, message);
        }
    }

    public static void executeToAll(String action, String message) {
        if (action.equalsIgnoreCase("[actionbar]")) {
            ActionBar.sendActionBarToAll(message);
        }
    }
}
