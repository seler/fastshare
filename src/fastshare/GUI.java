package fastshare;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import windows.*;

public class GUI {

    private static GUI instance = null;
    private static Image icon;
    private static TrayIcon trayIcon;
    private static windows.MainWindow SettingsWin;
    

    protected GUI() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SettingsWin = new windows.MainWindow();
        } catch(Exception e2){ }
    }

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    private static void setIconOff() throws Exception {
        icon = ImageIO.read(FastShare.class.getResource("/ico/tray_off.gif"));
    }

    private static void setIconOn() throws Exception {
        icon = ImageIO.read(FastShare.class.getResource("/ico/tray_on.gif"));
    }

    public static void repaintIcon() throws Exception {
        if (WWWServer.getInstance().isRunning() == true) {
            setIconOn();
        } else {
            setIconOff();
        }
        trayIcon.setImage(icon);
    }

    public static void setSystemLookAndFeel() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

    public static void showTrayIcon() throws Exception {
        //Image icon = ImageIO.read(FastShare.class.getResource("/ico/tray_off.gif"));
        if(fastshare.WWWServer.getInstance().isRunning()==false) setIconOff();
        if(fastshare.WWWServer.getInstance().isRunning()==true) setIconOn();
        
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
                try{
                    Settings();
                } catch(Exception ex){ }
            }
        });

        SystemTray.getSystemTray().add(trayIcon);
    }

    public static void SettingsNotFound() {
        SettingsNotFound win = new SettingsNotFound();
        win.setVisible(true);
    }

    public static void Settings() throws Exception {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width-SettingsWin.getSize().width)/2;
        int y = (dim.height-SettingsWin.getSize().height)/2;
        SettingsWin.setLocation(x, y);
        SettingsWin.setVisible(true);
    }
}
