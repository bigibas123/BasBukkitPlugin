package ga.dingemans.bigibas123.BasBukkitPlugin.Threads;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.config.Config;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.IconMenu;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MainBasPlugin extends Thread {

    public void run() {
        this.setPriority(MIN_PRIORITY);//i don't care if it runs slowly
        this.setName(this.getClass().getName());
        try {
            Reference.ServerListGenerated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.isInterrupted();
        }
        this.createServerItemMap();
        try {
            Reference.ServerItemMapGenerated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.createMenu();
    }


    public void createServerItemMap() {
        String[] sl = Reference.serverList;
        Reference.serverMap = new HashMap<>();
        for (String server : sl) {
            String item = Config.getItem(server);
            short dur = Config.getDurability(server);
            if (item == null) {
                item = Material.STAINED_CLAY.name();
                dur = (short) Reference.rnd.nextInt(16);
            }
            Material mat = Material.getMaterial(item);
            ItemStack itemstack = new ItemStack(mat, 1, dur);
            Reference.serverMap.put(server, itemstack);
            Config.setItem(server, item);
            Config.setDurability(server, dur);
        }
        Reference.plugin.saveConfig();
        Reference.ServerItemMapGenerated.countDown();
    }

    public void createMenu() {
        int slots;
        slots = Reference.serverMap.size();
        int rest = slots % 9;
        slots = slots - rest + 9;
        //slots need to be a multitude of 9 (above)
        IconMenu menu = new IconMenu("Server selector", slots, new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.getPlayer().sendMessage("You have chosen " + event.getName());
                /*why not just Connect?
                Because Lagg and  internetWeirdness
                 and possible future ConnectOther support*/
                Messaging.send(new String[]{"ConnectOther", event.getPlayer().getName(), event.getName()}, event.getPlayer());
                event.setWillClose(true);
            }
        }, Reference.plugin);
        Map<String, ItemStack> serverMap = Reference.serverMap;
        int i = 0;
        for (Map.Entry<String, ItemStack> entry : serverMap.entrySet()) {
            menu.setOption(i, entry.getValue(), entry.getKey(), "Connects you to the " + entry.getKey() + " server");
            i++;
        }
        Reference.menu = menu;
    }

}