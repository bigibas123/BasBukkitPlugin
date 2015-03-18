package ga.dingemans.bigibas123.BasBukkitPlugin.util;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import org.bukkit.ChatColor;

public class Chatcreator {
    private String message;
    private String mainColour;

    public Chatcreator(ChatColor Maincolour, String message) {
        this.mainColour = String.valueOf(ChatColor.COLOR_CHAR) + Maincolour.getChar();
        this.message = ChatColor.COLOR_CHAR + String.valueOf(ChatColor.GREEN) + "[" + Reference.plugin.getName() + "]" + mainColour + message;
    }

    public void append(String msg, ChatColor... color) {
        String realcolor;
        if (color == null) {
            realcolor = mainColour;
        } else {
            realcolor = String.valueOf(ChatColor.COLOR_CHAR);
        }
        message = message + realcolor + msg;
    }

    public void newLine() {
        message = message + "\n";
    }

    public String create() {
        return message;
    }
}
