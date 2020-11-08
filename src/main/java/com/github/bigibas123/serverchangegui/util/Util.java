package com.github.bigibas123.serverchangegui.util;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permissible;

public class Util {
    public static String replaceColorCodes(String string) {
        return ChatColor.RESET.toString() +
                string.replaceAll("(?<!\\\\)&", String.valueOf(ChatColor.COLOR_CHAR))
                        .replaceAll("\\\\&", "&");
    }

    public static boolean hasPermission(Permissible player, String permission) {
        if (player.hasPermission(permission)) return true;
        StringBuilder permPart = new StringBuilder();
        for (String piece : permission.split("\\.")) {
            permPart.append(piece).append(".");
            if (player.hasPermission(permPart.toString() + "*")) return true;
        }
        return false;
    }
}
