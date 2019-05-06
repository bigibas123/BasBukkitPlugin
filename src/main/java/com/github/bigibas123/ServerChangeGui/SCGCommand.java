package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.ChatHelper;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.github.bigibas123.ServerChangeGui.util.ChatHelper.level.*;

public class SCGCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if ("reload".equalsIgnoreCase(args[0])) {
                Reference.config.reload();
                return true;
            } else if ("save".equalsIgnoreCase(args[0])) {
                Reference.menu.save();
                Reference.config.save();
                return true;
            } else if ("setitem".equalsIgnoreCase(args[0])) {
                if (sender instanceof Player) {
                    if (args.length > 1) {
                        ItemStack stack = ItemStackBuilder.of(((Player) sender).getInventory().getItemInMainHand().clone())
                                .transformMeta(itemMeta ->
                                        itemMeta.setDisplayName(
                                                ChatColor.COLOR_CHAR + String.valueOf(ChatColor.RESET.getChar()) +
                                                        itemMeta.getDisplayName().replace('&', ChatColor.COLOR_CHAR))).build();
                        new ChatHelper(sender, ChatHelper.level.GOOD)
                                .append("Server item of: ").append(DEFAULT, args[1])
                                .append(GOOD, " set to:").append(DEFAULT, stack.getType().name()).send();
                        Reference.menu.setItem(args[1], stack);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                            .append("You are:").append(sender.getClass().getSimpleName()).send();
                }
            } else {
                return false;
            }
            return false;
        } else {
            if (sender instanceof Player) {
                Reference.menu.open((Player) sender);
            } else {
                new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                        .append("You are:").append(sender.getClass().getSimpleName()).send();
            }
            return true;
        }
    }
}
