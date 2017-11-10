package com.mooo.dingemans.bigibas123.ServerChangeGui.Reference;


import com.mooo.dingemans.bigibas123.ServerChangeGui.util.serverObj;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;

public class Reference {
    public static final Random rnd = new Random(System.currentTimeMillis());
    public static JavaPlugin plugin;
    public static HashMap<String, serverObj> server = new HashMap<>();
}
