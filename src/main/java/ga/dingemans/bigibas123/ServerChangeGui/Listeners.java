package ga.dingemans.bigibas123.ServerChangeGui;


import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import ga.dingemans.bigibas123.ServerChangeGui.config.Config;
import ga.dingemans.bigibas123.ServerChangeGui.util.Messaging;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Listeners implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        final PlayerLoginEvent e = event;
        Reference.plugin.getServer().getScheduler().runTaskLater(Reference.plugin, new Runnable() {
            @Override
            public void run() {
                Messaging.send(new String[]{"GetServers"}, e.getPlayer());
            }
        }, Config.getCallDelay());

    }

}
