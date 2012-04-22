package fastshare;

import java.io.File;

public class Resource {

    private String location;
    private String resContext;

    private void setLocation(String _location) {
        this.location = _location;
    }

    private void setResContext(String _resContext) {
        this.resContext = _resContext;
    }

    public String getLocation() {
        return this.location;
    }

    public String getResContext() {
        return this.resContext;
    }

    public Resource(String _path, String _context) {
        File file = new File(_path);
        setLocation(file.getAbsolutePath());
        setResContext(_context +"/"+ file.getName() ); //file.getPath();
        resContext = resContext.replace("\\" , "/");
    }
}
