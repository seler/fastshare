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
        /*RandomString rs = new RandomString(9);
            System.out.println(rs.nextString());
            System.out.println(rs.nextString());
            System.out.println(rs.nextString());
            System.out.println(rs.nextString());
            System.out.println(rs.nextString());
            */
            
        //GUI.getInstance().setSystemLookAndFeel();

        if (SystemTray.isSupported()) {
            try {   
                if (Settings.load() == true) {
                    GUI.getInstance().showTrayIcon();
                } else {
                    GUI.getInstance().SettingsNotFound();
                }
            } catch (Exception e) {
                System.out.println("BRAK");
                e.printStackTrace();
            }
        }
        /*
         * if(args.length<1){ File thisFile = new
         * File(FastShare.class.getProtectionDomain().getCodeSource().getLocation().toURI());
         * String thisFilePath = thisFile.getName();
         *
         * System.out.println("Bledne argumenty."); System.out.println("Help:
         * java -jar " + thisFilePath + " --help");
         *
         * //System.out.println("Nie wybrano interfejsu.");
         * //NetInterfaces.ListInterfaces(); }
         *
         * if(args.length==1){ ArgParser.parse(args[0]); }
         */

        /*
         * int argCount = args.length; int filesCount = argCount-2;
         *
         * String context = args[0]; 
         * String email = args[1]; 
         * ArrayList<String> files = new ArrayList<String>();
         *
         * //System.out.println("Pliki:");
         *
         * for(int i=0; i<filesCount;i++){ 
         *      files.add(args[2+i]); 
         * }
         *
         * Sharing sharing = new Sharing(context, email); 
         * for(String s : files)
         *      sharing.addFile(s);
         *
         * sharing.print();
         *
         * WWWServer wwwserv = new WWWServer(); 
         * wwwserv.addContext(sharing);
         * wwwserv.run();
         *
         */
    }
}
