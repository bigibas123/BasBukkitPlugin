package ga.dingemans.bigibas123.BasBukkitPlugin.Reference.msgThreads;

import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.config.Config;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.UEH;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class serverlist extends Thread {
    public int waitingtime;
    @SuppressWarnings("FieldCanBeLocal")
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    @Override
    public void run() {
        do {
            this.setPriority(MIN_PRIORITY);
            this.setName("Servermenu creator");
            this.uncaughtExceptionHandler = new UEH();
            this.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
            Reference.serverList = null;
            Messaging.send(new String[]{"GetServers"}, null);
            if (Reference.serverList != null) {
                this.createmenu();
            } else {
                int waitingtime = 1000;
                do {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        this.uncaughtExceptionHandler.uncaughtException(this, e);
                    }
                    waitingtime--;
                } while (waitingtime >= 0);
            }
        } while (Reference.menu == null);
    }


    public void createmenu() {
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
            String item = Config.getItem(server);
            short dur = Config.getDurability(server);
            if (item == null) {
                item = Material.STAINED_CLAY.name();
                dur = (short) rnd.nextInt(16);
                Config.setItem(server, item);
                Config.setDurability(server, dur);
            }
            Material mat = Material.getMaterial(item);
            ItemStack itms = new ItemStack(mat, 1);
            itms.setDurability(dur);
            menu.setOption(i, itms, server, "Connects you to the " + server + " server");
            i++;
        }
        Reference.menu = menu;
        Reference.plugin.saveConfig();
    }
}
