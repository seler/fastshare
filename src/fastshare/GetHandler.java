package fastshare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

public class GetHandler implements HttpHandler {

    private Sharing sharing;
    private File file;

    public GetHandler(Sharing _share) {
        sharing = _share;
    }

    public void handle(HttpExchange t) throws IOException {
        String get = t.getRequestURI().getPath();
        String getContext = get.replace("/shares", "");

        System.out.println("get: " + get);
        Boolean fileFound = false;

        System.out.println("getContext: " + getContext);

        for (Resource r : sharing.Resources) {
            if (r.getResContext().equals(getContext)) {
                serveFile(t, r.getLocation());
                /*byte[] file = getFileToByte(r.getLocation());
                t.sendResponseHeaders(200, file.length);
                OutputStream os = t.getResponseBody();
                os.write(file, 0, file.length);
                os.close();
                System.out.println("TRAF!");*/
                fileFound = true;
            }
        }
        if (fileFound == false) {
            System.out.println("404");
            serveResource(t, "/html/404.html");
        }
    }

    private void serveResource(HttpExchange t, String res) throws IOException {
        InputStream resFile = getClass().getResourceAsStream(res);
        byte[] bytearray = obtainByteData(resFile);
        t.sendResponseHeaders(200, bytearray.length);
        OutputStream os = t.getResponseBody();
        os.write(bytearray, 0, bytearray.length);
        os.close();
    }
    
    private void serveFile(HttpExchange t, String location) throws IOException {
        byte[] file = getFileToByte(location);
        t.sendResponseHeaders(200, file.length);
        OutputStream os = t.getResponseBody();
        os.write(file, 0, file.length);
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
 
    private byte[] getFileToByte(String file) throws IOException{
        File f = new File(file);
        byte[] ret = new byte[(int)f.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(ret, 0, ret.length);
        bis.close();
        fis.close();
        return ret;
    }
}
