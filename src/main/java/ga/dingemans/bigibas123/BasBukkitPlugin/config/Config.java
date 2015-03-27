package ga.dingemans.bigibas123.BasBukkitPlugin.config;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static final JavaPlugin plugin = Reference.plugin;

    public static void load() {
        plugin.saveConfig();
    }
    public static FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public static String getItem(String server) {
        return getConfig().getString("BasPlugin.items." + server + ".item");
    }

    public static short getDurability(String server) {
        return (short) getConfig().getInt("BasPlugin.items." + server + ".durability");
    }

    public static void setItem(String server, String item) {
        getConfig().set("BasPlugin.items." + server + ".item", item);

    }

    public static void setDurability(String server, short dur) {
        getConfig().set("BasPlugin.items." + server + ".durability", dur);
    }
}
