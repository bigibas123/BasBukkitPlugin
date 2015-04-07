package ga.dingemans.bigibas123.ServerChangeGui;


import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import ga.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Messaging.send(new String[]{"GetServers"}, event.getPlayer());
        Reference.SCGmain.start();
    }

}
