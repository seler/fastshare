package fastshare;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Project's main class - checks if SystemTray is supported and if true, puts
 * icon in systray
 */
public class FastShare {

    /**
     * Label showing information about running instance
     */
    public static windows.StatusLabel StatusLabel = new windows.StatusLabel();
    /**
     * List containing current sharings
     */
    public static ArrayList<Sharing> Sharings = new ArrayList<Sharing>();

    /**
     * Main method
     *
     * @param args CLI params are ignored
     * @throws Exception Throws exception i systray is not supported
     */
    public static void main(String[] args) throws Exception {

        if (SystemTray.isSupported()) {
            try {
                if (Settings.load() == true) {
                    if (Settings.getAutostart().equals("true")) {
                        WWWServer.getInstance().run();
                    }
                    GUI.getInstance().showTrayIcon();
                    TCPServer.getInstance().run();
                } else {
                    Settings.autoFill();
                    Settings.save();
                    if (Settings.load() == true) {
                        if (Settings.getAutostart().equals("true")) {
                            WWWServer.getInstance().run();
                        }
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
