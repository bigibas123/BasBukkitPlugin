package com.mooo.dingemans.bigibas123.ServerChangeGui;


import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.Chatcreator;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.IconMenu;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.ServerInfo;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("Convert2Diamond")
public class SCGMain extends Thread {
    public SCGMain() {
        super();
        this.setPriority(MIN_PRIORITY);
        this.setName("SCGMain" + Reference.rnd.nextInt());
        this.start();
    }

    @Override
    public void run() {
        try {
            Reference.ServerListGenerated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.createServerInfos();
        this.createMenu();
    }

    private void createMenu() {
        Integer maxspot = -1;
        for (Integer i : Reference.spots.keySet()) {
            if (maxspot < i) {
                maxspot = i;
            }
        }
        String menname = Config.getMenuName();
        if (menname == null) {
            menname = "Server Change Gui";
            Config.setMenuName(menname);
        }
        int rest = maxspot % 9;
        maxspot = maxspot - rest;
        maxspot = maxspot + 9;
        IconMenu menu = new IconMenu(menname, maxspot, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                Chatcreator cc = new Chatcreator(ChatColor.GREEN, "Connecting you to " + event.getName());
                event.getPlayer().sendMessage(cc.create());
                ServerInfo serverinfo = Reference.locationToServerInfo.get(event.getPosition());
                Messaging.send(new String[]{"ConnectOther", event.getPlayer().getName(), serverinfo.getName()}, event.getPlayer());
                event.setWillClose(true);
            }
        }, Reference.plugin);
        Reference.locationToServerInfo = new HashMap<Integer, ServerInfo>();
        for (Map.Entry<String, ServerInfo> entry : Reference.serverMap.entrySet()) {
            ServerInfo si = entry.getValue();
            menu.setOption(si.getLocation(), si.getItem(), si.getCustomname(), si.getLore());
            Reference.locationToServerInfo.put(si.getLocation(), si);
        }
        Reference.menu = menu;
        Config.save();
    }

    private void createServerInfos() {
        Reference.spots = new HashMap<Integer, Boolean>();
        Reference.serverMap = new HashMap<String, ServerInfo>();
        for (String server : Reference.serverList) {
            Reference.serverMap.put(server, new ServerInfo(server));
        }
    }
}
