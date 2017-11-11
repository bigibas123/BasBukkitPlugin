package com.mooo.dingemans.bigibas123.ServerChangeGui;

import com.mooo.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import com.mooo.dingemans.bigibas123.ServerChangeGui.config.Config;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.serverObj;
import me.lucko.helper.messaging.bungee.BungeeMessaging;
import me.lucko.helper.plugin.ExtendedJavaPlugin;


/*@Plugin(
        name = "ServerChangeGui",
        version = "2.0",
        description = "A plugin that uses bungeecord to create an inventory serverchooser",
        load = PluginLoadOrder.STARTUP,
        authors = {"bigibas123"},
        depends = {@PluginDependency(value = "helper", soft = true)}
)*/
public class ServerChangeGui extends ExtendedJavaPlugin {


    @Override
    public void enable() {
        Reference.plugin = this;
        BungeeMessaging.getServers(sl -> {
            for (String s : sl) {
                Reference.server.put(s, new serverObj(s));
            }
        });
        this.registerCommand(new SCGCommand(), "SCG", "scg", "serverchangegui");
    }


    public void load() {

    }

    @Override
    public void disable() {
        //this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        //this.getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", this);
        Config.save();
    }


}
