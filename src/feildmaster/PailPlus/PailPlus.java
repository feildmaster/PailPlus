package feildmaster.PailPlus;

import feildmaster.PailPlus.Config.PermsConf;
import feildmaster.PailPlus.Listeners.JoinListener;
import feildmaster.PailPlus.Monitors.Util;
import feildmaster.PailPlus.Pail.MainWindow;
import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

public class PailPlus extends JavaPlugin {
    private MainWindow ai;
    private HashMap<String, PermissionAttachment> permissions = new HashMap<String, PermissionAttachment>();
    private PermsConf pConfig;

    public void onDisable() {
        System.out.println(getDescription().getName()+" disabled");
    }

    public void onEnable() {
        if (Util.getPail() == null) {
            Util.log().warning("["+getDescription().getName()+"] requires Pail.");
            return;
        }
        if (Util.apFound()) {
            me.escapeNT.pail.Util.Util.getInterfaceComponents().remove("Advanced");
            getServer().getPluginManager().disablePlugin(Util.pm().getPlugin("AdvancedPail"));
            Util.log().warning("["+getDescription().getName()+"] Please delete AdvancedPail.jar!");
        }
        ai = new MainWindow();

        pConfig = new PermsConf(new File(getDataFolder(), "permissions.yml"));

        // Hook into Pail!
        Util.getPail().loadInterfaceComponent("Advanced", ai);

        // Register Events
        JoinListener jl = new JoinListener();
        Util.pm().registerEvent(Type.PLAYER_JOIN, jl, Priority.Monitor, this);
        Util.pm().registerEvent(Type.PLAYER_QUIT, jl, Priority.Monitor, this);
        Util.pm().registerEvent(Type.PLAYER_KICK, jl, Priority.Monitor, this);

        // Preload Infos... :)
        Util.addPermissionNodes();
        Util.preloadUsers();

        // Schedule Tasks
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, ai.monitor, 10L, 20L); // CPU monitor
        getServer().getScheduler().scheduleSyncDelayedTask(this, ai.updatePlugins, 10L); // Checks "enabled" plugins

        Util.log().info(getDescription().getName()+" v"+getDescription().getVersion()+" enabled");
    }

    public void registerPlayer(Player player) {
        if(player == null) return;
        // Add permission attachment
        permissions.put(player.getName(), player.addAttachment(this));
        // Add player to perms dropdown menu
        ai.getPermissionPanel().addPlayer(player.getName());
        // Load player permissions
        Util.loadPermissions(player);
    }

    public void unregisterPlayer(Player player) {
        if(permissions.containsKey(player.getName())) {
            try {
                player.removeAttachment(permissions.get(player.getName()));
            }
            catch (IllegalArgumentException ex) {}
            permissions.remove(player.getName());
        }
    }

    public PermsConf getPermsConfig() {
        return pConfig;
    }

    public MainWindow getAI() {
        return ai;
    }

    public PermissionAttachment getAttachment(Player player) {
        return getAttachment(player.getName());
    }

    public PermissionAttachment getAttachment(String player) {
        return permissions.get(player);
    }
}
