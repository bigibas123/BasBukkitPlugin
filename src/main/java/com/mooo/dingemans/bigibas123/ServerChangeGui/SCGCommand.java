package com.mooo.dingemans.bigibas123.ServerChangeGui;

import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.Chatcreator;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.MenuWrapper;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.serverObj;
import me.lucko.helper.messaging.bungee.BungeeMessaging;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class SCGCommand extends Command implements CommandExecutor, TabCompleter {


    @SuppressWarnings("WeakerAccess")
    protected SCGCommand() {
        super("SCG", "the main server change gui command", "/scg [reload|save]", Arrays.asList("SCG", "scg", "serverchangegui"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (Objects.equals(cmd.getName(), this.getName())) {
            Chatcreator cc = new Chatcreator(ChatColor.GREEN, "");
            if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("SCG.reload")) {
                    noPermission("SCG.reload", cc);
                } else {
                    Config.reload();
                    BungeeMessaging.getServers(sl -> {
                        for (String s : sl) {
                            Reference.server.put(s, new serverObj(s));
                        }
                    });
                    cc.append("Reloading config...", ChatColor.YELLOW);
                }
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("save")) {
                if (!sender.hasPermission("SCG.save")) {
                    noPermission("SCG.save", cc);
                } else {
                    Reference.server.values().forEach(serverObj::save);
                    if (Objects.equals(Config.getMenuName(), "Server Chooser")) {
                        Config.setMenuName("Server Chooser");
                    }
                    Config.save();
                    cc.append("Saved Config", ChatColor.GREEN);
                }
            } else if (sender instanceof Player) {
                Player player = (Player) sender;
                if (sender.hasPermission("SCG.use")) {
                    MenuWrapper men = new MenuWrapper(player);
                    men.getMenu().open();
                    return true;
                } else {
                    noPermission("SCG.use", cc);
                }
            } else {
                cc.append("This command is only for players", ChatColor.RED);
                cc.newLine();
                cc.append("You are:" + sender.getName(), ChatColor.RED);
                cc.newLine();
                cc.append("Type:" + sender.toString(), ChatColor.RED);
            }
            sender.sendMessage(cc.create());
            return true;
        } else {

            return false;
        }
    }

    private void noPermission(String perm, Chatcreator cc) {
        cc.append("You don't have permission", ChatColor.RED);
        cc.append(perm, ChatColor.BLUE);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Arrays.asList("reload", "save");
    }
}
