package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Config {

    private static String getPrefix() {
        return Reference.plugin.getDescription().getPrefix();
    }

    private FileConfiguration getConfig() {
        return Reference.plugin.getConfig();
    }

    public void save() {
        Reference.plugin.saveConfig();
    }

    public void reload() {
        Reference.plugin.reloadConfig();
    }

    public ItemStack getServerItem(String name) {
        ConfigurationSection serverSect = this.getSection("server", name);
        if (serverSect.isSet("item")) {
            return serverSect.getItemStack("item");
        } else {
            Material[] mats = Material.values();
            ItemStackBuilder rng = ItemStackBuilder.of(mats[new Random().nextInt(mats.length)]).transformMeta(m -> m.setDisplayName(name));
            return rng.build();
        }
    }

    public void setServerItem(String name, ItemStack item) {
        ConfigurationSection serverSect = this.getSection("server", name);
        serverSect.set("item", item);
    }

    public Integer getServerSlot(String name) {
        return this.getSection("server", name).getInt("slot", 0);
    }

    public void setServerSlot(String name, int slot) {
        this.getSection("server", name).set("slot", slot);
    }

    public Set<String> getServerList() {
        Set<String> servers = getSection("server").getKeys(false);

        if (servers == null) {
            servers = new HashSet<>();
        }
        return servers;
    }

    public int getMenuWidth() {
        return this.getSection("general", "menu").getInt("width", 9);
    }

    public void setMenuWidth(int width) {
        this.getSection("general", "menu").set("width", width);
    }

    public String getMenuTitle() {
        return getSection("general", "menu").getString("title", "Servers");
    }

    public void setMenuTitle(String title) {
        this.getSection("general", "menu").set("title", title);
    }

    private ConfigurationSection getSection(String... names) {
        ConfigurationSection sect = getConfig().getConfigurationSection(quickDot(getPrefix(), quickDot(names)));
        if (sect == null) {
            sect = getConfig().createSection(quickDot(getPrefix(), quickDot(names)));
        }
        return sect;
    }

    private ConfigurationSection getSection(ConfigurationSection parent, String... names) {
        ConfigurationSection sect = parent.getConfigurationSection(quickDot(names));
        if (sect == null) {
            sect = getConfig().createSection(quickDot(names));
        }
        return sect;
    }

    private String quickDot(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String s : args) {
            builder.append(s).append(".");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
