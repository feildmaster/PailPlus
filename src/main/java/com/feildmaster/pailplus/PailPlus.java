package com.feildmaster.pailplus;

import com.feildmaster.lib.configuration.PluginWrapper;
import com.feildmaster.pailplus.monitor.Util;
import com.feildmaster.pailplus.pail.MainWindow;
import javax.swing.JFrame;

public class PailPlus extends PluginWrapper {
    private MainWindow ai;
    private Settings config;

    public void onDisable() {
        //Util.save();
        if (!getConfig().checkDefaults()) {
            getConfig().saveDefaults();
        }
        Util.getTray().removeIcon();
    }

    // Order means everything.
    public void onEnable() {
        if (Util.getPail() == null) {
            Util.warning("Requires Pail.");
            return;
        }

        // Hook into Pail!
        Util.getPail().loadInterfaceComponent("Advanced", ai = new MainWindow());
        Util.getTray().addIcon();
        if (Util.getTray().isActive()) {
            Util.getPail().getMainWindow().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Util.getPail().getMainWindow().removeWindowListener(Util.getPail().getWindowListener());
        }

        if (!getConfig().checkDefaults()) {
            getConfig().saveDefaults();
        }

        // Register Events
        //new JoinListener(this);

        // Preload Info
        //Util.addPermissionNodes();
        //Util.preloadUsers();

        // Schedule Tasks
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, ai.monitor, 10L, 20L); // CPU monitor
    }

    public Settings getConfig() {
        if (config == null) {
            config = new Settings(this);
        }
        return config;
    }

    public MainWindow getMainWindow() {
        return ai;
    }
}
