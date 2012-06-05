package fastshare;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;

/**
 * Handles HTTP requests
 */
public class GetHandler implements HttpHandler {

    /**
     * Specifies handled sharing
     */
    private Sharing sharing;
    /**
     * Specifies requested file
     */
    private File file;

    /**
     * Constructor
     * @param _share 
     */
    public GetHandler(Sharing _share) {
        sharing = _share;
    }

    /**
     * Handles HTTP request, serves requested file or displays error notification
     * @param t HttpExchange object
     * @throws IOException 
     */
    public void handle(HttpExchange t) throws IOException {
        String get = t.getRequestURI().getPath();
        String getContext = get.replace("/shares", "");

        //System.out.println("get: " + get);
        Boolean fileFound = false;
        Boolean listedFiles = false;

        //System.out.println("getContext: " + getContext);

        for (Resource r : sharing.Resources) {
            if (r.getResContext().equals(getContext)) {
                serveFile(t, r.getLocation());
                fileFound = true;
            }
        }
        if ((getContext.equals(sharing.getContext() + "/")) || (getContext.equals(sharing.getContext()))) {
            try {
                String html_ = getResourceAsString("/html/listfiles.html");
                String html = html_.replaceAll("__filelist__", fileList());
                byte[] res = html.getBytes();
                t.sendResponseHeaders(200, res.length);
                OutputStream os = t.getResponseBody();
                os.write(res, 0, res.length);
                os.close();
                listedFiles = true;
                //System.out.println(html_);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if ((fileFound == false) && (listedFiles == false)) {
            //System.out.println("404");
            serveResource(t, "/html/404.html");
        }
    }

    /**
     * Generates available files' list in HTML
     * @return files' list in HTML
     */
    private String fileList() {
        String ret = "";
        for (Resource r : sharing.Resources) {
            String name = r.getResContext().replace(sharing.getContext() + "/", "");
            String link = "<a href='" + r.getLink() + "'>[Download]</a>";

            ret += "<tr><td width=75%>" + name + "</td><td width=25%>" + link + "</td></tr>";
        }
        return ret;
    }

    /**
     * Serves requested file
     * @param t HttpExchange object
     * @param res Resource URI
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
     * Converts static resources to String
     * @param res Resource URI
     * @return String describing static resource
     * @throws Exception 
     */
    private String getResourceAsString(String res) throws Exception {
        InputStream is = getClass().getResourceAsStream(res);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    /**
     * Serves requested file in HTTP request
     * @param t HttpExchange object
     * @param location
     * @throws IOException 
     */
    private void serveFile(HttpExchange t, String location) throws IOException {
        byte[] file = getFileToByte(location);
        t.sendResponseHeaders(200, file.length);
        OutputStream os = t.getResponseBody();
        os.write(file, 0, file.length);
        os.close();
    }

    /**
     * Obtains byte data of InputStream parameter
     * @param is InputStream parameter
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

    /**
     * Obtains byte data of file parameter
     * @param file
     * @return array of byte's
     * @throws IOException
     */
    private byte[] getFileToByte(String file) throws IOException {
        File f = new File(file);
        byte[] ret = new byte[(int) f.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(ret, 0, ret.length);
        bis.close();
        fis.close();
        return ret;
    }
}
