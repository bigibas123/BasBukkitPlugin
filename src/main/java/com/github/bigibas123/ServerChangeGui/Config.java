package com.github.bigibas123.ServerChangeGui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.Set;

public class Config {

    @Getter
    private final ServerChangeGui plugin;

    public Config(ServerChangeGui plugin) {

        this.plugin = plugin;
    }

    private String getPrefix() {
        String prefix = this.getPlugin().getDescription().getPrefix();
        if(prefix == null) {
            return "SCG";
        }else {
            return prefix;
        }
    }

    private FileConfiguration getConfig() {
        return this.getPlugin().getConfig();
    }

    public void save() {
        this.getPlugin().saveConfig();
    }

    public void reload() {
        this.getPlugin().reloadConfig();
    }

    public ItemStack getServerItem(String name) {
        ConfigurationSection serverSect = this.getSection("server", name);
        if (serverSect.isSet("item")) {
            return serverSect.getItemStack("item");
        } else {
            Material[] mats = Material.values();
            ItemStack rngStack = new ItemStack(mats[new Random().nextInt(mats.length)]);
            @Nullable ItemMeta meta = rngStack.getItemMeta();
            assert meta != null;
            meta.setDisplayName(name);
            rngStack.setItemMeta(meta);
            return rngStack;
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

        return getSection("server").getKeys(false);
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

    @SuppressWarnings("unused")
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
