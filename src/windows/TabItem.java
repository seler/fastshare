package windows;

public class TabItem {
    private String Receivers;
    private String URL;
    
    public TabItem(String s, String u){
        setReceivers(s);
        setURL(u);
    }
    
    public void setReceivers(String s){
        this.Receivers = s;
    }
    public void setURL(String u){
        this.URL = u;
    }
    
    public String getReceivers(){
        return Receivers;
    }
    public String getURL(){
        return URL;
    }
}
