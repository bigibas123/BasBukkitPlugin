package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.ChatHelper;
import com.github.bigibas123.ServerChangeGui.util.LogHelper;
import com.github.bigibas123.ServerChangeGui.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.lucko.helper.Schedulers;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Menu {
    private String title;
    private int width;
    private int lines;
    private ArrayList<Integer> takenSlots;
    private HashMap<String, ServerItem> items;

    public Menu() {
        this.update(Reference.getConfig().getServerList());
    }

    public boolean setItem(String server, ItemStack stack) {
        ServerItem si = this.items.get(server);
        if (si == null) return false;
        ServerItem nsi = new ServerItem(si.getServerName(), si.getSlot(), stack);
        this.items.put(server, nsi);
        return true;
    }

    public boolean setSlot(String server, Integer slot) {
        ServerItem si = this.items.get(server);
        if (si == null) return false;
        ServerItem nsi = new ServerItem(si.getServerName(), slot, si.getStack());
        this.items.put(server, nsi);
        return true;
    }

    public List<String> getServers() {
        return new ArrayList<>(this.items.keySet());
    }

    public void requestUpdate() {
        Reference.getBungee().getServers().thenAccept(this::update);
    }

    public void update(Collection<String> servers) {
        title = Reference.getConfig().getMenuTitle();
        width = Reference.getConfig().getMenuWidth();
        this.takenSlots = new ArrayList<>();
        this.items = new HashMap<>();
        int max = -1;
        for (String server : servers) {
            Integer slot = Reference.getConfig().getServerSlot(server);
            if (takenSlots.contains(slot)) {

                int oldSlot = slot;
                slot = this.getNextOpenSlot(slot);
                LogHelper.INFO(String.format("Slot %1s taken setting to slot:%3s", oldSlot, slot));
            }
            ItemStack item = Reference.getConfig().getServerItem(server);
            this.items.put(server, new ServerItem(server, slot, item));
            this.takenSlots.add(slot);
            max = Math.max(max, slot + 1);
        }
        this.lines = Gui.getMenuSize(max, width);
        LogHelper.FINE("width:" + width + " lines:" + lines + " taken slots:" + Arrays.toString(takenSlots.toArray()));
    }

    private Integer getNextOpenSlot(Integer slot) {
        int cur = slot;
        boolean done = false;
        while (!done) {
            if (takenSlots.contains(cur)) {
                cur++;
            } else {
                done = true;
            }
        }
        return cur;
    }

    public void save() {
        Config cfg = Reference.getConfig();
        cfg.setMenuTitle(this.title);
        cfg.setMenuWidth(this.width);
        for (ServerItem item : this.items.values()) {
            cfg.setServerSlot(item.getServerName(), item.getSlot());
            cfg.setServerItem(item.getServerName(), item.getStack());
        }
        cfg.save();
    }

    public void open(Player player) {
        if (this.items != null && this.items.size() > 0) {
            ServerMenu menu = new ServerMenu(player, this.lines, this.title, this.items);
            Schedulers.sync().run(menu::open);
        } else {
            new ChatHelper(player, ChatHelper.level.WARN).append("Menu not fetched").newLine(ChatHelper.level.DEFAULT)
                    .append("Fetching now...").send();
            this.requestUpdate();
        }
    }

    public void reload() {
        this.update(Reference.getConfig().getServerList());
    }

    private class ServerMenu extends Gui {

        private final HashMap<String, ServerItem> items;

        public ServerMenu(Player player, int lines, String title, HashMap<String, ServerItem> items) {
            super(player, lines, title);
            HashMap<String, ServerItem> allowedItems = new HashMap<>();
            for (Map.Entry<String, ServerItem> item : items.entrySet()) {
                if (Util.hasPermission(player, "SCG.use." + item.getValue().getServerName())) {
                    allowedItems.put(item.getKey(), item.getValue());
                }
            }
            this.items = allowedItems;
        }

        @Override
        public void redraw() {
            if (this.isFirstDraw()) {
                for (Map.Entry<String, ServerItem> entry : this.items.entrySet()) {
                    Slot slot = this.getSlot(entry.getValue().getSlot());
                    slot.applyFromItem(Item.builder(entry.getValue().getStack())
                            .bind(inventoryClickEvent -> {
                                if (inventoryClickEvent.getWhoClicked() instanceof Player) {
                                    Reference.getBungee().connect((Player) inventoryClickEvent.getWhoClicked(), entry.getValue().getServerName());
                                }
                            }, ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE)
                            .build());
                }
            }
        }
    }

    @AllArgsConstructor
    @Data
    private class ServerItem {
        private final String serverName;
        private final Integer slot;
        private final ItemStack stack;

    }
}
