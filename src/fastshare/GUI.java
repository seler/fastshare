package fastshare;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import windows.*;


public class GUI {
    
    public static void setSystemLookAndFeel() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    
    public static void showTrayIcon() throws Exception {
        Image icon = ImageIO.read(FastShare.class.getResource("/ico/tray_on.gif"));
        
        PopupMenu menu = new PopupMenu();

        MenuItem showConfig = new MenuItem("Pokaz Ustawienia");
        menu.add(showConfig);
        MenuItem exit = new MenuItem("Zamknij");
        menu.add(exit);
        
        showConfig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings();
            }
	});
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
	});
        
        
        TrayIcon trayIcon = new TrayIcon(icon, "FastShare", menu);
        SystemTray.getSystemTray().add(trayIcon);
    }
    
    public static void SettingsNotFound(){
        SettingsNotFound win = new SettingsNotFound();
        win.setVisible(true);
    }
    
    public static void Settings(){
        windows.Settings SettingsWin = new windows.Settings();
        SettingsWin.setVisible(true);
    }
    
}
