package tcpclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        String param = "";
        int i=0;
        for(String s : args){
            if(i>0) param += "||";
            param += s;
            i++;
        }

        Socket clientSocket = new Socket("localhost", 4444);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(param + '\n');
        clientSocket.close();
        
    }
}
