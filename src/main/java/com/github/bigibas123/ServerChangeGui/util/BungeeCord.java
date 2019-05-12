package com.github.bigibas123.ServerChangeGui.util;

import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BungeeCord implements PluginMessageListener {

    private static final String channel = "BungeeCord";
    @Getter(AccessLevel.PRIVATE)
    private final ServerChangeGui plugin;
    private final HashMap<String, ArrayList<CompletableFuture<ByteArrayDataInput>>> waits;
    private final ArrayList<PluginMessage> scheduled;

    public BungeeCord(ServerChangeGui serverChangeGui, Messenger messenger) {
        messenger.registerOutgoingPluginChannel(serverChangeGui, channel);
        messenger.registerIncomingPluginChannel(serverChangeGui, channel, this);
        this.plugin = serverChangeGui;
        this.waits = new HashMap<>();
        this.scheduled = new ArrayList<>();
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!BungeeCord.channel.equals(channel)) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (waits.containsKey(subChannel)) {
            waits.get(subChannel).remove(0).complete(in);
             plugin.getLog().INFO("Received pluginMessage:" + subChannel + " still queued:" + waits.get(subChannel).size());
        } else {
             plugin.getLog().WARNING("Received plugin message without listening for it:");
             plugin.getLog().WARNING("SubChannel:" + subChannel + " Contents:" + in.readUTF());
        }

    }

    public CompletableFuture<List<String>> getServers() {
        String sub = "GetServers";
        CompletableFuture<ByteArrayDataInput> fut = new CompletableFuture<>();

        CompletableFuture<List<String>> returnedFuture = fut.thenApplyAsync(in -> {
            List<String> list = Arrays.asList(in.readUTF().split(", "));
             plugin.getLog().INFO("Received server list:" + Arrays.toString(list.toArray()));
            return list;
        });

        if (!this.waits.containsKey(sub)) this.waits.put(sub, new ArrayList<>());

        this.waits.get(sub).add(fut);

        new PluginMessage(sub).send();
        return returnedFuture;
    }


    public void connect(PluginMessageRecipient player, String server) {
        new PluginMessage("Connect", player, server).send();
    }

    public void playerLogin(Player player) {
        if(scheduled.size() > 0) {
            Bukkit.getScheduler().runTaskAsynchronously(this.getPlugin(), () -> {
                scheduled.removeIf(pluginMessage -> {
                    pluginMessage.send();
                    return true;
                });
            });
        }
    }

    @Getter
    public class PluginMessage {

        private final String subchannel;
        private final String[] args;
        @Setter
        private PluginMessageRecipient recipient;
        private boolean forceRecipient;

        public PluginMessage(String subchannel, PluginMessageRecipient recipient, String... args) {
            this.subchannel = subchannel;
            this.recipient = recipient;
            this.args = args;
            this.forceRecipient = true;
        }

        public PluginMessage(String subchannel, String... args) {
            this.subchannel = subchannel;
            this.args = args;
            this.forceRecipient = false;
        }

        public void send() {
            if (!this.forceRecipient) {
                if (this.getRecipient() == null) {
                    Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
                    if (player == null) {
                         plugin.getLog().INFO("Scheduling pluginmessage: " + subchannel + " for a later time");
                        scheduled.add(this);
                        return;
                    } else {
                        this.recipient = player;
                    }
                }
            } else {
                if (this.getRecipient() == null) {
                     plugin.getLog().WARNING("Recipient is null but forced, Discarding pluginMessage: " + this.getSubchannel());
                    return;
                }
            }
            if (this.getRecipient() instanceof Player) {
                 plugin.getLog().INFO("Sending pluginMessage: " + this.getSubchannel() + " to: " + ((Player) this.getRecipient()).getName());
            } else {
                 plugin.getLog().INFO("Sending pluginMEssage: " + this.getSubchannel());
            }
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(this.getSubchannel());
            for (String arg : this.getArgs()) {
                out.writeUTF(arg);
            }
            this.getRecipient().sendPluginMessage(plugin, channel, out.toByteArray());
        }
    }
}
