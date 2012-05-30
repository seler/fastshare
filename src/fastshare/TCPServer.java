package fastshare;

import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {

    private static TCPServer instance = null;
    private boolean running = false;
    private ServerSocket srvsock;
    private Socket sock;
    private BufferedReader input;
    private DataOutputStream output;

    protected TCPServer() {
    }

    public static TCPServer getInstance() {
        if (instance == null) {
            instance = new TCPServer();
        }
        return instance;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        running = true;
        try {
            srvsock = new ServerSocket(4444);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        while (isRunning()) {
            try {
                sock = srvsock.accept();
                input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                output = new DataOutputStream(sock.getOutputStream());
                String in;
                while ((in = input.readLine()) != null) {
                    System.out.println("echo: " + in);
                    windows.NewSharing SharingWin = new windows.NewSharing(in);
                    SharingWin.setVisible(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() {
        //Thread.currentThread().interrupt();
        running = false;
    }
}