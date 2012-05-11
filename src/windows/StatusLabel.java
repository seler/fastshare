package windows;

import javax.swing.JLabel;

public class StatusLabel extends JLabel {
    private String L_interface;
    private String L_port;
    private String L_WWWworking;
    
    public StatusLabel(){
        super("<html><b>Interface:</b><br /><b>Port:</b></html>");
    }
    
    private String working(){
        String ret = "";
        if(fastshare.WWWServer.getInstance().isRunning()==true) ret = "<font color=blue>RUNNING</font>";
        else ret = "<font color=red>STOPPED</font>";
        return ret;
    }
    
    public void reprint(){
        L_interface = fastshare.Settings.getInterface();
        L_port =fastshare.Settings.getPort();
        setText(  "<html><b>Interface: </b>"+L_interface+"<br />"
                + "<b>Port: </b>"+L_port+"<br />"
                + "<b>Status: </b>" + working()
                + "</html>");
    }
}
