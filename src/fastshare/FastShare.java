package fastshare;

import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

public class FastShare {

    public static void main(String[] args) throws IOException {
        int argCount = args.length;
        int filesCount = argCount-2;
        
        String context = args[0];
        String email = args[1];
        ArrayList<String> files = new ArrayList<String>();
        
        //System.out.println("Pliki:");
        
        for(int i=0; i<filesCount;i++){
            files.add(args[2+i]);
        }
        
        Sharing sharing = new Sharing(context, email);
        for(String s : files) sharing.addFile(s);
        
        sharing.print();
        //for(String s : files) System.out.println(s);
        
        //for(int i=2; i<args.length; i++) files += args[i]+" ";
        /*
        System.out.println("files="+files);
        
        Sharing sharing = new Sharing(context, email, files);
        sharing.print();
        */
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/shares", new GetHandler(sharing));
        server.setExecutor(null);
        server.start();

    }
}
