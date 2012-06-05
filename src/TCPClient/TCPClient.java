package tcpclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Simple CLI app which sends filenames of files/directories, if user selects
 * them and makes 'Send to -> FastShare'.
 */
public class TCPClient {

    /**
     * @param args	Command-line arguments - files and directories to share
     * @return	0 if connected
     * @throws Exception Throws exception if can't make connection
     */
    public static void main(String[] args) throws Exception {
        String param = "";
        int i = 0;
        for (String s : args) {
            if (i > 0) {
                param += "||";
            }
            param += s;
            i++;
        }

        Socket clientSocket = new Socket("localhost", 4444);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(param + '\n');
        clientSocket.close();
    }
}
