package ga.dingemans.bigibas123.ServerChangeGui;

import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import ga.dingemans.bigibas123.ServerChangeGui.Threads.SCGmain;
import ga.dingemans.bigibas123.ServerChangeGui.config.Config;
import ga.dingemans.bigibas123.ServerChangeGui.util.Chatcreator;
import ga.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;

public class ServerChangeGui extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        Reference.plugin = this;
        Reference.playercount = new HashMap<>();
        Config.save();
        Reference.SCGmain = new SCGmain();
    }


    public void onLoad() {

    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        if (!Reference.SCGmain.isInterrupted()) {
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
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Reference.SCGmain.fetchplayercount();
                if (Reference.ServerListGenerated.getCount() == 1) {
                    Messaging.send(new String[]{"GetServers"}, player);
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "Servers not fetched");
                    cc.newLine();
                    cc.append("Fetching for you", ChatColor.DARK_GREEN);
                    cc.newLine();
                    cc.append("Please re-execute the command", ChatColor.YELLOW);
                    player.sendMessage(cc.create());
                    return true;
                }
                if (Reference.ServerItemMapGenerated.getCount() == 1) {
                    Reference.SCGmain.createServerItemMap();
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "ServerItemMap not generated");
                    cc.newLine();
                    cc.append("Generating for you", ChatColor.DARK_GREEN);
                    player.sendMessage(cc.create());
                }
                if (Reference.menu == null) {
                    Reference.SCGmain.createMenu();
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "Menu not created");
                    cc.newLine();
                    cc.append("Creating for you", ChatColor.DARK_GREEN);
                    player.sendMessage(cc.create());
                }
                if (Reference.listupdated) {
                    Reference.SCGmain.createServerItemMap();
                    Reference.SCGmain.createMenu();
                }

                    Reference.menu.open(player);
                    return true;

            } else {
                Chatcreator cc = new Chatcreator(ChatColor.RED, "This command is only for players");
                cc.newLine();
                cc.append("You are:" + sender.getName(), null);
                cc.newLine();
                cc.append("Type:" + sender.toString(), null);
                sender.sendMessage(cc.create());
                return true;
            }
        }
        return false;
    }


}
