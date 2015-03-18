package ga.dingemans.bigibas123.BasBukkitPlugin.util;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import ga.dingemans.bigibas123.BasBukkitPlugin.BasBukkitPlugin;
import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

@SuppressWarnings("UnusedDeclaration")
public class Messaging {
    public static void recieve(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServers")) {
            String[] serverList = in.readUTF().split(", ");
            LogHelper.INFO(Arrays.toString(serverList));
            Reference.serverList = serverList;
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
            player.sendPluginMessage(BasBukkitPlugin.getProvidingPlugin(BasBukkitPlugin.class), "BungeeCord", out.toByteArray());
            return true;
        } else {
            return false;
        }
    }

}
