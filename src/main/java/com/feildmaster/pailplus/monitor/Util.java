package com.feildmaster.pailplus.monitor;

import com.feildmaster.pailplus.PailPlus;
import com.feildmaster.pailplus.pail.MainWindow;
import com.feildmaster.pailplus.pail.PailTray;
import java.util.*;
import java.util.logging.Level;
import me.escapeNT.pail.Pail;
import org.bukkit.entity.Player;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.*;

public class Util {
    private static final Server server = Bukkit.getServer();
    private static final PailTray tray = new PailTray();
    private static final PluginManager pm = server.getPluginManager();
    private static Map<String, Boolean> pluginStatus = new HashMap<String, Boolean>();
    private static CommandSender sender;
    private static Pail pail;
    private static PailPlus pailPlus;

    public static Server getServer() {
        return server;
    }

    // Log Functions
    public static void info(String msg) {
        log(Level.INFO, msg);
    }

    public static void warning(String msg) {
        log(Level.WARNING, msg);
    }

    private static void log(Level level, String msg) {
        getPailPlus().getLogger().log(level, msg);
    }

    public static Pail getPail() {
        if (pail == null) {
            pail = (Pail) getServer().getPluginManager().getPlugin("Pail");
        }
        return pail;
    }

    public static PailPlus getPailPlus() {
        if (pailPlus == null) {
            pailPlus = (PailPlus) getServer().getPluginManager().getPlugin("PailPlus");
        }
        return pailPlus;
    }

    public static MainWindow getAI() {
        return getPailPlus().getMainWindow();
    }

    public static Player[] getPlayers() {
        return getServer().getOnlinePlayers();
    }

    public static Map<String, Boolean> getPluginStatus() {
        if (pluginStatus.isEmpty()) {
            updatePluginStatus();
        }
        return pluginStatus;
    }

    public static Map<String, Boolean> updatePluginStatus() {
        for (Plugin p : getServer().getPluginManager().getPlugins()) {
            pluginStatus.put(p.getDescription().getName(), p.isEnabled());
        }
        return pluginStatus;
    }

    public static List<World> getWorlds() {
        return getServer().getWorlds();
    }

//    public static void setPlayerPermission(String player, String world, String perm, Boolean value) {
//        getPailPlus().getPermsConfig().setPermission(player, world, perm, value); // Set config
//        Player p = getServer().getPlayer(player);
//        if(p != null)
//            loadPermissions(p);
////        else
////            getPermPanel().reloadPermissions();
//    }
//
//    public static void loadPermissions(Player player) {
//        if(player == null) return;
//
//        PermissionAttachment attachment = getPailPlus().getAttachment(player);
//        if(attachment==null) return;
//
//        for (String key : attachment.getPermissions().keySet())
//            attachment.unsetPermission(key);
//
//        for(Map.Entry<String, Object> entry : getPailPlus().getPermsConfig().getPlayerPermissions(player).entrySet())
//            if (entry.getValue() != null && entry.getValue() instanceof Boolean)
//                attachment.setPermission(entry.getKey(), (Boolean)entry.getValue());
//
//        player.recalculatePermissions();
////        getPermPanel().reloadPermissions();
//    }
//
//    public static void preloadUsers() {
//        for(String name : getPermsConfig().getRegisteredPlayers()) {
//            Player player = getServer().getPlayer(name);
//            if(player == null)
//                getPermPanel().addPlayer(name);
//            else
//                getPailPlus().registerPlayer(player);
//        }
//    }

//    public static void addPermissionNodes() {
//        //getAI().getPermissionPanel().getPannel().addBoxes(getPermsConfig().getRegisteredNodes());
//    }

    public static PailTray getTray() {
        return tray;
    }

    public static void toggleWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (window.isVisible()) {
            hideWindow();
        } else {
            showWindow();
        }
    }

    public static void hideWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (!window.isVisible()) return;
        window.setVisible(false);
    }

    public static void showWindow() {
        me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        if (window.isVisible() && !window.isActive()) {
            bringToFront();
        } else if (!window.isVisible()) {
            window.setVisible(true);
        }
    }

    public static void bringToFront() {
        final me.escapeNT.pail.GUIComponents.MainWindow window = getPail().getMainWindow();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.toFront();
                window.repaint();
            }
        });

    }

    public static void reload() {
        getServer().dispatchCommand(getSender(), "reload");
    }

    public static void stop() {
        Util.getServer().dispatchCommand(getSender(), "stop");
    }

    public static void saveAll() {
        getServer().dispatchCommand(getSender(), "save-all");
    }

    public static CommandSender getSender() {
        if (sender == null) {
            sender = getServer().getConsoleSender();
        }
        return sender;
    }
}
