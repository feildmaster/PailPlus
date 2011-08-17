package feildmaster.AdvancedPail.Pail;

import feildmaster.AdvancedPail.Monitors.Util;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import org.bukkit.entity.Player;

public class PermPanel extends JPanel {
    private Map<String, JCheckBox> boxes = new HashMap<String, JCheckBox>();
    private boolean bdissabled = false;
    
    public PermPanel()   {
        init();
    }
    private void init() {
        setLayout(new GridLayout(0,1));
    }
    public void loadPlayerPerms(String name) {
        if(name.equals("Select a player")) {
            dissableBoxes();
            return;
        } else if(bdissabled)
            enableBoxes();
        
        Player player = Util.getServer().getPlayer(name);
        Map<String, Object> perms = Util.getPermsConfig().getPlayerPermissions(player);
        
        for(Map.Entry<String, JCheckBox> entry : boxes.entrySet()) {
            String node = entry.getKey();
            JCheckBox box = entry.getValue();
            if(player != null) { // Registered, online player. :D
                // Color box
                box.setForeground(player.hasPermission(node)?Color.green:(perms.containsKey(node)?Color.red:Color.black));
                // Mark/unmark box
                box.setSelected(perms.containsKey(node)?(Boolean)perms.get(node):false);
            } else { // Registered, offline player
                perms = Util.getPermsConfig().getPlayerPermissions(name); // Load the players perms
                // Color the box
                box.setForeground((perms.containsKey(node)&&((Boolean)perms.get(node)))?Color.green:(perms.containsKey(node)?Color.red:Color.black));
                // Mark/unmark box
                box.setSelected(perms.containsKey(node)?(Boolean)perms.get(node):false);
            }// else if(!box.getForeground().equals(Color.black))
               // box.setForeground(Color.black);
        }
    }
    
    protected void addNode(String node) {
        if(node.isEmpty()) return;
        if(!boxes.containsKey(node)) {
            JCheckBox box = new JCheckBox();
            box.setText(node);
            box.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent evt) {
                    JCheckBox box = (JCheckBox)evt.getItem();
                    if(box.isEnabled()) // Only trigger if it's enabled!!
                        Util.getAdvPail().getAI().getPermissionPanel().togglePlayerPerms(box.getText(), box.isSelected());
                }
            });
            boxes.put(node, box);
            add(box);
        }
    }
    
    public void addBoxes(List<String> nodes) {
        if(nodes.isEmpty())return;
        for(String node : nodes)
            if(!boxes.containsKey(node)) {
                addNode(node);
        }
        Util.getAdvPail().getAI().getPermissionPanel().reloadPermissions();
    }

    private void dissableBoxes() {
        for(JCheckBox box : boxes.values()) {
            box.setEnabled(false);
            box.setSelected(false);
            box.setForeground(Color.black);
        }
        bdissabled = true;
    }
    private void enableBoxes() {
        for(JCheckBox box : boxes.values())
            box.setEnabled(true);
        bdissabled = false;
    }
}
