package com.github.bigibas123.ServerChangeGui.util;


import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class LogHelper {

    private ServerChangeGui plugin;

    public LogHelper(ServerChangeGui plugin) {
        this.plugin = plugin;
    }

    private void log(Level logLevel, Object msg) {

        Bukkit.getLogger().log(logLevel, "[" + plugin.getName() + "] " + msg, plugin);

    }

    public void ALL(Object object) {
        log(Level.ALL, object);
    }

    public void FINEST(Object object) {
        log(Level.FINEST, object);
    }

    public void FINER(Object object) {
        log(Level.FINER, object);
    }

    public void FINE(Object object) {
        log(Level.FINE, object);
    }

    public void CONFIG(Object object) {
        log(Level.CONFIG, object);
    }

    public void INFO(Object object) {
        log(Level.INFO, object);
    }

    public void WARNING(Object object) {
        log(Level.WARNING, object);
    }

    public void SEVERE(Object object) {
        log(Level.SEVERE, object);
    }

}
