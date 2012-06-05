package windows;

import javax.swing.JLabel;

/**
 * Extended JLabel - shows most important info
 * (interface, port, server on/off)
 */
public class StatusLabel extends JLabel {
    /**
     * variable describing selected interface
     */
    private String L_interface;
    /**
     * variable describing slected port
     */
    private String L_port;
    /**
     * variable indicating if HTTP Server is running or not
     */
    private String L_WWWworking;
    
    /**
     * Constructor
     */
    public StatusLabel(){
        super("<html><b>Interface:</b><br /><b>Port:</b></html>");
    }
    
    /**
     * Returns "Server RUNNING/STOPPED" info
     * @return RUNNING/STOPPED
     */
    private String working(){
        String ret = "";
        if(fastshare.WWWServer.getInstance().isRunning()==true) ret = "<font color=blue>RUNNING</font>";
        else ret = "<font color=red>STOPPED</font>";
        return ret;
    }
    
    /**
     * Reprints label, refreshes information
     */
    public void reprint(){
        L_interface = fastshare.Settings.getInterface();
        L_port =fastshare.Settings.getPort();
        setText(  "<html><b>Interface: </b>"+L_interface+"<br />"
                + "<b>Port: </b>"+L_port+"<br />"
                + "<b>Status: </b>" + working()
                + "</html>");
    }
}
