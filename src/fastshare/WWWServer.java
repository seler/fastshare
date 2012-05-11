package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WWWServer implements Runnable {

    private static WWWServer instance = null;
    private boolean running = false;
    HttpServer server;
    
    protected WWWServer() {
    }

    public static WWWServer getInstance() {
        if (instance == null) {
            instance = new WWWServer();
        }
        return instance;
    }

    public void addContext(Sharing sharing){
        server.createContext("/shares", new GetHandler(sharing));
    }
    
    @Override
    public void run() {
        try{
            server = HttpServer.create(new InetSocketAddress(NetInterfaces.getAddressByName(Settings.getInterface()), Integer.parseInt(Settings.getPort())), 0);
            server.setExecutor(null);
            server.createContext("/", new NoContextHandler());
        } catch(Exception e){ }
        server.start();
        running = true;
        FastShare.StatusLabel.reprint();
        try{
            GUI.getInstance().repaintIcon();
        } catch(Exception e){ }
    }
    
    public void stop() {
        server.stop(0);
        server = null;
        running = false;
        FastShare.StatusLabel.reprint();
        try{
            GUI.getInstance().repaintIcon();
        } catch(Exception e){ }
    }
    
    public boolean isRunning(){
        return running;
    }
    
}
