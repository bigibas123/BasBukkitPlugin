package ga.dingemans.bigibas123.BasBukkitPlugin;


import ga.dingemans.bigibas123.BasBukkitPlugin.Reference.Reference;
import ga.dingemans.bigibas123.BasBukkitPlugin.util.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Messaging.send(new String[]{"GetServers"}, event.getPlayer());
        Reference.mainBasPlugin.start();
    }

}
