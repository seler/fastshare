/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastshare;

import com.sun.net.httpserver.*;
import java.io.*;

/**
 *
 * @author vltR
 */
public class TestowyHandler implements HttpHandler {
    private String _filename;
    private File file;
    private FileReader fileReader;
    
    public TestowyHandler( String filename ){
         _filename = filename;
          file = new File(_filename);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        //read(is); // .. read the request body
        //String response = "This is the response " + t.getRemoteAddress().getHostName() + ":)";
        fileReader = new FileReader(_filename);
        char[] buf = new char[1048576];
        fileReader.read(buf);
        String response = String.copyValueOf(buf);
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    //test
    //tralalal
    //chuj
}
