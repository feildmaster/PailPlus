package feildmaster.PailPlus.Pail;

import feildmaster.PailPlus.Monitors.Util;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        init();
    }
    private void init() {
        add(new JLabel("Welcome"));

        // "General"
        //Util.getServer().getSpawnRadius();
        //Util.getServer().setSpawnRadius(0);
    }
}
