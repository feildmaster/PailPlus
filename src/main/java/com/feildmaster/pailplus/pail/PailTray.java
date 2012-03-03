package com.feildmaster.pailplus.pail;

import com.feildmaster.pailplus.monitor.Util;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class PailTray {
    private static final TrayIcon PAIL_ICON = new TrayIcon(Util.getPail().PAIL_ICON);

    static {
        PAIL_ICON.setToolTip("Pail "+Util.getPail().getDescription().getVersion());
        PAIL_ICON.setImageAutoSize(true);
        PAIL_ICON.setPopupMenu(new PailMenu());
        PAIL_ICON.addMouseListener(new Listener());
    }

    public void addIcon() {
        if(!SystemTray.isSupported() || isActive()) return;
        try {
            SystemTray.getSystemTray().add(PAIL_ICON);
        } catch (AWTException ex) {
            Util.getPailPlus().getLogger().throwing("PailPlusTray", "addIcon", ex);
        }
    }

    public void removeIcon() {
        if(!isActive()) return;
        SystemTray.getSystemTray().remove(PAIL_ICON);
    }

    public void showWarning(String message) {
        showMessage("Warning", message, TrayIcon.MessageType.WARNING);
    }
    public void showInfo(String message) {
        showMessage("Notice", message, TrayIcon.MessageType.INFO);
    }
    public void showError(String message) {
        showMessage("Error", message, TrayIcon.MessageType.ERROR);
    }
    public void showMessage(String message) {
        showMessage("PailPlus", message, TrayIcon.MessageType.NONE);
    }
    public void showMessage(String title, String message, TrayIcon.MessageType type) {
        if(!isActive()) return;
        PAIL_ICON.displayMessage(title, message, type);
    }

    public boolean isActive() {
        if(!SystemTray.isSupported()) return false;
        return Arrays.asList(SystemTray.getSystemTray().getTrayIcons()).contains(PAIL_ICON);
    }

    static class Listener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2 && !e.isConsumed()) {
                    Util.toggleWindow();
                    e.consume();
                }
        }

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
    }

    static class PailMenu extends PopupMenu {
        public PailMenu() {
            add(openPail());
            add(separator());
            add(actions());
//            add(save());
//            add(reload());
//            add(shutdown());
            add(separator());
            add(closePail());
        }

        private MenuItem openPail() {
            MenuItem item = new MenuItem();
            item.setLabel("Open Pail");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Util.showWindow();
                }
            });
            return item;
        }

        private MenuItem closePail() {
            MenuItem item = new MenuItem();
            item.setLabel("Close Pail");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Util.hideWindow();
                }
            });
            return item;
        }

        private MenuItem shutdown() {
            MenuItem item = new MenuItem();
            item.setLabel("Shutdown");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Util.saveAll();
                    Util.stop();
                }
            });
            return item;
        }

        private Menu actions() {
            Menu menu = new Menu();
            menu.setLabel("Actions");
            menu.add(save());
            menu.add(reload());
            menu.add(shutdown());
            return menu;
        }

        private MenuItem save() {
            MenuItem item = new MenuItem();
            item.setLabel("Save");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Util.saveAll();
                }
            });
            return item;
        }

        private MenuItem reload() {
            MenuItem item = new MenuItem();
            item.setLabel("Reload");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Util.reload();
                }
            });
            return item;
        }

        private MenuItem separator() {
            MenuItem item = new MenuItem();
            item.setLabel("-");
            return item;
        }
    }
}
