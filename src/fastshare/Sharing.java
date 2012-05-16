package fastshare;

import java.io.File;
import java.util.ArrayList;

public class Sharing {

    private String      context;
    private String      email;
    private String      mainDir;
    public ArrayList<Resource> Resources = new ArrayList<Resource>();

    public String getContext() {
        return this.context;
    }

    public void setContext(String c) {
        this.context = c;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String e) {
        this.email = e;
    }
    
    public Sharing(String _context, String _email){
        setContext(_context);
        setEmail(_email);
    }
    
    public Object[] getInfo(){
        String receivers = getEmail();
        String URL = "";
        
        String ip = Settings.getIP();
        String port = Settings.getPort();

        URL = "http://"+ip+":"+port+"/shares"+getContext()+"/";
        
        return new Object[]{receivers,URL};
    }
    
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
