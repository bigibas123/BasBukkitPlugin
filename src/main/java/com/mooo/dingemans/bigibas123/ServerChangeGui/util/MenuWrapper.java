package com.mooo.dingemans.bigibas123.ServerChangeGui.util;

import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import lombok.Getter;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.messaging.bungee.BungeeMessaging;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


public class MenuWrapper {
    @Getter
    private ServerMenu menu;

    public MenuWrapper(Player p) {
        //this.menu = new ServerMenu()
        int maxSlot = 0;
        for (Map.Entry<String, serverObj> s : Reference.server.entrySet()) {
            if (s.getValue().isVisible()) {
                if (maxSlot < s.getValue().getSlotNumber()) {
                    maxSlot = s.getValue().getSlotNumber();
                }
            }
        }
        int ms;
        if (maxSlot % 9 == 0) {
            ms = maxSlot;
        } else {
            ms = maxSlot + (9 - (maxSlot % 9));
        }
        if (ms == 0) ms = 9;
        this.menu = new ServerMenu(p, ms / 9, Config.getMenuName(), Reference.server);
    }


    public class ServerMenu extends Gui {
        private HashMap<String, serverObj> sm;

        public ServerMenu(Player player, int lines, String title, HashMap<String, serverObj> sm) {
            super(player, lines, title);
            this.sm = sm;
        }

        @Override
        public void redraw() {
            for (serverObj s : sm.values()) {
                if (s.isVisible()) {
                    HashMap<ClickType, Consumer<InventoryClickEvent>> handlers = new HashMap<>();
                    ICECons ch = new ICECons(s.getName());
                    for (ClickType t : ClickType.values()) {
                        handlers.put(t, ch);
                    }
                    Item itm = new Item(handlers, s.getItem());
                    this.setItem(s.getSlotNumber(), itm);
                }
            }
        }

        private class ICECons implements Consumer<InventoryClickEvent> {
            private final String serverName;

            public ICECons(String name) {
                this.serverName = name;
            }

            @Override
            public void accept(InventoryClickEvent inventoryClickEvent) {
                BungeeMessaging.connect((Player) inventoryClickEvent.getWhoClicked(), this.serverName);
            }

        }
    }
}