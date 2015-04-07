package ga.dingemans.bigibas123.ServerChangeGui.util;


import ga.dingemans.bigibas123.ServerChangeGui.Reference.Reference;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@SuppressWarnings({"UnusedDeclaration", "WeakerAccess"})
public class LogHelper {

    private static void log(Level logLevel, Object msg) {

        Bukkit.getLogger().log(logLevel, "[" + Reference.plugin.getName() + "]" + msg, Reference.plugin);

    }

    public static void ALL(Object object) {
        log(Level.ALL, object);
    }

    public static void FINEST(Object object) {
        log(Level.FINEST, object);
    }

    public static void FINER(Object object) {
        log(Level.FINER, object);
    }

    public static void FINE(Object object) {
        log(Level.FINE, object);
    }

    public static void CONFIG(Object object) {
        log(Level.CONFIG, object);
    }

    public static void INFO(Object object) {
        log(Level.INFO, object);
    }

    public static void WARNING(Object object) {
        log(Level.WARNING, object);
    }

    public static void SERVE(Object object) {
        log(Level.SEVERE, object);
    }

}
