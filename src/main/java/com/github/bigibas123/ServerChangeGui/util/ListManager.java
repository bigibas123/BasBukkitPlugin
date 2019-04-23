package com.github.bigibas123.ServerChangeGui.util;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ListManager {

    public HashMap<Integer, VarPair<String, ItemStack>> menuItems;

    public ListManager(List<String> servers) {
        this.menuItems = new HashMap<>();
        this.update(servers);
    }

    public void update(List<String> serverList) {
        for (String serverName : serverList) {
            Reference.config.getServerList();
        }
    }
}
