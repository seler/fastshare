package fastshare;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.util.ArrayList;
import javax.activation.*;

public class TestowyHandler implements HttpHandler {
    private String filename;
    private File file;
    private FileReader fileReader;
    private String mimeType;
    
    public TestowyHandler( String _filename ){
          filename = _filename;
          file = new File(filename);
          mimeType = new MimetypesFileTypeMap().getContentType(file);
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        
        /*fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        */
        
        String response = "";
        //String[] buf = lines.toArray(new String[lines.size()]);
        //for(String s : buf) response += s+"\n";

        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        while(bis.read() != -1){
            response += bis.read();
        }
        Headers headers = t.getResponseHeaders();
        headers.add("Content-Type", mimeType+"; charset=utf-8");
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
