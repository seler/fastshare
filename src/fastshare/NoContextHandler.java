package fastshare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

public class NoContextHandler implements HttpHandler {

    public NoContextHandler() {
    }

    public void handle(HttpExchange t) throws IOException {
        serveResource(t, "/html/nocontext.html");
    }

    private void serveResource(HttpExchange t, String res) throws IOException {
        InputStream resFile = getClass().getResourceAsStream(res);
        byte[] bytearray = obtainByteData(resFile);
        t.sendResponseHeaders(200, bytearray.length);
        OutputStream os = t.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
    }

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

