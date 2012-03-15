package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class FastShare {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
	// testowy komentarz by vltR
        // drugi :P
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 1024);
        server.createContext("/applications/myapp", new TestowyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    
    public void nowyTest(){
        System.out.println("A tu sobie testuje - ostatni raz (mam nadzieje)");
    }
}
