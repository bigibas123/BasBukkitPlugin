package com.github.bigibas123.ServerChangeGui.util;

import org.bukkit.permissions.Permissible;

public class PermissionUtils {
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
