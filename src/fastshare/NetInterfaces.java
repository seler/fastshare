package fastshare;

import java.io.IOException;
import java.net.*;
import java.util.*;
import sun.java2d.pipe.ValidatePipe;

/**
 * Manages network interfaces
 */
public class NetInterfaces {

    /**
     * Lists available network interfaces on standard output
     * @throws IOException 
     */
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

    /**
     * Lists available network interfaces and their IP adresses on main window's ComboBox
     * @return Combo Items
     * @throws Exception 
     */
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
    
    /**
     * Returns IP address of specified network interface
     * @param interfID interface ID
     * @return IP address
     * @throws IOException 
     */
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
    
    /**
     * Retuns IP Address of specified network interface
     * @param name  interface name
     * @return IP address
     * @throws IOException 
     */
    public static InetAddress getAddressByName(String name) throws IOException {
        InetAddress ret;
        
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
        
        for(int i=0; i<Interfaces.size(); i++){
            if(Interfaces.get(i).getName().equals(name)) return Addresses.get(i);
        }
        
        return null;
    }
    
    /**
     * Returns first available network interface
     * @return first available network interface's name
     * @throws IOException 
     */
    public static String getFirstInterfaceName() throws IOException {
        ArrayList<NetworkInterface> Interfaces   = new ArrayList<NetworkInterface>();
        
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            Interfaces.add(netIf);
        }
        
        return Interfaces.get(0).getName();
    }
    
    /**
     * Returns true if specified network interface is available
     * @param name network interface's name
     * @return boolean describing interface's availability
     * @throws IOException 
     */
    public static Boolean isAvailable(String name) throws IOException {
        Boolean ret = false;
        ArrayList<NetworkInterface> Interfaces   = new ArrayList<NetworkInterface>();
        
        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netIf : Collections.list(nets)) {
            Interfaces.add(netIf);
        }

        for(int i=0; i<Interfaces.size(); i++){
            if(Interfaces.get(i).getName().equals(name)) ret = true;
        }
        return ret;
    }
    
    
}
