package ga.dingemans.bigibas123.ServerChangeGui.config;


import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static final JavaPlugin plugin = Reference.plugin;

    public static void save() {
        plugin.saveConfig();
    }
    public static FileConfiguration getConfig() {
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
}
