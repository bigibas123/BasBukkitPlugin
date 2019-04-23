package com.github.bigibas123.ServerChangeGui.util;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class Config {

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
        return getConfig().getItemStack(quickDot(Reference.plugin.getDescription().getPrefix(), "servers", name, "item"));
    }

    public Integer getServerSlot(String name) {
        return getConfig().getInt(quickDot(Reference.plugin.getDescription().getPrefix(), "servers", name, "slot"), 0);
    }


    private String quickDot(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String s : args) {
            builder.append(s).append(".");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public Set<String> getServerList() {
        return getConfig().getConfigurationSection(quickDot(Reference.plugin.getDescription().getPrefix(), "servers")).getKeys(false);
    }
}
