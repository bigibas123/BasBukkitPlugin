package ga.dingemans.bigibas123.ServerChangeGui.util;


import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.ChatColor;

public class Chatcreator {
    private final String pluginprefix;
    private String message;
    @SuppressWarnings("CanBeFinal")
    private String mainColour;

    public Chatcreator(ChatColor Maincolour, String message) {
        this.pluginprefix = String.valueOf(ChatColor.COLOR_CHAR) + ChatColor.GREEN.getChar() + "[" + Reference.plugin.getName() + "]";
        this.mainColour = String.valueOf(ChatColor.COLOR_CHAR) + Maincolour.getChar();
        this.message = this.pluginprefix + this.mainColour + message;
    }

    @SuppressWarnings("SameParameterValue")
    public void append(String msg, ChatColor color) {
        String realcolor;
        if (color == null) {
            realcolor = mainColour;
        } else {
            realcolor = String.valueOf(ChatColor.COLOR_CHAR) + color.getChar();
        }
        message = message + realcolor + msg;
    }

    public void newLine() {
        message = message + "\n" + this.pluginprefix;
    }

    public String create() {
        return message;
    }
}
