package ga.dingemans.bigibas123.ServerChangeGui.util;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ga.dingemans.bigibas123.ServerChangeGui.ServerChangeGui;
import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
            Reference.serverList = in.readUTF().split(", ");
            Reference.ServerListGenerated.countDown();
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
            player.sendPluginMessage(ServerChangeGui.getProvidingPlugin(ServerChangeGui.class), "BungeeCord", out.toByteArray());
            return true;
        } else {
            return false;
        }
    }

}
