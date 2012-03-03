package com.feildmaster.pailplus.pail;

import com.feildmaster.pailplus.monitor.Util;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        init();
    }
    private void init() {
        add(new JLabel("Nothing to see here, move along..."));

        // Minimize to tray
        JCheckBox minimize = new JCheckBox();
        minimize.setText("Minimize To Tray");
        minimize.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                ((JCheckBox) evt.getItem()).isSelected();
            }
        });

        // On Close: Default, Minimize to Tray, Shut Down and Save
        JCheckBox close = new JCheckBox();
        close.setText("Action on Close");

        //add(minimize);

        // "General"
        //Util.getServer().getSpawnRadius();
        //Util.getServer().setSpawnRadius(0);

        // Consol Wordwrap -- Failed
        //Util.getPail().getMainWindow().getServerControls().getServerConsolePanel().getConsoleOutput().getScrollerPanel().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
