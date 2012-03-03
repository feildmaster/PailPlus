package com.feildmaster.pailplus.pail;

import com.feildmaster.pailplus.monitor.Util;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainWindow extends JPanel {
    public MainWindow() {
        initComponents();
    }

    private void initComponents() {
        window = new JTabbedPane(); // The window with everything in it.
        //plugins = new PluginsPanel();
        monitorPanel = new MonitorPanel();
        settings = new SettingsPanel();
        //permission = new PermissionPanel();

        window.setTabPlacement(JTabbedPane.LEFT);
        window.setFocusable(false);

        //plugins.setBorder(BorderFactory.createTitledBorder("Enable/Disable Plugins"));

        // Add window tabs
        window.addTab("Settings", settings);
        //window.addTab("Plugins", new JScrollPane().add(plugins));
        //window.addTab("Perms", permission);

        // Memory Panel
        Util.getPail().getMainWindow().getContentPane().add(monitorPanel, BorderLayout.PAGE_END);

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addComponent(window, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(window, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        //window.getAccessibleContext().setAccessibleName("");
    }

    private MonitorPanel monitorPanel;
    //private PluginsPanel plugins;
    //private PermissionPanel permission;
    private JTabbedPane window;
    private JPanel settings;
//
//    private void updatePlugins() {
//        Map<String, Boolean> status = Util.updatePluginStatus();
//        Map<String, JCheckBox> boxes = plugins.getBoxes();
//
//        for(String s : boxes.keySet())
//            if(status.get(s)!=boxes.get(s).isSelected())
//                boxes.get(s).setSelected(status.get(s));
//    }
//
//    public PermissionPanel getPermissionPanel() {
//        return permission;
//    }
//    public Runnable updatePlugins = new Runnable() {
//        public void run() {
//            updatePlugins();
//        }
//    };

    public Runnable monitor = new Runnable() {
        public void run() {
            monitorPanel.setMonitors();
        }
    };
}