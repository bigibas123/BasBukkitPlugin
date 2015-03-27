package ga.dingemans.bigibas123.BasBukkitPlugin;

import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.Threads.MainBasPlugin;
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
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        Reference.plugin = this;

        Config.load();
        Reference.mainBasPlugin = new MainBasPlugin();
    }


    public void onLoad() {

    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        Messaging.receive(channel, player, message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("BBP")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (Reference.ServerListGenerated.getCount() == 1) {
                    Messaging.send(new String[]{"GetServers"}, player);
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "Servers not fetched");
                    cc.newLine();
                    cc.append("Fetching for you", ChatColor.DARK_GREEN);
                    player.sendMessage(cc.create());
                    return true;
                } else if (Reference.ServerItemMapGenerated.getCount() == 1) {
                    Reference.mainBasPlugin.createServerItemMap();
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "ServerItemMap not generated");
                    cc.newLine();
                    cc.append("Generating for you", ChatColor.DARK_GREEN);
                    player.sendMessage(cc.create());
                    return true;
                } else if (Reference.menu == null) {
                    Reference.mainBasPlugin.createMenu();
                    Chatcreator cc = new Chatcreator(ChatColor.RED, "Menu not created");
                    cc.newLine();
                    cc.append("Creating for you", ChatColor.DARK_GREEN);
                    player.sendMessage(cc.create());
                    return true;
                } else {
                    Reference.menu.open(player);
                    return true;
                }
            } else {
                Chatcreator cc = new Chatcreator(ChatColor.RED, "This command is only for players");
                cc.newLine();
                cc.append("You are:" + sender.getName(), null);
                cc.append("Type:" + sender.toString(), null);
                sender.sendMessage(cc.create());
                return true;
            }
        }
        return false;
    }
    }
