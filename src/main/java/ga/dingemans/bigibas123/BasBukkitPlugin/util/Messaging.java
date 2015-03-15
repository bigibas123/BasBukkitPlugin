package ga.dingemans.bigibas123.BasBukkitPlugin.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ga.dingemans.bigibas123.BasBukkitPlugin.BasBukkitPlugin;
import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Messaging {
    public static void send(String[] args, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Reference.lastmsg = args[1];
        out.writeUTF(Reference.PluginChannelname);
        for (String arg : args) {
            out.writeUTF(arg);
        }
        if (player == null) {
            player = Bukkit.getPlayer(Reference.myUUID);
        }

        player.sendPluginMessage(BasBukkitPlugin.getProvidingPlugin(BasBukkitPlugin.class), "BungeeCord", out.toByteArray());
    }

    public static void recieve(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals(Reference.PluginChannelname)) {
            if (Reference.lastmsg.equals("ServerList")) {
                Reference.serverList = in.readUTF().split(", ");
            }

        }
    }
}
