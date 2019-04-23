package com.github.bigibas123.ServerChangeGui;

import com.github.bigibas123.ServerChangeGui.Reference.Reference;
import com.github.bigibas123.ServerChangeGui.util.Config;
import me.lucko.helper.Services;
import me.lucko.helper.messaging.bungee.BungeeCord;
import me.lucko.helper.messaging.bungee.BungeeCordImpl;
import me.lucko.helper.plugin.ExtendedJavaPlugin;

import java.util.List;


public class ServerChangeGui extends ExtendedJavaPlugin {


    @Override
    public void load() {
        Reference.name = this.getDescription().getName();
        Reference.plugin = this;
        Reference.bungee = Services.get(BungeeCord.class).orElse(new BungeeCordImpl(this));
    }

    @Override
    public void enable() {
        Reference.config = new Config();
        Reference.bungee.getServers().thenAcceptAsync(c -> Reference.lm.update(c));
        this.registerCommand(new SCGCommand(), ((List<String>) this.getDescription().getCommands().get("SCG").get("aliases")).toArray(new String[0]));
    }

    @Override
    public void disable() {

    }

}
