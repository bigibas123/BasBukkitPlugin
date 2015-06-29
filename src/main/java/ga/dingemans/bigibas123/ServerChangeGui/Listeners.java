package ga.dingemans.bigibas123.ServerChangeGui;


import ga.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Messaging.send(new String[]{"GetServers"}, event.getPlayer());
    }

}
