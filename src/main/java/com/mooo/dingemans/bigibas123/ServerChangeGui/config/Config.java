package com.mooo.dingemans.bigibas123.ServerChangeGui.config;


import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Config {
    private static final JavaPlugin plugin = Reference.plugin;

    public static void save() {
        plugin.saveConfig();
    }

    private static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static String getItem(String server) {
        return getConfig().getString("SCG.items." + server + ".item");
    }

    public static short getDurability(String server) {
        return (short) getConfig().getInt("SCG.items." + server + ".durability");
    }

    public static void setItem(String server, String item) {
        getConfig().set("SCG.items." + server + ".item", item);
    }

    public static void setDurability(String server, short dur) {
        getConfig().set("SCG.items." + server + ".durability", dur);
    }

    public static void setLocation(String server, int x) {
        getConfig().set("SCG.items." + server + ".location", x);
    }

    public static int getLocation(String server) {
        return getConfig().getInt("SCG.items." + server + ".location");
    }

    public static void setLore(String server, List<String> lore) {
        getConfig().set("SCG.items." + server + ".lore", lore);
    }

    public static List<String> getLore(String server) {
        return getConfig().getStringList("SCG.items." + server + ".lore");
    }

    public static void setName(String server, String customName) {
        getConfig().set("SCG.items." + server + ".customName", customName);
    }

    public static String getName(String server) {
        return getConfig().getString("SCG.items." + server + ".customName");
    }

    public static String getMenuName() {
        return getConfig().getString("SCG.general.menuName");
    }

    public static void setMenuName(String name) {
        getConfig().set("SCG.general.menuName", name);
    }

    public static long getCallDelay() {
        Long l = getConfig().getLong("SCG.general.messageCallDelayInTicks");
        if (l == 0) {
            l = 25L;
            getConfig().set("SCG.general.messageCallDelayInTicks", l);
        }
        return l;
    }
}
