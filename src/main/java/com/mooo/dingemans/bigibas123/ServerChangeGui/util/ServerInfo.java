package com.mooo.dingemans.bigibas123.ServerChangeGui.util;

import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class ServerInfo {
    @Getter @Setter private String name;
    @Getter @Setter private ItemStack item;
    @Getter @Setter private String itemmaterial;
    @Getter @Setter private short durability;
    @Getter @Setter private int location;
            @Setter private List<String> lore;
    @Getter @Setter private String customname;

    public ServerInfo(String name) {
        this.name = name;
        checkName();
        this.itemmaterial = Config.getItem(this.name);
        checkItemMaterial();
        this.durability = Config.getDurability(this.name);
        try {
            this.item = new ItemStack(Material.getMaterial(Config.getItem(this.itemmaterial)), 1, this.durability);
        } catch (NullPointerException ignored) {
        }
        checkItem();
        this.location = Config.getLocation(this.name);
        checkLocation();
        this.customname = Config.getName(this.name);
        checkCustomName();
        this.lore =  Config.getLore(this.name);
        checkLore();
        ItemMeta itemmet = this.item.getItemMeta();
        itemmet.setDisplayName(this.customname);
        itemmet.setLore(this.lore);
        this.item.setItemMeta(itemmet);
        safetoConfig();
    }

    private void checkName() {
        if (this.name == null) {
            this.name = "ERROR SERVER NAME WRONG";
        }
    }

    private void checkItemMaterial() {
        if (this.itemmaterial == null) {
            this.itemmaterial = Material.STAINED_CLAY.name();
        }
    }

    private void checkItem() {
        if (this.item == null) {
            this.item = new ItemStack(Material.STAINED_CLAY, 1, (short) Reference.rnd.nextInt(15));
            this.durability = this.item.getDurability();
        }
    }

    private void checkLore() {
        if (this.lore == null) {
            this.lore = new ArrayList<>();
            this.lore.add("Connects you to the " + this.customname + " server");
        }
    }

    private void checkLocation() {
        boolean taken = false;
        Integer maxspots = -1;
        for (Integer i : Reference.spots.keySet()) {
            if (maxspots < i) {
                maxspots = i;
            }
            if (i == this.location) {
                taken = true;
            }
        }
        if (taken) {
            this.location = maxspots + 1;
        }
        Reference.spots.put(this.location, true);
    }

    private void checkCustomName() {
        if (this.customname == null) {
            this.customname = this.name;
        }
    }

    public void safetoConfig() {
        Config.setItem(this.name, this.item.getData().getItemType().name());
        Config.setDurability(this.name, this.durability);
        Config.setLocation(this.name, this.location);
        Config.setLore(this.name, this.lore);
        Config.setName(this.name, this.customname);
        Config.save();
    }
    public String[] getLore(){
        return lore.toArray(new String[lore.size()]);
    }
}
