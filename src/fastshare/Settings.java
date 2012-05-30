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

    protected Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static boolean load() throws Exception {
        boolean error = false;
        File xmlFile = new File("../settings.xml");
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

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("../settings.xml"));
        transformer.transform(source, result);

        FastShare.StatusLabel.reprint();
        return true;
    }

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    public static void setInterface(String iface) {
        S_interface = iface;
        try {
            S_ip = NetInterfaces.getAddressByName(Settings.getInterface()).getHostAddress();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static String getIP() {
        return S_ip;
    }

    public static String getInterface() {
        return S_interface;
    }

    public static void setPort(String port) {
        S_port = port;
    }

    public static String getPort() {
        return S_port;
    }

    public static void setUsername(String username) {
        S_username = username;
    }

    public static String getUsername() {
        return S_username;
    }

    public static void setPassword(String password) {
        S_password = password;
    }

    public static String getPassword() {
        return S_password;
    }

    public static void setSmtphost(String smtphost) {
        S_smtphost = smtphost;
    }

    public static String getSmtphost() {
        return S_smtphost;
    }

    public static void setSmtpport(String smtpport) {
        S_smtpport = smtpport;
    }

    public static String getSmtpport() {
        return S_smtpport;
    }

    public static void setStarttls(String starttls) {
        S_starttls = starttls;
    }

    public static String getStarttls() {
        return S_starttls;
    }

    public static void setAuth(String auth) {
        S_auth = auth;
    }

    public static String getAuth() {
        return S_auth;
    }
}
