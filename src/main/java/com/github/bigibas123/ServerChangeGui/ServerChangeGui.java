package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.BungeeCord;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

import java.util.List;


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
        this.registerCommand(new SCGCommand(), ((List<String>) this.getDescription().getCommands().get("SCG").get("aliases")).toArray(new String[0]));
    }

    @Override
    public void disable() {
        Reference.menu.save();
        Reference.config.save();
    }

}
