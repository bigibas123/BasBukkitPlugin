package com.mooo.dingemans.bigibas123.ServerChangeGui.util;

import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class serverObj {

    @Getter
    private final String name;
    @Getter
    private final String displayName;
    @Getter
    private final ItemStack item;
    @Getter
    private final List<String> lore;
    @Getter
    private int slotNumber;

    public serverObj(String name) {
        this.name = name;
        this.displayName = Config.getName(this.getName());
        this.item = new ItemStack(Material.matchMaterial(Config.getItem(this.getName())),
                1,
                Config.getDurability(this.getName()));
        this.slotNumber = Config.getLocation(this.getName());
        this.lore = Config.getLore(this.getName());
        ItemMeta meta = this.getItem().getItemMeta();
        meta.setLore(this.getLore());
        meta.setDisplayName(this.getDisplayName());
        this.getItem().setItemMeta(meta);
    }


    public void save() {
        Config.setItem(this.getName(), this.getItem().getType().name());
        Config.setDurability(this.getName(), this.getItem().getDurability());
        Config.setLocation(this.getName(), this.getSlotNumber());
        Config.setName(this.getName(), this.getDisplayName());
        Config.setLore(this.getName(), this.getLore());
    }
}
