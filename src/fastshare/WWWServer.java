package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * HTTP Server serving resources to the clients
 */
public class WWWServer implements Runnable {

    /**
     * Instance - (Singleton)
     */
    private static WWWServer instance = null;
    /**
     * Boolean indicating if server is running or not
     */
    private boolean running = false;
    /**
     * HTTP Server implementation
     */
    HttpServer server;
    
    /**
     * Protected constructor - (Singleton)
     */
    protected WWWServer() {
    }

    /**
     * Returns initialized instance
     * @return initialized instance
     */
    public static WWWServer getInstance() {
        if (instance == null) {
            instance = new WWWServer();
        }
        return instance;
    }

    /**
     * Adds new sharing. From now, sharing is available via HTTP
     * @param sharing object representing sharing
     */
    public void addContext(Sharing sharing){
        server.createContext("/shares"+sharing.getContext(), new GetHandler(sharing));
        FastShare.Sharings.add(sharing);
        
        windows.MainWindow.TabModel.clearData();
        
        for(Sharing s : FastShare.Sharings){
            windows.MainWindow.TabModel.addRow(s.getInfo());
        }
        
        String url = getURL() + sharing.getContext()+"/";
        String sMail = sharing.getEmail();
        String[] mails = sMail.split(" ");
        for(String m : mails){
            Mailer.sendNotification(m, url);
        }
    }
    
    /**
     * Returns the server's URL (IP and port)
     * @return the server's URL
     */
    private String getURL(){
        String ip = "";
        String port = Settings.getPort();
        try {
            ip = NetInterfaces.getAddressByName(Settings.getInterface()).getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "http://"+ip+":"+port+"/shares";
    }
    
    /**
     * Removes sharing. From now, sharing is not availble via HTTP
     * @param sharing object representing sharing
     */
    public void removeContext(Sharing sharing){
        server.removeContext("/shares"+sharing.getContext());
        FastShare.Sharings.remove(sharing);
        windows.MainWindow.TabModel.clearData();
        
        for(Sharing s : FastShare.Sharings){
            windows.MainWindow.TabModel.addRow(s.getInfo());
        }
    }
    
    /**
     * Starts the server on interface and port specified in Settings
     */
    @Override
    public void run() {
        try{
            server = HttpServer.create(new InetSocketAddress(NetInterfaces.getAddressByName(Settings.getInterface()), Integer.parseInt(Settings.getPort())), 0);
            //server = HttpServer.create(new InetSocketAddress(8080), 0 );
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
    
    /**
     * Stops the server
     */
    public void stop() {
        server.stop(0);
        server = null;
        running = false;
        FastShare.StatusLabel.reprint();
        FastShare.Sharings.clear();
        try{
            GUI.getInstance().repaintIcon();
        } catch(Exception e){ }
    }
    
    /**
     * Returns boolean indicating if server is running
     * @return boolean indicating if server is running
     */
    public boolean isRunning(){
        return running;
    }
    
}
