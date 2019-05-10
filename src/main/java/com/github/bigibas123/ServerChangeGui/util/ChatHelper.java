package com.github.bigibas123.ServerChangeGui.util;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import static com.github.bigibas123.ServerChangeGui.util.ChatHelper.level.*;

public class ChatHelper {

    private final CommandSender player;
    private StringBuilder msg;

    public ChatHelper(CommandSender player, level level) {
        this.player = player;
        this.msg = new StringBuilder();
        msg.append(level);
        prefix();
    }

    public static void quickNoPermission(CommandSender player, String permission) {
        new ChatHelper(player, SEVERE).append("You dont have permission:").append(HIGHLIGHT, permission).newLine(PS)
                .append(PS, "if you think this is in error please contact one of your server admins").send();
    }

    public ChatHelper append(String string) {
        msg.append(string);
        return this;
    }

    public ChatHelper append(level l, String str) {
        msg.append(quickAppend(l.toString(), str));
        return this;
    }

    public ChatHelper newLine(level l) {
        msg.append("\n");
        msg.append(l);
        prefix();
        return this;
    }

    private void prefix() {
        msg.append(quickAppend("[", Reference.getPlugin().getDescription().getPrefix(), "] "));
    }

    private String quickAppend(String... args) {
        StringBuilder out = new StringBuilder();
        for (String arg : args) {
            out.append(arg);
        }
        return out.toString();
    }

    public void send() {
        for (String msp : this.msg.toString().split("\n")) {
            this.player.sendMessage(msp);
        }
    }

    public enum level {
        SEVERE(ChatColor.RED),
        WARN(ChatColor.YELLOW),
        DEFAULT(ChatColor.DARK_GREEN),
        GOOD(ChatColor.GREEN),
        HIGHLIGHT(ChatColor.AQUA),
        BAD(ChatColor.DARK_RED),
        PS(ChatColor.DARK_GRAY);

        @Getter
        private final String color;

        level(ChatColor c) {
            this.color = ((Character) ChatColor.COLOR_CHAR).toString() + c.getChar();
        }


        @Override
        public String toString() {
            return this.color;
        }

        public String tS() {
            return this.color;
        }
    }
}
