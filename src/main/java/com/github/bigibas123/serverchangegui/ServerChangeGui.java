package com.github.bigibas123.serverchangegui;

import com.github.bigibas123.serverchangegui.menu.MenuManager;
import com.github.bigibas123.serverchangegui.util.BungeeCord;
import com.github.bigibas123.serverchangegui.util.LogHelper;
import lombok.Getter;
import lombok.NonNull;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;


public class ServerChangeGui extends ExtendedJavaPlugin {


    @Getter
    public LogHelper log;
    @Getter
    public BungeeCord bungee;
    @Getter
    public Config configHelper;
    @Getter
    public IMenu menu;

    @Override
    protected void load() {
        //ServerChangeGui.instance = this;
        log = new LogHelper(this);
        log.INFO("Loading: "+this.getName());
        bungee = new BungeeCord(this, this.getServer().getMessenger());
        configHelper = new Config(this);
        menu = new MenuManager(this.getConfigHelper(),this.getBungee(), log);
    }

    @Override
    protected void enable() {
        log.INFO("Enabling: "+this.getName());
        this.getServer().getPluginManager().registerEvents(new LoginListener(this.bungee),this);
        this.registerCommand("SCG", new SCGCommand(this));
        this.menu.requestUpdate();
    }

    public <T extends CommandExecutor & TabCompleter> void registerCommand(String name, @NonNull T cmdC) {
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
    protected void disable() {
        log.INFO("Disabling: "+this.getName());
        this.menu.save();
        this.configHelper.save();
    }

}
