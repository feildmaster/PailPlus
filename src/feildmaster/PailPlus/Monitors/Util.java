package feildmaster.PailPlus.Monitors;

import feildmaster.PailPlus.PailPlus;
import feildmaster.PailPlus.Config.PermsConf;
import feildmaster.PailPlus.Pail.MainWindow;
import feildmaster.PailPlus.Pail.PermissionPanel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import me.escapeNT.pail.Pail;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class Util {
    private static final Server server = Bukkit.getServer();
    private static final PluginManager pm = server.getPluginManager();
    private static Map<String, Boolean> pluginStatus = new HashMap<String, Boolean>();
    private static Pail pail;
    private static PailPlus pailPlus;

    public static Boolean apFound() {
        return pm.getPlugin("AdvancedPail")!=null;
    }
    public static Server getServer() {
        return server;
    }
    public static PluginManager pm() {
        return pm;
    }
    public static Logger log() {
        return server.getLogger();
    }

    public static Pail getPail() {
        if(pail == null) pail = (Pail)pm.getPlugin("Pail");
        return pail;
    }
    public static PailPlus getPailPlus() {
        if(pailPlus == null) pailPlus = (PailPlus)pm.getPlugin("PailPlus");
        return pailPlus;
    }
    public static MainWindow getAI() {
        return getPailPlus().getAI();
    }
    public static PermsConf getPermsConfig() {
        return getPailPlus().getPermsConfig();
    }
    public static PermissionPanel getPermPanel() {
        return getPailPlus().getAI().getPermissionPanel();
    }

    public static Player[] getPlayers() {
        return server.getOnlinePlayers();
    }

    public static Plugin[] getPlugins() {
        return pm.getPlugins();
    }

    public static Map<String, Boolean> getPluginStatus() {
        if(pluginStatus.isEmpty()) updatePluginStatus();
        return pluginStatus;
    }
    public static Map<String, Boolean> updatePluginStatus() {
        for(Plugin p : getPlugins())
            pluginStatus.put(p.getDescription().getName(), p.isEnabled());
        return pluginStatus;
    }

    public static List<World> getWorlds() {
        return getServer().getWorlds();
    }

    public static void setPlayerPermission(String player, String perm, Boolean value) {
        getPailPlus().getPermsConfig().setPermission(player, perm, value); // Set config
        Player p = getServer().getPlayer(player);
        if(p != null)
            loadPermissions(p);
        else
            getPermPanel().reloadPermissions();
    }

    public static void loadPermissions(Player player) {
        if(player == null) return;

        PermissionAttachment attachment = getPailPlus().getAttachment(player);
        if(attachment==null) return;

        for (String key : attachment.getPermissions().keySet())
            attachment.unsetPermission(key);

        for(Map.Entry<String, Object> entry : getPailPlus().getPermsConfig().getPlayerPermissions(player).entrySet())
            if (entry.getValue() != null && entry.getValue() instanceof Boolean)
                attachment.setPermission(entry.getKey(), (Boolean)entry.getValue());

        player.recalculatePermissions();
        getPermPanel().reloadPermissions();
    }

    public static Configuration getConfig() {
        return getPailPlus().getConfiguration();
    }

    public static void preloadUsers() {
        for(String name : getPermsConfig().getRegisteredPlayers()) {
            getPailPlus().registerPlayer(getServer().getPlayer(name));
            getPermPanel().addPlayer(name);
        }
    }

    public static void addPermissionNodes() {
        getAI().getPermissionPanel().getPannel().addBoxes(getPermsConfig().getRegisteredNodes());
    }
}
