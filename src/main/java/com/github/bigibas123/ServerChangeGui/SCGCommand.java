package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.util.ChatHelper;
import com.github.bigibas123.ServerChangeGui.util.Util;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.bigibas123.ServerChangeGui.util.ChatHelper.level.*;

public class SCGCommand implements CommandExecutor, TabCompleter {

    private final ServerChangeGui plugin;

    public SCGCommand(ServerChangeGui plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean success;
        Util.hasPermission(sender, "SCG.use");
        if (args.length > 0) {
            if ("reload".equalsIgnoreCase(args[0])) {
                success = reload(sender);
            } else if ("save".equalsIgnoreCase(args[0])) {
                success = save(sender);
            } else if ("setItem".equalsIgnoreCase(args[0])) {
                success = setItem(sender, args);
            } else if ("setSlot".equalsIgnoreCase(args[0])) {
                success = setslot(sender, args);
            } else {
                new ChatHelper(sender, SEVERE).append("Invalid subcommand").send();
                success = false;
            }
        } else {
            if (sender instanceof Player) {
                plugin.getMenu().open((Player) sender);
                success = true;
            } else {
                new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                        .append("You are: ").append(HIGHLIGHT, sender.getClass().getSimpleName()).send();
                success = false;
            }
        }
        return success;
    }

    private boolean reload(CommandSender sender) {
        boolean success;
        if (Util.hasPermission(sender, "SCG.reload")) {
            plugin.getMenu().reload();
            plugin.getMenu().reload();
        } else {
            ChatHelper.quickNoPermission(sender, "SCG.reload");
        }
        success = true;
        return success;
    }

    private boolean save(CommandSender sender) {
        boolean success;
        if (Util.hasPermission(sender, "SCG.save")) {
            plugin.getMenu().save();
            plugin.getConfigHelper().save();
        } else {
            ChatHelper.quickNoPermission(sender, "SCG.save");
        }
        success = true;
        return success;
    }

    private boolean setItem(CommandSender sender, String[] args) {
        boolean success;
        if (Util.hasPermission(sender, "SCG.setitem")) {
            if (sender instanceof Player) {
                if (args.length > 1) {
                    if (Util.hasPermission(sender, "SCG.setitem." + args[1])) {
                        @NotNull ItemStack original = ((Player) sender).getInventory().getItemInMainHand().clone();
                        ItemStack stack = ItemStackBuilder.of(original)
                                .transformMeta(itemMeta -> {
                                    if(itemMeta.hasDisplayName()){
                                        String name = itemMeta.getDisplayName();
                                        name = Util.replaceColorCodes(name);
                                        itemMeta.setDisplayName(name);
                                    }
                                    List<String> lore = itemMeta.getLore();
                                    if (lore != null) {
                                        lore.replaceAll(Util::replaceColorCodes);
                                        itemMeta.setLore(lore);
                                    }
                                    itemMeta.addItemFlags(original.getItemMeta() != null ? original.getItemMeta().getItemFlags().toArray(new ItemFlag[0]) : new ItemFlag[0]);
                                }).build();
                        success = plugin.getMenu().setItem(args[1], stack);
                        if (success) {
                            new ChatHelper(sender, GOOD)
                                    .append("Server item of: ").append(HIGHLIGHT, args[1])
                                    .append(GOOD, " set to: ").append(HIGHLIGHT, stack.getType().name()).send();
                        } else {
                            new ChatHelper(sender, BAD).append("Server: ").append(HIGHLIGHT, args[1]).append(BAD, " not found").newLine(PS)
                                    .append("Please use one of: ").append(Arrays.toString(plugin.getMenu().getServers().toArray())).send();
                        }
                    } else {
                        ChatHelper.quickNoPermission(sender, "SCG.setitem." + args[1]);
                        success = true;
                    }
                } else {
                    success = false;
                }
            } else {
                new ChatHelper(sender, SEVERE).append("This command requires arguments or to be run as a player").newLine(PS)
                        .append("You are:").append(sender.getClass().getSimpleName()).send();
                success = true;
            }
        } else {
            ChatHelper.quickNoPermission(sender, "SCG.setitem");
            success = true;
        }
        return success;
    }

    private boolean setslot(CommandSender sender, String[] args) {
        boolean success;
        //FIXME doesn't work properly yet
        // Slots aren't overwritten if they're already taken
        if (Util.hasPermission(sender, "SCG.setslot")) {
            if (args.length > 2) {
                if (Util.hasPermission(sender, "SCG.setslot." + args[1])) {
                    int slot = Integer.parseInt(args[2]);
                    if (plugin.getMenu().isSlotFree(slot - 1)) {
                        success = plugin.getMenu().setSlot(args[1], slot - 1);
                        if (success) {
                            new ChatHelper(sender, GOOD).append("Set slot of: ").append(HIGHLIGHT, args[1])
                                    .append(GOOD, " to ").append(HIGHLIGHT, Integer.valueOf(slot).toString()).send();
                        } else {
                            new ChatHelper(sender, BAD).append("Server: ").append(HIGHLIGHT, args[1]).append(BAD, " not found").newLine(PS)
                                    .append("Please use one of: ").append(Arrays.toString(plugin.getMenu().getServers().toArray())).send();
                        }
                    } else {
                        new ChatHelper(sender, BAD).append("Slot: ").append(HIGHLIGHT, Integer.toString(slot)).append(BAD, " already taken").newLine(PS)
                                .append("Please choose one that is not: ").append(Arrays.toString(plugin.getMenu().getTakenSlots().toArray()));
                        success = true;
                    }
                } else {
                    ChatHelper.quickNoPermission(sender, "SCG.setslot." + args[1]);
                    success = true;
                }
            } else {
                new ChatHelper(sender, BAD).append("Not enough arguments").newLine(PS)
                        .append("/scg setslot <server> <slot>").send();
                success = false;
            }
        } else {
            ChatHelper.quickNoPermission(sender, "SCG.setslot");
            success = true;
        }
        return success;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            if ("reload".startsWith(args[0]) && Util.hasPermission(sender, "SCG.reload")) {
                suggestions.add("reload");
            }
            if ("save".startsWith(args[0]) && Util.hasPermission(sender, "SCG.save")) {
                suggestions.add("save");
            }
            if ("setItem".startsWith(args[0]) && Util.hasPermission(sender, "SCG.setitem")) {
                suggestions.add("setItem");
            }
            if ("setslot".startsWith(args[0]) && Util.hasPermission(sender, "SCG.setslot")) {
                suggestions.add("setslot");
            }
        } else if (args.length == 2) {
            if (("setItem".equalsIgnoreCase(args[0]) && Util.hasPermission(sender, "SCG.setitem")) || ("setslot".equalsIgnoreCase(args[0]) && Util.hasPermission(sender, "SCG.setslot"))) {
                for (String server : plugin.getMenu().getServers()) {
                    if (server.startsWith(args[1])) {
                        suggestions.add(server);
                    }
                }
            }
        }
        return suggestions;
    }
}
