package me.dw1e.ff.misc.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public final class StringUtil {

    public static List<String> filterStartingWith(List<String> options, String input) {
        return options.stream().filter(option -> option.toLowerCase()
                .startsWith(input.toLowerCase())).collect(Collectors.toList());
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
