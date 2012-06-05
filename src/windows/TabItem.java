package windows;

/**
 * Main window's table item representation
 */
public class TabItem {
    /**
     * variabe describing sharing's receivers
     */
    private String Receivers;
    /**
     * variable describing sharing's URL
     */
    private String URL;
    
    /**
     * Constructor
     * @param s receivers
     * @param u URL
     */
    public TabItem(String s, String u){
        setReceivers(s);
        setURL(u);
    }
    
    /**
     * Sets receivers
     * @param s receivers
     */
    public void setReceivers(String s){
        this.Receivers = s;
    }
    /**
     * Sets URL
     * @param u URL 
     */
    public void setURL(String u){
        this.URL = u;
    }
    
    /**
     * Returns receivers
     * @return receivers
     */
    public String getReceivers(){
        return Receivers;
    }
    /**
     * Returns URL
     * @return URL
     */
    public String getURL(){
        return URL;
    }
}
