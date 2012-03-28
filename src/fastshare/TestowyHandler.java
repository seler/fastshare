/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fastshare;

import com.sun.net.httpserver.*;
import java.io.*;
import java.util.ArrayList;

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
        
        fileReader = new FileReader(_filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        String response = "";
        String[] buf = lines.toArray(new String[lines.size()]);
        for(String s : buf) response += s+"\n";
        
        //Headers headers = t.getResponseHeaders();
        //headers.add("Content-Type", "application/xhtml+xml; charset=utf-8");
        
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    //test
    //tralalal
    //chuj
}
