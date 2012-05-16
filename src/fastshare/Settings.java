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

    protected Settings() {
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public static boolean load() throws Exception {
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
                    setInterface(getTagValue("interface", eElement));
                    setPort(getTagValue("port", eElement));
                }
            }
        }
        
        FastShare.StatusLabel.reprint();
        return true;
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

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("../settings.xml"));
        transformer.transform(source, result);
        
        FastShare.StatusLabel.reprint();
        return true;
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
        } catch (Exception e) { }
    }
    
    public static String getIP(){
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
}
