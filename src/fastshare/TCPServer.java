package fastshare;

import java.io.*;
import java.net.*;

/**
 * TCP Server waiting for files/directories from 'right-click context menu'
 * in order to share them
 */
public class TCPServer implements Runnable {

    /**
     * Instance - (Singleton)
     */
    private static TCPServer instance = null;
    /**
     * Boolean indicating if TCP Server is running or not
     */
    private boolean running = false;
    /**
     * Server socket
     */
    private ServerSocket srvsock;
    /**
     * Client socket
     */
    private Socket sock;
    /**
     * Input from connected client
     */
    private BufferedReader input;
    /**
     * Output to connected client
     */
    private DataOutputStream output;

    /**
     * Protected constructor - (Singleton)
     */
    protected TCPServer() {
    }

    /**
     * Returns initialized instance or initiates a new one
     * @return initialized instance
     */
    public static TCPServer getInstance() {
        if (instance == null) {
            instance = new TCPServer();
        }
        return instance;
    }

    /**
     * Returns boolean indicating if server is running
     * @return boolean indicating if server is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Runs the TCP Server (on localhost:4444) and waits for connection
     * from client
     */
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

    /**
     * Stops the TCP Server
     */
    public void stop() {
        //Thread.currentThread().interrupt();
        running = false;
    }
}