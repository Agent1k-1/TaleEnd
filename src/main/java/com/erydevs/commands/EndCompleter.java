package com.erydevs.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EndCompleter implements TabCompleter {

    private static final List<String> COMMANDS = new ArrayList<>();

    static {
        COMMANDS.add("start");
        COMMANDS.add("stop");
        COMMANDS.add("open");
        COMMANDS.add("reload");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> completions = new ArrayList<>();

            for (String cmd : COMMANDS) {
                if (cmd.startsWith(input)) {
                    completions.add(cmd);
                }
            }

            return completions;
        }

        return Collections.emptyList();
    }
}
