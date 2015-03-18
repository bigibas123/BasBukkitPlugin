package ga.dingemans.bigibas123.BasBukkitPlugin.Reference.msgThreads;

import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.LogHelper;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.UEH;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class serverlist extends Thread {
    @Override
    public void run() {
        this.setPriority(MIN_PRIORITY);
        this.setName("Servermenu creator");
        this.setUncaughtExceptionHandler(new UEH());
        Reference.serverList = null;
        Messaging.send(new String[]{"GetServers"}, null);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        if (Reference.serverList == null) {
            this.run();
        } else {
            this.createmenu();
        }
    }


    public void createmenu() {
        LogHelper.INFO("got servers");
        int slots;
        slots = Reference.serverList.length;
        int rest = slots % 9;
        slots = slots - rest + 9;
        //slots need to be a multitude of 9 (above)
        IconMenu menu = new IconMenu("My Fancy Menu", slots, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                Messaging.send(new String[]{"Connect", event.getName()}, event.getPlayer());
                event.setWillClose(true);
            }
        }, Reference.plugin);
        int i = 0;
        Random rnd = new Random();
        for (String server : Reference.serverList) {
            ItemStack itms = new ItemStack(Material.STAINED_CLAY, 1);
            itms.setDurability((short) rnd.nextInt(16));
            menu.setOption(i, itms, server, "Connects you to the " + server + " server");
            i++;
        }
        Reference.menu = menu;
    }
}
