package com.github.bigibas123.ServerChangeGui.Reference;

import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import com.github.bigibas123.ServerChangeGui.util.Config;
import com.github.bigibas123.ServerChangeGui.util.ListManager;
import lombok.Getter;
import me.lucko.helper.messaging.bungee.BungeeCord;

public class Reference {
    @Getter
    public static String name;
    @Getter
    public static ServerChangeGui plugin;
    @Getter
    public static BungeeCord bungee;
    @Getter
    public static ListManager lm;
    public static Config config;
}
