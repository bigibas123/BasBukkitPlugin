package com.mooo.dingemans.bigibas123.ServerChangeGui.config;


import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class Config {
    private static final JavaPlugin plugin = Reference.plugin;

    public static void save() {
        getConfig().set("SCG.general.paragraphForCopy", "\u00A7");
        plugin.saveConfig();
    }

    private static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static void reload() {
        plugin.reloadConfig();

    }

    public static String getItem(String server) {
        String itm = getConfig().getString("SCG.items." + server + ".item");
        if (itm == null || Objects.equals(itm, "")) {
            return Material.HARD_CLAY.name();
        } else {
            return itm;
        }
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
        int s;
        if (!getConfig().contains("SCG.items." + server + ".location")) {
            s = 1;
        } else {
            s = getConfig().getInt("SCG.items." + server + ".location");
        }
        return s;
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
        String nm = getConfig().getString("SCG.items." + server + ".customName");
        if (nm == null || Objects.equals(nm, "")) {
            return server;
        } else {
            return nm;
        }
    }

    public static void setVisibility(String server, boolean vis) {
        getConfig().set("SCG.items." + server + ".visible", vis);
    }

    public static boolean getVisibility(String server) {
        return !getConfig().contains("SCG.items." + server + ".visible") || getConfig().getBoolean("SCG.items." + server + ".visible");
    }

    public static String getMenuName() {
        if (getConfig().getString("SCG.general.menuName") == null || getConfig().getString("SCG.general.menuName").equals("")) {
            return "Server Chooser";
        } else {
            return getConfig().getString("SCG.general.menuName");
        }
    }

    public static void setMenuName(String name) {
        getConfig().set("SCG.general.menuName", name);
    }


}
