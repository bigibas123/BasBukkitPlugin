package ga.dingemans.bigibas123.BasBukkitPlugin.Reference;


import ga.dingemans.bigibas123.BasBukkitPlugin.Threads.MainBasPlugin;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Reference {
    public static final CountDownLatch ServerItemMapGenerated = new CountDownLatch(1);
    public static final CountDownLatch ServerListGenerated = new CountDownLatch(1);
    public static final Random rnd = new Random(System.currentTimeMillis());
    public static String[] serverList = null;
    public static Map<String, ItemStack> serverMap;
    public static IconMenu menu = null;
    public static JavaPlugin plugin;
    public static MainBasPlugin mainBasPlugin;
}
