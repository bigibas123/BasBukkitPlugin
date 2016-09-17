package com.mooo.dingemans.bigibas123.ServerChangeGui.util;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class Messaging {
    public static void receive(String channel, Player player, byte[] message) {
        player.getDisplayName();//unusedDeclaration annoying
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        LogHelper.FINE("Received message from:" + subchannel);
        if (subchannel.equals("GetServers")) {
            Reference.serverList = new ArrayList<>();
            String[] tempservers = in.readUTF().split(", ");
            Collections.addAll(Reference.serverList, tempservers);
            Reference.ServerListGenerated.countDown();
        } else if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playercount = in.readInt();
            ServerInfo serverinf = Reference.serverMap.get(server);
            if (serverinf == null) {
                if (!Reference.serverList.contains(server)) {
                    Reference.serverList.add(server);
                    Reference.listupdated.countDown();
                }
            }
            if (serverinf != null) {
                ItemStack itm = serverinf.getItem();
                itm.setAmount(playercount);
                serverinf.setItem(itm);
            }
            Reference.listupdated.countDown();

        }
    }

    public static boolean send(String[] args, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String arg : args) {
            out.writeUTF(arg);
        }
        if (player == null) {
            player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        }

        if (player != null) {
            player.sendPluginMessage(Reference.plugin, "BungeeCord", out.toByteArray());
            return true;
        } else {
            return false;
        }
    }

}
