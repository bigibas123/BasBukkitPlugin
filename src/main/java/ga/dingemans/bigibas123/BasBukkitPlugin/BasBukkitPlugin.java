package ga.dingemans.bigibas123.BasBukkitPlugin;

import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.msgThreads.serverlist;
import ga.dingemans.bigibas123.BasBukkitPlugin.config.Config;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Chatcreator;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BasBukkitPlugin extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        Reference.plugin = this;

        Config.load();

        Reference.serverlistthread = new serverlist();
        Reference.serverlistthread.start();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        Messaging.recieve(channel, player, message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("BBP")) {
            if (Reference.serverList == null) {
                Chatcreator chatmsg = new Chatcreator(ChatColor.RED, "please wait for the servers to be fetched");
                chatmsg.newLine();
                sender.sendMessage(chatmsg.create());
                Reference.serverlistthread.waitingtime = 0;
                chatmsg = new Chatcreator(ChatColor.RED, "Refetching for you...");
                sender.sendMessage(chatmsg.create());
                return true;
            } else if (Reference.menu == null) {
                Reference.serverlistthread.createmenu();
            }
            if (sender instanceof Player) {
                    //noinspection ConstantConditions
                    Reference.menu.open((Player) sender);
                } else {
                    sender.sendMessage("Command can only be run by player");
                }


            return true;
        }
        return false;
    }
}
