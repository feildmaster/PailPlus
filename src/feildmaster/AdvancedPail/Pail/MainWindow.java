package feildmaster.AdvancedPail.Pail;

import feildmaster.AdvancedPail.Monitors.CPUMonitor;
import feildmaster.AdvancedPail.Monitors.Util;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class MainWindow extends JPanel {
    public MainWindow() {
        initComponents();
        memory.setToolTipText("Max: "+(CPUMonitor.memoryMax()/1024L/1024L)+"mb");
    }
    
    private void initComponents() {

        window = new JTabbedPane();
        plugins = new PluginsPanel();
        threads = new JLabel();
        memory = new JLabel();
        settings = new SettingsPanel();
        permission = new PermissionPanel();

        window.setTabPlacement(JTabbedPane.LEFT);
        window.setFocusable(false);
        
        plugins.setBorder(BorderFactory.createTitledBorder("Enable/Disable Plugins"));
        
        // Add window tabs
        window.addTab("Settings", settings);
        window.addTab("Plugins", plugins);
        window.addTab("Perms", permission);

        threads.setHorizontalAlignment(SwingConstants.CENTER);
        threads.setText(String.valueOf(CPUMonitor.threadsUsed()));
        threads.setVerticalAlignment(SwingConstants.BOTTOM);
        threads.setBorder(BorderFactory.createTitledBorder("Threads"));
        threads.setPreferredSize(new Dimension(40, 14));
        threads.setRequestFocusEnabled(false);
        threads.setVerifyInputWhenFocusTarget(false);

        memory.setHorizontalAlignment(SwingConstants.RIGHT);
        memory.setText((CPUMonitor.memoryUsed() / 1024L / 1024L) + "/" + (CPUMonitor.memoryTotal() / 1024L / 1024L)+"mb");
        memory.setVerticalAlignment(SwingConstants.BOTTOM);
        memory.setBorder(BorderFactory.createTitledBorder("Memory"));
        memory.setPreferredSize(new Dimension(40, 14));
        memory.setRequestFocusEnabled(false);
        memory.setVerifyInputWhenFocusTarget(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(memory, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 386, Short.MAX_VALUE)
                .addComponent(threads, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
            .addComponent(window, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(window, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(memory, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addComponent(threads, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
        );

        window.getAccessibleContext().setAccessibleName("");
    }
    
    private JLabel memory;
    private PluginsPanel plugins;
    private PermissionPanel permission;
    private JLabel threads;
    private JTabbedPane window;
    private JPanel settings;

    private void updatePlugins() {
        Map<String, Boolean> status = Util.updatePluginStatus();
        Map<String, JCheckBox> boxes = plugins.getBoxes();
        
        for(String s : boxes.keySet()) {
            boxes.get(s).setSelected(status.get(s));
        }
    }
    
    public PermissionPanel getPermissionPanel() {
        return permission;
    }
    public Runnable updatePlugins = new Runnable() {
        public void run() {
            updatePlugins();
        }
    };
    
    private void setMonitors() {
        memory.setText((CPUMonitor.memoryUsed() / 1024L / 1024L) + "/" + (CPUMonitor.memoryTotal() / 1024L / 1024L)+"mb");
        threads.setText(String.valueOf(CPUMonitor.threadsUsed()));
    }
    public Runnable monitor = new Runnable() {
        public void run() {
            setMonitors();
        }
    };
}