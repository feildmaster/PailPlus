package feildmaster.AdvancedPail.Listeners;

import feildmaster.AdvancedPail.Monitors.Util;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener extends PlayerListener {
    public void onPlayerJoin(PlayerJoinEvent event) {
        Util.getAdvPail().registerPlayer(event.getPlayer());
    }
    public void onPlayerQuit(PlayerQuitEvent event) {
        Util.getAdvPail().unregisterPlayer(event.getPlayer());
    }
    public void onPlayerKick(PlayerKickEvent event) {
        Util.getAdvPail().unregisterPlayer(event.getPlayer());
    }
}
