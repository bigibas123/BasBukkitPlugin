package com.mooo.dingemans.bigibas123.ServerChangeGui;

import com.mooo.dingemans.bigibas123.ServerChangeGui.util.Chatcreator;
import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ServerChangeGui extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        Reference.plugin = this;
        Config.save();
        Reference.SCGmain = new SCGMain();
    }


    public void onLoad() {

    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        if (!Reference.SCGmain.isInterrupted() || !Reference.SCGmain.isAlive()) {
            Reference.SCGmain.interrupt();
        }

    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        Messaging.receive(channel, player, message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("SCG")) {
            Chatcreator cc = new Chatcreator(ChatColor.GREEN, "");
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Messaging.send(new String[]{"GetServers"}, player);
                if (Reference.ServerListGenerated.getCount() == 1) {
                    cc.append("Serverlist not fetched", ChatColor.RED);
                    cc.newLine();
                    cc.append("Fetching for you...", ChatColor.GREEN);
                    cc.newLine();
                    cc.append("Please rerun the command", ChatColor.YELLOW);
                    player.sendMessage(cc.create());
                    Messaging.send(new String[]{"GetServers"}, player);
                    return true;
                } else if (Reference.menu == null) {
                    if (!Reference.SCGmain.isAlive()) {
                        cc.append("Menu not Created", ChatColor.RED);
                        cc.newLine();
                        cc.append("Creating For you...", ChatColor.GREEN);
                        cc.newLine();
                        cc.append("Please rerun the command", ChatColor.YELLOW);
                        player.sendMessage(cc.create());
                        Reference.SCGmain = new SCGMain();
                        return true;
                    } else {
                        cc.append("Menu Creation Currently running", ChatColor.RED);
                        cc.newLine();
                        cc.append("Please wait", ChatColor.GREEN);
                        player.sendMessage(cc.create());
                        return true;
                    }
                }

                if (Reference.listupdated.getCount() == 0 && !Reference.SCGmain.isAlive()) {
                    Reference.SCGmain = new SCGMain();
                }

                Reference.menu.open(player);
                return true;
            } else {
                cc.append("This command is only for players", ChatColor.RED);
                cc.newLine();
                cc.append("You are:" + sender.getName(), ChatColor.RED);
                cc.newLine();
                cc.append("Type:" + sender.toString(), ChatColor.RED);
                sender.sendMessage(cc.create());
                return true;
            }
        }
        return false;
    }


}
