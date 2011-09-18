package feildmaster.PailPlus.Pail;

import feildmaster.PailPlus.Monitors.Util;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JPanel;


public class PluginsPanel extends JPanel {
    private Map<String, JCheckBox> boxes = new HashMap<String, JCheckBox>();

    public PluginsPanel() {
        initComponents();
    }

    private void initComponents() {
        Map<String, Boolean> plugins = Util.getPluginStatus();
        setLayout(new GridLayout(Math.max(plugins.size()/4, 15), 4));

        List<String> titles = new ArrayList<String>(plugins.keySet());
        Collections.sort(titles, String.CASE_INSENSITIVE_ORDER);

        for(String s : titles) {
            if(s.equals("Pail") || s.equals("PailPlus")) continue;

            getBoxes().put(s, new JCheckBox(s));
            JCheckBox box = getBoxes().get(s);
            box.setSelected(true);
            box.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    togglePlugin((JCheckBox)evt.getItem());
                }
            });

            add(box);
        }
    }

    private void togglePlugin(JCheckBox box) {
        String name = box.getText();
        if(box.isSelected() && !Util.pm().isPluginEnabled(name))
            Util.pm().enablePlugin(Util.pm().getPlugin(name));
        else if (!box.isSelected() && Util.pm().isPluginEnabled(name))
            Util.pm().disablePlugin(Util.pm().getPlugin(name));
    }

    public Map<String, JCheckBox> getBoxes() {
        return boxes;
    }
}
