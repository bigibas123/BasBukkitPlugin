package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.util.BungeeCord;
import com.github.bigibas123.ServerChangeGui.util.LogHelper;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public class ServerChangeGui extends JavaPlugin {


    @Getter
    public LogHelper log;
    @Getter
    public BungeeCord bungee;
    @Getter
    public Config configHelper;
    @Getter
    public Menu menu;

    @Override
    public void onLoad() {
        //ServerChangeGui.instance = this;
        log = new LogHelper(this);
        log.INFO("Loading: "+this.getName());
        bungee = new BungeeCord(this, this.getServer().getMessenger());
        configHelper = new Config(this);
        menu = new Menu(this.getConfigHelper(),this.getBungee(), log);
    }

    @Override
    public void onEnable() {
        log.INFO("Enabling: "+this.getName());
        this.getServer().getPluginManager().registerEvents(new LoginListener(this.bungee),this);
        this.registerCommand("SCG", new SCGCommand(this));
        this.menu.requestUpdate();
    }

    public <T extends CommandExecutor & TabCompleter> void registerCommand(String name, @NotNull T cmdC) {
        PluginCommand cmd = this.getCommand(name);
        if (cmd != null) {
            log.FINE("Registering command: "+cmd.getName()+" class:"+cmdC.toString());
            cmd.setExecutor(cmdC);
            cmd.setTabCompleter(cmdC);
        }else {
            log.WARNING("Command: "+name+" not found so not registering");
        }
    }

    @Override
    public void onDisable() {
        log.INFO("Disabling: "+this.getName());
        this.menu.save();
        this.configHelper.save();
    }

}
