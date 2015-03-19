package ga.dingemans.bigibas123.BasBukkitPlugin.config;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private static JavaPlugin plugin = Reference.plugin;

    public static void load() {
        plugin.saveConfig();
        //reload();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void reload() {
        plugin = Reference.plugin;
        plugin.reloadConfig();
    }

    public static FileConfiguration getconfig() {
        return plugin.getConfig();
    }

    public static String getItem(String server) {
        return getconfig().getString("BasPlugin.items." + server + ".item");
    }

    public static short getDurability(String server) {
        return (short) getconfig().getInt("BasPlugin.items." + server + ".durabiltiy");
    }

    public static void setItem(String server, String item) {
        getconfig().set("BasPlugin.items." + server + ".item", item);

    }

    public static void setDurability(String server, short dura) {
        getconfig().set("BasPlugin.items." + server + ".durabiltiy", dura);
    }
}
