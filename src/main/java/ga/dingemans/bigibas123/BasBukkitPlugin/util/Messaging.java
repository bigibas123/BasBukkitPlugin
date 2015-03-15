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
import java.util.logging.Level;


public class Messaging {
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
            LogHelper.INFO("message sent");
            return true;
        } else {
            LogHelper.WARNING("no players found to send the pluginmessage to,\n I hope the pluginmaker accounted for this");
            return false;
        }
    }

    public static void recieve(String channel, @SuppressWarnings("UnusedParameters") Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServers")) {
            Bukkit.getLogger().log(Level.INFO, "[BasPlugin]message recieved");
            String[] serverList = in.readUTF().split(", ");
            LogHelper.INFO(Arrays.toString(serverList));
            Reference.serverList = serverList;
            }


    }
}
