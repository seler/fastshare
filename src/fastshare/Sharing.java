package fastshare;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents single sharing entry
 */
public class Sharing {

    /**
     * Individual context in sharing's URL
     */
    private String      context;
    /**
     * Specifies receivers' e-mail addresses (separated by space)
     */
    private String      email;
    /**
     * Sharing's top directory
     */
    private String      mainDir;
    /**
     * List of Resources belonging to sharing
     */
    public ArrayList<Resource> Resources = new ArrayList<Resource>();

    /**
     * Returns sharing's individual context
     * @return sharing's individual context
     */
    public String getContext() {
        return this.context;
    }

    /**
     * Sets sharing's individual context
     * @param c sharing's individual context
     */
    public void setContext(String c) {
        this.context = c;
    }

    /**
     * Returns sharing's receivers' e-mail addresses (separated by space)
     * @return 
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets sharing's receivers' e-mail addresses (separated by space)
     * @param e sharing's receivers' e-mail addresses (separated by space)
     */
    public void setEmail(String e) {
        this.email = e;
    }
    
    /**
     * Constructor - needs context and e-mail addresses
     * @param _context individual sharing's context
     * @param _email receivers' e-mail addreses
     */
    public Sharing(String _context, String _email){
        setContext(_context);
        setEmail(_email);
    }
    
    /**
     * Returns basic sharing's info - used in main window's table
     * @return basic sharing's info
     */
    public Object[] getInfo(){
        String receivers = getEmail();
        String URL = "";
        
        String ip = Settings.getIP();
        String port = Settings.getPort();

        URL = "http://"+ip+":"+port+"/shares"+getContext()+"/";
        
        return new Object[]{receivers,URL};
    }
    
    /**
     * Adds file or directory to sharing
     * @param _file file or directory to be added to sharing
     */
    public void addFile(String _file){
            File file = new File(_file); // f
            String name = file.getName();
            
            if(file.isDirectory()==false)
                Resources.add(new Resource(_file, getContext())); //f -> 
            
            if(file.isDirectory()==true){
                mainDir = _file;
                if(!mainDir.endsWith("/")) mainDir += "/";
                recurseDirectory(_file);                // f
            }
            
        //}
    }
    
    /**
     * Parses whole directory and it's subdirectories
     * @param dir directory to be shared
     */
    private void recurseDirectory(String dir){
        //System.out.println("recurseDirectory.dir="+dir);
        File f = new File(dir);
        String name = f.getName();
        for(File child : f.listFiles()){
            if( child.isDirectory() == false){
                // path, context
                String newDir = dir.replace(mainDir, "");
                File tmp = new File(newDir);
                Resources.add(new Resource(dir+"/"+child.getName(), getContext()+"/"+tmp.getName()));
                //bylo: Resources.add(new Resource(dir+"/"+child.getName(), getContext()+"/"+newDir));
                //File tmp = new File(newDir);
                
                //System.out.println("context: "+getContext()+"/"+tmp.getName());
            }
            else{
                recurseDirectory(dir+"/"+child.getName());
            }
        }
    }
    
    /**
     * Prints basic info about sharing to stdout
     */
    public void print(){
        System.out.println("Context: "+getContext());
        System.out.println("Email: "+getEmail());
        System.out.println("--- RESOURCES ---");
        for(Resource r : Resources){
            System.out.println("Resource Location: " + r.getLocation() );
            System.out.println("Resource Context: " + r.getResContext() );
            System.out.println("");
        }
    }
    
}
