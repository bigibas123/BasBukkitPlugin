package ga.dingemans.bigibas123.BasBukkitPlugin.util;

import org.bukkit.ChatColor;

public class UEH implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Chatcreator cc = new Chatcreator(ChatColor.RED, "Thread errored:");
        cc.append(t.getName());
        cc.newLine();
        for (StackTraceElement st : e.getStackTrace()) {
            cc.append(st.getMethodName() + " at " + st.getClassName() + " line:" + st.getLineNumber());
            cc.newLine();
        }
        LogHelper.WARNING(cc.create());
    }
}
