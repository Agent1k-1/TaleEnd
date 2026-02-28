package com.erydevs.utils;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([0-9a-fA-F]{6})");

    public static String colorize(String input) {
        if (input == null) {
            return "";
        }
        String output = ChatColor.translateAlternateColorCodes('&', input);
        Matcher matcher = HEX_PATTERN.matcher(output);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String replacement = ChatColor.of("#" + hexCode).toString();
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}

