package com.feildmaster.pailplus;

import com.feildmaster.lib.configuration.EnhancedConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings extends EnhancedConfiguration {
    public Settings(Plugin plugin) {
        super(plugin);
    }

    public boolean getAlwaysShowTray() {
        return getBoolean("always_show_tray");
    }
    public void setAlwaysShowTray(boolean value) {
        set("always_show_tray", value);
    }

    public boolean getMinimizeToTray() {
        return getBoolean("minimize_to_tray");
    }
    public void setMinimizeToTray(boolean value) {
        set("minimize_to_tray", value);
    }

    public int getCloseAction() {
        return getInt("close_action");
    }
    public void setCloseAction(int value) {
        set("close_action", value);
    }
}
