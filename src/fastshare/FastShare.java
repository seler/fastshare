package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class FastShare {
    
    public static void main(String[] args) throws IOException {
        //args[0] = context (np. /app/data)
        //args[1] = plik do serwowania
        
        //System.out.println("arg0:"+args[0]+" | arg1:"+args[1]);
        
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext(args[0], new TestowyHandler(args[1]));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

}
