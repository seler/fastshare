package fastshare;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Manages user settings
 */
public class Settings {

    private static Settings instance = null;
    private static String S_interface;
    private static String S_port;
    private static String S_ip;
    private static String S_username;
    private static String S_password;
    private static String S_smtphost;
    private static String S_smtpport;
    private static String S_starttls;
    private static String S_auth;
    private static String S_autostart;

    /**
     * Protected constructor - Singleton
     */
    protected Settings() {
    }

    /**
     * Returns initializated instance, or initiates a new one
     * @return instance
     */
    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    /**
     * Loads user settings from XML file
     * @return boolean, indicating if settings were loaded
     * @throws Exception 
     */
    public static boolean load() throws Exception {
        boolean error = false;
        String homePath = System.getProperty("user.home"); // C\\Users\\nazwa
        
        File xmlFile = new File(homePath + "/.fastshare");
        if (xmlFile.exists() == true) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("settings");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    if (NetInterfaces.isAvailable(getTagValue("interface", eElement)) == true) {
                        setInterface(getTagValue("interface", eElement));
                    } else {
                        setInterface(NetInterfaces.getFirstInterfaceName());
                    }
                    try {
                        setPort(getTagValue("port", eElement));
                        setUsername(getTagValue("username", eElement));
                        setPassword(getTagValue("password", eElement));
                        setSmtphost(getTagValue("smtphost", eElement));
                        setSmtpport(getTagValue("smtpport", eElement));
                        setStarttls(getTagValue("starttls", eElement));
                        setAuth(getTagValue("auth", eElement));
                        setAutostart(getTagValue("autostart", eElement));
                    } catch (Exception e) {
                        error = true;
                    }
                }
            }
            if (error == false) {
                FastShare.StatusLabel.reprint();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Saves user settings to XML file
     * @return boolean indicating if settings were saved
     * @throws Exception 
     */
    public static boolean save() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("fastshare");
        doc.appendChild(rootElement);

        // settings
        Element settings = doc.createElement("settings");
        rootElement.appendChild(settings);

        // interface
        Element _interface = doc.createElement("interface");
        _interface.appendChild(doc.createTextNode(S_interface));
        settings.appendChild(_interface);

        // port
        Element _port = doc.createElement("port");
        _port.appendChild(doc.createTextNode(S_port));
        settings.appendChild(_port);

        // username
        Element _username = doc.createElement("username");
        _username.appendChild(doc.createTextNode(S_username));
        settings.appendChild(_username);

        // password
        Element _password = doc.createElement("password");
        _password.appendChild(doc.createTextNode(S_password));
        settings.appendChild(_password);

        // smtphost
        Element _smtphost = doc.createElement("smtphost");
        _smtphost.appendChild(doc.createTextNode(S_smtphost));
        settings.appendChild(_smtphost);

        // smtpport
        Element _smtpport = doc.createElement("smtpport");
        _smtpport.appendChild(doc.createTextNode(S_smtpport));
        settings.appendChild(_smtpport);

        // starttls
        Element _starttls = doc.createElement("starttls");
        _starttls.appendChild(doc.createTextNode(S_starttls));
        settings.appendChild(_starttls);

        // auth
        Element _auth = doc.createElement("auth");
        _auth.appendChild(doc.createTextNode(S_auth));
        settings.appendChild(_auth);
        
        // auth
        Element _autostart = doc.createElement("autostart");
        _autostart.appendChild(doc.createTextNode(S_autostart));
        settings.appendChild(_autostart);

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        
        String homePath = System.getProperty("user.home"); // C\\Users\\vltR
        StreamResult result = new StreamResult(new File(homePath + "/.fastshare"));
        //Runtime.getRuntime().exec("attrib +H " + System.getProperty("user.home") + "/.fastshare");
        transformer.transform(source, result);

        FastShare.StatusLabel.reprint();
        return true;
    }

    /**
     * Sets default settings if can't load from XML file
     */
    public static void autoFill() {
        try {
            setInterface(NetInterfaces.getFirstInterfaceName());
            setPort("8080");
            setUsername("fastshare123@gmail.com");
            setPassword("fastshare123");
            setSmtphost("smtp.gmail.com");
            setSmtpport("587");
            setStarttls("true");
            setAuth("true");
            setAutostart("false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Gets tag value from XML format
     * @param sTag  tag name
     * @param eElement XML node
     * @return tag value
     */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    /**
     * Sets interface name
     * @param iface interface name
     */
    public static void setInterface(String iface) {
        S_interface = iface;
        try {
            S_ip = NetInterfaces.getAddressByName(Settings.getInterface()).getHostAddress();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * Returns IP address
     * @return IP address
     */
    public static String getIP() {
        return S_ip;
    }

    /**
     * Returns interface name
     * @return interface name
     */
    public static String getInterface() {
        return S_interface;
    }

    /**
     * Sets HTTP port
     * @param port HTTP port
     */
    public static void setPort(String port) {
        S_port = port;
    }

    /**
     * Returns HTTP port
     * @return HTTP port
     */
    public static String getPort() {
        return S_port;
    }

    /**
     * Sets SMTP username
     * @param username SMTP username
     */
    public static void setUsername(String username) {
        S_username = username;
    }

    /**
     * Returns SMTP username
     * @return SMTP username
     */
    public static String getUsername() {
        return S_username;
    }

    /**
     * Sets SMTP password
     * @param password SMTP password
     */
    public static void setPassword(String password) {
        S_password = password;
    }

    /**
     * Returns SMTP password
     * @return SMTP password
     */
    public static String getPassword() {
        return S_password;
    }

    /**
     * Sets SMTP host
     * @param smtphost SMTP host 
     */
    public static void setSmtphost(String smtphost) {
        S_smtphost = smtphost;
    }

    /**
     * Returns SMTP host
     * @return SMTP host
     */
    public static String getSmtphost() {
        return S_smtphost;
    }

    /**
     * Sets SMTP port
     * @param smtpport SMTP port 
     */
    public static void setSmtpport(String smtpport) {
        S_smtpport = smtpport;
    }

    /**
     * Returns SMTP port
     * @return SMTP port
     */
    public static String getSmtpport() {
        return S_smtpport;
    }

    /**
     * Returns STARTTLS boolean
     * @param starttls STARTTLS boolean
     */
    public static void setStarttls(String starttls) {
        S_starttls = starttls;
    }

    /**
     * Returns STARTTLS boolean
     * @return STARTTLS boolean
     */
    public static String getStarttls() {
        return S_starttls;
    }

    /**
     * Sets SMTP Authentication
     * @param auth SMTP Authentication
     */
    public static void setAuth(String auth) {
        S_auth = auth;
    }

    /**
     * Returns SMTP Authentication
     * @return SMTP Authentication
     */
    public static String getAuth() {
        return S_auth;
    }
    
    /**
     * Sets Autostart boolean
     * @param autostart Autostart boolean
     */
    public static void setAutostart(String autostart) {
        S_autostart = autostart;
    }

    /**
     * Returns Autostart boolean
     * @return Autostart boolean
     */
    public static String getAutostart() {
        return S_autostart;
    }
}
