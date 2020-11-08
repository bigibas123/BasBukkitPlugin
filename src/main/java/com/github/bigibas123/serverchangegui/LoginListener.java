package com.github.bigibas123.serverchangegui;

import com.github.bigibas123.serverchangegui.util.BungeeCord;
import me.lucko.helper.Schedulers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginListener implements Listener {


    private final BungeeCord bungee;

    public LoginListener(BungeeCord bungee) {
        this.bungee = bungee;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Schedulers.async().runLater(() -> this.bungee.playerLogin(event.getPlayer()),20);
    }
}
