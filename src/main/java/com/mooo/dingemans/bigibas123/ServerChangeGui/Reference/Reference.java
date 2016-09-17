package com.mooo.dingemans.bigibas123.ServerChangeGui.Reference;


import com.mooo.dingemans.bigibas123.ServerChangeGui.util.IconMenu;
import com.mooo.dingemans.bigibas123.ServerChangeGui.util.ServerInfo;
import com.mooo.dingemans.bigibas123.ServerChangeGui.SCGMain;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Reference {
    public static final Random rnd = new Random(System.currentTimeMillis());
    public static ArrayList<String> serverList = new ArrayList<>();
    public static Map<String, ServerInfo> serverMap = new HashMap<>();
    public static HashMap<Integer, Boolean> spots;
    public static IconMenu menu = null;
    public static JavaPlugin plugin;
    public static SCGMain SCGmain;
    public static CountDownLatch listupdated = new CountDownLatch(1);
    public static CountDownLatch ServerListGenerated = new CountDownLatch(1);
    public static IconMenu.OptionClickEventHandler OCEH;
    public static HashMap<Integer, ServerInfo> locationToServerInfo;
}
