package fastshare;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import windows.*;

/**
 * Singleton, performs all GUI operations
 */
public class GUI {

    /**
     * Allows only one instance (Singleton design pattern)
     */
    private static GUI instance = null;
    /**
     * SysTray icon image
     */
    private static Image icon;
    /**
     * SysTray icon object
     */
    private static TrayIcon trayIcon;
    /**
     * Application's main window
     */
    private static windows.MainWindow SettingsWin;

    /**
     * Protected constructor, singleton
     *
     * @throws Exception
     */
    protected GUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SettingsWin = new windows.MainWindow();
        } catch (Exception e2) {
        }
    }

    /**
     * Returns initializated instance or creates a new one
     */
    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    /**
     * Sets 'off' tray icon (the red one)
     *
     * @throws Exception
     */
    private static void setIconOff() throws Exception {
        icon = ImageIO.read(FastShare.class.getResource("/ico/tray_off.gif"));
    }

    /**
     * Sets 'on' tray icon (the green one)
     *
     * @throws Exception
     */
    private static void setIconOn() throws Exception {
        icon = ImageIO.read(FastShare.class.getResource("/ico/tray_on.gif"));
    }

    /**
     * Repaints tray icon, checking if WWWServer is running
     *
     * @throws Exception
     */
    public static void repaintIcon() throws Exception {
        if (WWWServer.getInstance().isRunning() == true) {
            setIconOn();
        } else {
            setIconOff();
        }
        trayIcon.setImage(icon);
    }

    /**
     * Sets system's default Look & Feel
     *
     * @throws Exception
     */
    public static void setSystemLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    /**
     * Shows icon in system tray
     *
     * @throws Exception
     */
    public static void showTrayIcon() throws Exception {
        //Image icon = ImageIO.read(FastShare.class.getResource("/ico/tray_off.gif"));
        if (fastshare.WWWServer.getInstance().isRunning() == false) {
            setIconOff();
        }
        if (fastshare.WWWServer.getInstance().isRunning() == true) {
            setIconOn();
        }

        PopupMenu menu = new PopupMenu();

        MenuItem showConfig = new MenuItem("Status & Settings");
        menu.add(showConfig);
        MenuItem exit = new MenuItem("Exit");
        menu.add(exit);

        showConfig.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Settings();
                } catch (Exception a) {
                }
            }
        });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        trayIcon = new TrayIcon(icon, "FastShare", menu);
        trayIcon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Settings();
                } catch (Exception ex) {
                }
            }
        });

        SystemTray.getSystemTray().add(trayIcon);
    }

    /**
     * Shows messagebox saying 'Settings not found'
     */
    public static void SettingsNotFound() {
        SettingsNotFound win = new SettingsNotFound();
        win.setVisible(true);
    }

    /**
     * Displays application's main window
     */
    public static void Settings() throws Exception {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - SettingsWin.getSize().width) / 2;
        int y = (dim.height - SettingsWin.getSize().height) / 2;
        SettingsWin.setLocation(x, y);
        SettingsWin.setVisible(true);
    }
}
