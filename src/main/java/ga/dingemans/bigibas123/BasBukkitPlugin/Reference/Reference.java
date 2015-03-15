package ga.dingemans.bigibas123.BasBukkitPlugin.Reference;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.msgThreads.serverlist;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Reference {
    @SuppressWarnings("UnusedDeclaration")
    public static final UUID myUUID = UUID.fromString("eaf3965a-1f19-460c-b5dc-23c4ea09d955");
    public static String[] serverList = null;
    public static IconMenu menu = null;
    public static JavaPlugin plugin;
    public static serverlist serverlistthread;
}
