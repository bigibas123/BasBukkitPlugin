package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.BungeeCord;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;


public class ServerChangeGui extends ExtendedJavaPlugin {


    @Override
    public void load() {
        Reference.name = this.getDescription().getName();
        Reference.plugin = this;
        Reference.bungee = new BungeeCord(this, this.getServer().getMessenger());
        Reference.config = new Config();
        Reference.menu = new Menu();
    }

    @Override
    public void enable() {
        this.registerCommand("SCG", new SCGCommand());
        Reference.menu.requestUpdate();

    }

    public <T extends CommandExecutor & TabCompleter> void registerCommand(String name, T cmdc) {
        PluginCommand cmd = this.getCommand(name);
        SCGCommand cmdC = new SCGCommand();
        cmd.setExecutor(cmdC);
        cmd.setTabCompleter(cmdC);
    }

    @Override
    public void disable() {
        Reference.menu.save();
        Reference.config.save();
    }

}
