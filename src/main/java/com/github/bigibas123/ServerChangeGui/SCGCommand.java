package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.ChatHelper;
import com.github.bigibas123.ServerChangeGui.util.PermissionUtils;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.github.bigibas123.ServerChangeGui.util.ChatHelper.level.*;

public class SCGCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean success = false;
        PermissionUtils.hasPermission(sender, "SCG.use");
        if (args.length > 0) {
            if ("reload".equalsIgnoreCase(args[0])) {
                if (PermissionUtils.hasPermission(sender, "SCG.reload")) {
                    Reference.config.reload();
                    Reference.menu.reload();
                } else {
                    ChatHelper.quickNoPermission(sender, "SCG.reload");
                }
                success = true;
            } else if ("save".equalsIgnoreCase(args[0])) {
                if (PermissionUtils.hasPermission(sender, "SCG.save")) {
                    Reference.menu.save();
                    Reference.config.save();
                } else {
                    ChatHelper.quickNoPermission(sender, "SCG.save");
                }
                success = true;
            } else if ("setitem".equalsIgnoreCase(args[0])) {
                if (PermissionUtils.hasPermission(sender, "SCG.setitem")) {
                    if (sender instanceof Player) {
                        if (args.length > 1) {
                            if (PermissionUtils.hasPermission(sender, "SCG.setitem." + args[1])) {

                                ItemStack stack = ItemStackBuilder.of(((Player) sender).getInventory().getItemInMainHand().clone())
                                        .transformMeta(itemMeta ->
                                                itemMeta.setDisplayName(
                                                        ChatColor.COLOR_CHAR + String.valueOf(ChatColor.RESET.getChar()) +
                                                                itemMeta.getDisplayName().replaceAll("(?<!\\\\)&", String.valueOf(ChatColor.COLOR_CHAR))
                                                                        .replaceAll("\\\\&", "&")
                                                )
                                        ).build();
                                new ChatHelper(sender, ChatHelper.level.GOOD)
                                        .append("Server item of: ").append(DEFAULT, args[1])
                                        .append(GOOD, " set to:").append(DEFAULT, stack.getType().name()).send();
                                return Reference.menu.setItem(args[1], stack);
                            } else {
                                ChatHelper.quickNoPermission(sender, "SCG.setitem." + args[1]);
                            }
                        }
                    } else {
                        new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                                .append("You are:").append(sender.getClass().getSimpleName()).send();
                    }
                } else {
                    ChatHelper.quickNoPermission(sender, "SCG.setitem");
                }
                success = true;
            } else if ("setslot".equalsIgnoreCase(args[0])) {
                if (PermissionUtils.hasPermission(sender, "SCG.setslot")) {
                    if (args.length > 2) {
                        if (PermissionUtils.hasPermission(sender, "SCG.setslot." + args[1])) {
                            Reference.getMenu().setSlot(args[1], Integer.valueOf(args[2]));
                            new ChatHelper(sender, GOOD).append("Set slot of:").append(HIGHLIGHT, args[1])
                                    .append(GOOD, " to ").append(HIGHLIGHT, Integer.valueOf(args[2]).toString()).send();
                        } else {
                            ChatHelper.quickNoPermission(sender, "SCG.setslot." + args[1]);
                        }
                    }
                } else {
                    ChatHelper.quickNoPermission(sender, "SCG.setslot");
                }
                success = true;
            }
        } else {
            if (sender instanceof Player) {
                Reference.menu.open((Player) sender);
            } else {
                new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                        .append("You are:").append(sender.getClass().getSimpleName()).send();
            }
            success = true;
        }
        return success;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            if ("reload".startsWith(args[0]) && PermissionUtils.hasPermission(sender, "SCG.reload")) {
                suggestions.add("reload");
            }
            if ("save".startsWith(args[0]) && PermissionUtils.hasPermission(sender, "SCG.save")) {
                suggestions.add("save");
            }
            if ("setitem".startsWith(args[0]) && PermissionUtils.hasPermission(sender, "SCG.setitem")) {
                suggestions.add("setitem");
            }
            if ("setslot".startsWith(args[0]) && PermissionUtils.hasPermission(sender, "SCG.setslot")) {
                suggestions.add("setslot");
            }
        } else if (args.length == 2) {
            if (("setitem".equalsIgnoreCase(args[0]) && PermissionUtils.hasPermission(sender, "SCG.setitem")) || ("setslot".equalsIgnoreCase(args[0]) && PermissionUtils.hasPermission(sender, "SCG.setslot"))) {
                for (String server : Reference.menu.getServers()) {
                    if (server.startsWith(args[1])) {
                        suggestions.add(server);
                    }
                }
            }
        }
        return suggestions;
    }
}
