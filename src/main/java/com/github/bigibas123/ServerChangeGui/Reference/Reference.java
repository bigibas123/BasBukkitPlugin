package com.github.bigibas123.ServerChangeGui.Reference;

import com.github.bigibas123.ServerChangeGui.Config;
import com.github.bigibas123.ServerChangeGui.Menu;
import com.github.bigibas123.ServerChangeGui.ServerChangeGui;
import com.github.bigibas123.ServerChangeGui.util.BungeeCord;
import lombok.Getter;

public class Reference {
    @Getter
    public static String name;
    @Getter
    public static ServerChangeGui plugin;
    @Getter
    public static BungeeCord bungee;
    @Getter
    public static Config config;
    @Getter
    public static Menu menu;
}
