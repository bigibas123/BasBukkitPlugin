package com.github.bigibas123.ServerChangeGui.util;

import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.lucko.helper.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BungeeCord implements PluginMessageListener {

    private static final String channel = "BungeeCord";
    private final ServerChangeGui plugin;
    private final HashMap<String, ArrayList<CompletableFuture<ByteArrayDataInput>>> waits;

    public BungeeCord(ServerChangeGui serverChangeGui, Messenger messenger) {
        messenger.registerOutgoingPluginChannel(serverChangeGui, channel);
        messenger.registerIncomingPluginChannel(serverChangeGui, channel, this);
        this.plugin = serverChangeGui;
        this.waits = new HashMap<>();
    }

    private void sendPluginMessage(String subChannel, String... args) {
        PluginMessageRecipient recipient = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        if (recipient == null) {
            LogHelper.INFO("Scheduled plugin msg:" + subChannel + " for a later time");
            Events.subscribe(PlayerLoginEvent.class).expireAfter(1).handler(e -> {
                this.sendPluginMessage(subChannel, e.getPlayer(), args);
            }).bindWith(plugin);
        } else {
            this.sendPluginMessage(subChannel, recipient, args);
        }
    }

    private void sendPluginMessage(String subChannel, PluginMessageRecipient recipient, String... args) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        for (String arg : args) {
            out.writeUTF(arg);
        }
        recipient.sendPluginMessage(plugin, channel, out.toByteArray());
        LogHelper.INFO("Sent: Channel:" + channel + " Subchannel:" + subChannel + " Content:" + Arrays.toString(out.toByteArray()));
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!BungeeCord.channel.equals(channel)) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (waits.containsKey(subChannel)) {
            waits.get(subChannel).remove(0).complete(in);
            LogHelper.INFO("Received pluginMessage:" + subChannel + " still queued:" + waits.get(subChannel).size());
        } else {
            LogHelper.WARNING("Received plugin message without listening for it:");
            LogHelper.WARNING("SubChannel:" + subChannel + " Contents:" + in.readUTF());
        }

    }

    public CompletableFuture<List<String>> getServers() {
        String sub = "GetServers";
        CompletableFuture<ByteArrayDataInput> fut = new CompletableFuture<>();

        CompletableFuture<List<String>> returnedFuture = fut.thenApplyAsync(in -> {
            List<String> list = Arrays.asList(in.readUTF().split(", "));
            LogHelper.INFO("Received server list:" + Arrays.toString(list.toArray()));
            return list;
        });

        if (!waits.containsKey(sub)) waits.put(sub, new ArrayList<>());

        waits.get(sub).add(fut);

        sendPluginMessage(sub);
        return returnedFuture;
    }


    public void connect(PluginMessageRecipient player, String server) {
        sendPluginMessage("Connect", player, server);
    }
}
