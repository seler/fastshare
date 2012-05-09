package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class WWWServer implements Runnable {

    HttpServer server;
    
    public WWWServer() throws IOException{
        server = HttpServer.create(new InetSocketAddress(80), 0);
        server.setExecutor(null);
    }

    public void addContext(Sharing sharing){
        server.createContext("/shares", new GetHandler(sharing));
    }
    
    @Override
    public void run() {
        server.start();
    }
    
}
