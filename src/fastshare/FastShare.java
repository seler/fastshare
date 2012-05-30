package fastshare;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FastShare {
    
    public static windows.StatusLabel StatusLabel = new windows.StatusLabel();
    public static ArrayList<Sharing> Sharings = new ArrayList<Sharing>();

    public static void main(String[] args) throws Exception {
            
        //GUI.getInstance().setSystemLookAndFeel();
        // System.out.println(NetInterfaces.getFirstInterfaceName());
        //TCPServer.getInstance().run();
        //Thread servThread = new Thread(TCPServer.getInstance());
        //servThread.run();
        if (SystemTray.isSupported()) {
            try {   
                if (Settings.load() == true) {
                    GUI.getInstance().showTrayIcon();
                    TCPServer.getInstance().run();
                } else {
                    Settings.autoFill();
                    Settings.save();
                    if(Settings.load()==true){
                        GUI.getInstance().showTrayIcon();
                        TCPServer.getInstance().run();
                    }
                }
            } catch (Exception e) {
                System.out.println("BRAK OBSLUGI SYSTRAY");
                e.printStackTrace();
            }
        }

    }
}
