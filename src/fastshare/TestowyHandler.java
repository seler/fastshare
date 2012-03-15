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

    @Override
    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        //read(is); // .. read the request body
        String response = "This is the response " + t.getRemoteAddress().getHostName() + ":)";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    //test
    //tralalal
}
