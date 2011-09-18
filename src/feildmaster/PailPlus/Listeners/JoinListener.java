package feildmaster.PailPlus.Listeners;

import feildmaster.PailPlus.Monitors.Util;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener extends PlayerListener {
    public void onPlayerJoin(PlayerJoinEvent event) {
        Util.getPailPlus().registerPlayer(event.getPlayer());
    }
    public void onPlayerQuit(PlayerQuitEvent event) {
        Util.getPailPlus().unregisterPlayer(event.getPlayer());
    }
    public void onPlayerKick(PlayerKickEvent event) {
        Util.getPailPlus().unregisterPlayer(event.getPlayer());
    }
}
