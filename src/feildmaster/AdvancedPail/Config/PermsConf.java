package feildmaster.AdvancedPail.Config;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;
import feildmaster.AdvancedPail.Monitors.Util;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class PermsConf {
    private Configuration config;
    private List<String> registeredNodes;
    private Boolean updated = false;
    
    public PermsConf (File file) {
        config = new Configuration(file);
        config.load();
        if(!file.exists())
            setupPerms();
        else
            loadPerms();
        
    }
    
    private void loadPerms() {
        registeredNodes = config.getStringList("registered_nodes", null);
        Collections.sort(registeredNodes, String.CASE_INSENSITIVE_ORDER);
        config.getProperty("users");
    }
    
    private void setupPerms() {
        List<String> basic = Arrays.asList(new String[] {
            "craftbukkit.command.whitelist","craftbukkit.command.ban.player",
            "craftbukkit.command.unban.player","craftbukkit.command.op",
            "craftbukkit.command.time.set","craftbukkit.command.save.perform",
            "craftbukkit.command.tell","craftbukkit.command.say",
            "craftbukkit.command.give","craftbukkit.command.teleport",
            "craftbukkit.command.kick","craftbukkit.command.stop",
            "craftbukkit.command.list","craftbukkit.command.me",
            "craftbukkit.command.kill"
        });
        Collections.sort(basic, String.CASE_INSENSITIVE_ORDER);
        
        registeredNodes = basic;
        config.setProperty("registered_nodes", basic);
        config.setProperty("users", new HashMap<String, Object>());
        config.save();
    }
    
    // Node functions
    public List<String> getRegisteredNodes() {
        return registeredNodes;
    }
    public void addNode(String node) {
        if(registeredNodes.contains(node)) return;
        registeredNodes.add(node);
        Collections.sort(registeredNodes);
        setProperty("registered_nodes", registeredNodes);
    }
    public List<String> getRegisteredPlayers() {
        List<String> names = config.getNode("users").getKeys();
        Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }
    public Map<String, Object> getPlayerPermissions(Player player) {
        return player == null?null:getPlayerPermissions(player.getName());
    }
    public Map<String,Object> getPlayerPermissions(String player) {
        if(config.getProperty("users."+player) == null)
            registerPlayer(player);
        
        return config.getNode("users."+player+".permissions").getAll();
    }
    public List<String> getPlayerPermissionList(Player p) {
        if(p == null) return null;
        
        return config.getNode("users."+p.getName()+".permissions").getKeys();
    }
    
    
    
    public void registerPlayer(String player) {
        // More nodes? Who knows what's in store!
        setProperty("users."+player+".permissions", new HashMap<String, Object>());
        save(); // Save for good measure
    }
    public void setPermission(String player, String perm, Boolean value) {
        if(player.equals("Select a player")) return;
        
        Map<String, Object> perms = getPlayerPermissions(player);
        perms.put(perm, value);
        setProperty("users."+player+".permissions", perms);
    }
    
    private void setProperty(String node, Object value) {
        config.setProperty(node, value);
        
        // Toggle save button to "on"
        if(!updated) {
            updated = true;
            Util.getPermPanel().setSave(updated);
        }
    }
    
    public void save() {
        config.save();
        // Toggle save button to "off"
        if(updated) {
            updated = false;
            Util.getPermPanel().setSave(updated);
        }
            
    }
    
//    // Taken from PermissionsBukkit
//    public ConfigurationNode getNode(String child) {
//        return getNode("", child);
//    }
//    // Taken from PermissionsBukkit
//    private ConfigurationNode getNode(String parent, String child) {
//        ConfigurationNode parentNode = null;
//        if (child.contains(".")) {
//            int index = child.lastIndexOf('.');
//            parentNode = getNode("", child.substring(0, index));
//            child = child.substring(index + 1);
//        } else if (parent.length() == 0) {
//            parentNode = config;
//        } else if (parent.contains(".")) {
//            int index = parent.indexOf('.');
//            parentNode = getNode(parent.substring(0, index), parent.substring(index + 1));
//        } else {
//            parentNode = getNode("", parent);
//        }
//
//        if (parentNode == null) {
//            return null;
//        }
//
//        for (String entry : parentNode.getKeys()) {
//            if (child.equalsIgnoreCase(entry)) {
//                return parentNode.getNode(entry);
//            }
//        }
//        return null;
//    }
}
