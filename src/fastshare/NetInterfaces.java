package fastshare;

import java.io.IOException;
import java.net.*;
import java.util.*;
import sun.java2d.pipe.ValidatePipe;

public class NetInterfaces {

    public static void ListInterfaces() throws IOException {
        ArrayList<NetworkInterface> Interfaces   = new ArrayList<NetworkInterface>();
        ArrayList<InetAddress> Addresses         = new ArrayList<InetAddress>();
        
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            Enumeration<InetAddress> address = netIf.getInetAddresses();
            if (address.hasMoreElements()) {
                for (InetAddress adr : Collections.list(address)) {
                    if (adr instanceof Inet4Address){
                        Interfaces.add(netIf);
                        Addresses.add(adr);
                    }
                }
            }
        }
        
        for(int i=0; i<Interfaces.size();i++){
            System.out.print("#" + i + " - ");
            System.out.print(Interfaces.get(i).getName() + " (" + Addresses.get(i).getHostAddress() + ")");
            System.out.print("\n");
        }
        
    }

    
    public static ArrayList<windows.ComboItem> ListInterfacesCombo() throws Exception {
        ArrayList<NetworkInterface> Interfaces   = new ArrayList<NetworkInterface>();
        ArrayList<InetAddress> Addresses         = new ArrayList<InetAddress>();
        
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            Enumeration<InetAddress> address = netIf.getInetAddresses();
            if (address.hasMoreElements()) {
                for (InetAddress adr : Collections.list(address)) {
                    if (adr instanceof Inet4Address){
                        Interfaces.add(netIf);
                        Addresses.add(adr);
                    }
                }
            }
        }
        
        ArrayList<windows.ComboItem> ret = new ArrayList<windows.ComboItem>();
        for(int i=0; i<Interfaces.size();i++){
            String value = Interfaces.get(i).getName();
            String label = value + " - " + Addresses.get(i).getHostAddress();
            windows.ComboItem item = new windows.ComboItem(value,label);
            ret.add(item);
        }
        
        return ret;
    }
    
    
    public static InetAddress getAddress(int interfID) throws IOException {
        ArrayList<InetAddress> Addresses         = new ArrayList<InetAddress>();
        
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            Enumeration<InetAddress> address = netIf.getInetAddresses();
            if (address.hasMoreElements()) {
                for (InetAddress adr : Collections.list(address)) {
                    if (adr instanceof Inet4Address){
                        Addresses.add(adr);
                    }
                }
            }
        }
        
        return Addresses.get(interfID);
        
    }
    
    
}
