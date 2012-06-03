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
    
    public void removeContext(Sharing sharing){
        server.removeContext("/shares"+sharing.getContext());
        FastShare.Sharings.remove(sharing);
        windows.MainWindow.TabModel.clearData();
        
        for(Sharing s : FastShare.Sharings){
            windows.MainWindow.TabModel.addRow(s.getInfo());
        }
    }
    
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
    
    public boolean isRunning(){
        return running;
    }
    
}
