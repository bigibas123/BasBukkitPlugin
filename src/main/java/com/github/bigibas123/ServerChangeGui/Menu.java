package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.util.BungeeCord;
import com.github.bigibas123.ServerChangeGui.util.ChatHelper;
import com.github.bigibas123.ServerChangeGui.util.LogHelper;
import com.github.bigibas123.ServerChangeGui.util.Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.lucko.helper.Schedulers;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.Slot;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import java.util.*;
import java.util.function.BiConsumer;

public class Menu implements IMenu {
    private String title;
    private int width;
    private int lines;
    private ArrayList<Integer> takenSlots;
    private HashMap<String, ServerItem> items;
    private Config config;
    private BungeeCord bungee;
    private LogHelper log;

    public Menu(Config config, BungeeCord bungee, LogHelper log) {
        this.config = config;
        this.bungee = bungee;
        this.log = log;
        this.update(config.getServerList());
    }

    public boolean setItem(String server, ItemStack stack) {
        ServerItem si = this.items.get(server);
        if (si == null) return false;
        ServerItem nsi = new ServerItem(si.getServerName(), si.getSlot(), stack);
        this.items.put(server, nsi);
        return true;
    }

    public boolean isSlotFree(int slot) {
        return !takenSlots.contains(slot);
    }

    public boolean setSlot(String server, int slot) {
        ServerItem si = this.items.get(server);
        if (si == null) return false;
        if (!takenSlots.contains(slot)) {
            ServerItem nsi = new ServerItem(si.getServerName(), slot, si.getStack());
            this.items.put(server, nsi);
            this.takenSlots.add(slot);
            return true;
        } else {

            return false;
        }
    }

    public List<String> getServers() {
        return new ArrayList<>(this.items.keySet());
    }


    public ArrayList<Integer> getTakenSlots() {
        return new ArrayList<>(takenSlots);
    }

    public void requestUpdate() {
        this.bungee.getServers().thenAccept(this::update);
    }

    public void update(Collection<String> servers) {
        title = config.getMenuTitle();
        width = config.getMenuWidth();
        this.takenSlots = new ArrayList<>();
        this.items = new HashMap<>();
        int max = -1;
        for (String server : servers) {
            Integer slot = config.getServerSlot(server);
            if (takenSlots.contains(slot)) {

                int oldSlot = slot;
                slot = this.getNextOpenSlot(slot);
                log.INFO(String.format("Slot %1s taken setting to slot:%3s", oldSlot, slot));
            }
            ItemStack item = config.getServerItem(server);
            this.items.put(server, new ServerItem(server, slot, item));
            this.takenSlots.add(slot);
            max = Math.max(max, slot + 1);
        }
        this.lines = Gui.getMenuSize(max, width);
        log.FINE("width:" + width + " lines:" + lines + " taken slots:" + Arrays.toString(takenSlots.toArray()));
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
        Config cfg = config;
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
            ServerMenu menu = new ServerMenu(player, this.width, this.title, this.items, (ent, serverName) -> bungee.connect((PluginMessageRecipient) ent,serverName));
            Schedulers.sync().run(menu::open);
        } else {
            new ChatHelper(player, ChatHelper.level.WARN).append("Menu not fetched").newLine(ChatHelper.level.DEFAULT)
                    .append("Fetching now...").send();
            this.requestUpdate();
        }
    }

    public void reload() {
        this.update(config.getServerList());
    }

    private static class ServerMenu extends Gui {

        private final HashMap<String, ServerItem> items;
        private final BiConsumer<HumanEntity, String> clickCallback;

        private static int getLineCount(Collection<ServerItem> items, int width){
            int max = -1;
            for (ServerItem item : items) {
                int slot = item.getSlot();
                max = Math.max(slot, max);
            }
            return getMenuSize(max+1,width);
        }

        public ServerMenu(Player player, int width, String title, HashMap<String, ServerItem> items, BiConsumer<HumanEntity,String> clickCallback) {
            super(player, getLineCount(items.values(),width), title);
            this.clickCallback = clickCallback;
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
                                    clickCallback.accept(inventoryClickEvent.getWhoClicked(),entry.getValue().getServerName());
                                }
                            }, ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE)
                            .build());
                }
            }
        }
    }

    @AllArgsConstructor
    @Data
    private static class ServerItem {
        private final String serverName;
        private final Integer slot;
        private final ItemStack stack;

    }
}
