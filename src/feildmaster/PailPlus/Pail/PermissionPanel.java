package feildmaster.PailPlus.Pail;

import feildmaster.PailPlus.Monitors.Util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class PermissionPanel extends JPanel {
    List<String> players = new ArrayList<String>();
    Boolean updating = false;

    public PermissionPanel() {
        init();
    }

    private void init() {
        permScroll = new JScrollPane();
        saveNode = new JButton();
        saveConf = new JButton();
        inputNode = new JTextField();
        playerSelect = new JComboBox();
        panel = new PermPanel();

        saveConf.setText("Save");
        saveConf.setEnabled(false);
        saveConf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Util.getPermsConfig().save();
            }
        });

        saveNode.setText("Add Node");
        saveNode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPermission(inputNode.getText());
            }
        });

        permScroll.setViewportView(panel);

        playerSelect.setModel(new DefaultComboBoxModel());
        playerSelect.addItem("Select a player");
        playerSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reloadPermissions();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerSelect, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 10, 10)
                .addComponent(saveConf)
                .addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(inputNode, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 5, 5)
                .addComponent(saveNode)
                .addContainerGap())
            .addComponent(permScroll, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(saveConf)
                    .addComponent(saveNode)
                    .addComponent(inputNode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerSelect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(permScroll, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
        );
    }

    public void addPlayer(String name){
        if(!players.contains(name)) {
            playerSelect.addItem(name);
            players.add(name);
        }
    }

    public void setSave(Boolean value) {
        saveConf.setEnabled(value);
    }

    public void addPermission(String node) {
        if(node.isEmpty()) return;

        // Add to config
        Util.getPermsConfig().addNode(node);
        // Add to panel
        panel.addNode(node);

        inputNode.setText("");

        // Reload permissions
        Util.getPailPlus().getAI().getPermissionPanel().reloadPermissions();
    }

    public PermPanel getPannel() {
        return panel;
    }


    private JButton saveConf;
    private JButton saveNode;
    private JTextField inputNode;
    private JComboBox playerSelect;
    private JScrollPane permScroll;
    private PermPanel panel;

    public void reloadPermissions() {
        updating = true;
        panel.loadPlayerPerms(playerSelect.getSelectedItem().toString());
        updating = false;
    }

    public void togglePlayerPerms(String perm, Boolean value) {
        if(!updating)
            Util.setPlayerPermission(playerSelect.getSelectedItem().toString(), perm, value);
    }
}
