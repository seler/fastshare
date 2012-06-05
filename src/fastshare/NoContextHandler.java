package fastshare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

/**
 * Handles HTTP requests without specified sharing's context
 */
public class NoContextHandler implements HttpHandler {

    /**
     * Empty constructor
     */
    public NoContextHandler() {
    }

    /**
     * Serves static 'html/nocontext.html' page
     * @param t HttpExchange object
     * @throws IOException 
     */
    public void handle(HttpExchange t) throws IOException {
        serveResource(t, "/html/nocontext.html");
    }

    /**
     * Serves static resource
     * @param t HttpExchange object
     * @param res Resource's path
     * @throws IOException 
     */
    private void serveResource(HttpExchange t, String res) throws IOException {
        InputStream resFile = getClass().getResourceAsStream(res);
        byte[] bytearray = obtainByteData(resFile);
        t.sendResponseHeaders(200, bytearray.length);
        OutputStream os = t.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
    }

    /**
     * Obtains byte data of specified resource
     * @param is InputStream
     * @return array of byte's
     * @throws IOException 
     */
    private byte[] obtainByteData(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int readBytes = is.read(); readBytes >= 0; readBytes = is.read()) {
            outputStream.write(readBytes);
        }
        // Convert the contents of the output stream into a byte array
        byte[] byteData = outputStream.toByteArray();
        is.close();
        outputStream.close();
        return byteData;
    }

}

