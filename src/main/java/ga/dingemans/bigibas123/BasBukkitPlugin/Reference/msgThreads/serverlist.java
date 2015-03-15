package ga.dingemans.bigibas123.BasBukkitPlugin.Reference.msgThreads;

import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class serverlist extends Thread {
    @Override
    public void run() {
        Messaging.send(new String[]{"GetServers"}, null);
        int i = 0;
        do {

            try {
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        } while (!(Reference.serverList == null) || !(i >= 10));
        if (Reference.serverList == null) {
            this.run();
        } else {
            int slots;
            slots = Reference.serverList.length;
            int rest = slots % 9;
            slots = slots - rest + 1;
            //slots need to be a multitude of 9
            Reference.menu = new IconMenu("My Fancy Menu", slots, new IconMenu.OptionClickEventHandler() {
                @Override
                public void onOptionClick(IconMenu.OptionClickEvent event) {
                    event.getPlayer().sendMessage("You have chosen " + event.getName());
                    event.setWillClose(true);
                }
            }, Reference.plugin);
            i = 0;
            Random rnd = new Random();
            for (String server : Reference.serverList) {
                ItemStack itms = new ItemStack(Material.STAINED_GLASS_PANE, 1);
                itms.setDurability((short) rnd.nextInt(16));
                Reference.menu.setOption(i, itms, server, "Connects you to the " + server + " server");
                i++;
            }

        }
    }
}
