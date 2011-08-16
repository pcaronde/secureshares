package ro.panzo.secureshares.util;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IpInfoUtil {

    private Logger log = Logger.getLogger(IpInfoUtil.class);
    private static IpInfoUtil ourInstance = new IpInfoUtil();

    private final static String key = "11b09a6de5eb9d60ee69a6a83b8a2e605bb54246f05df614afb56bad0fc1422f";
    private final static String host = "http://api.ipinfodb.com";
    private final static String path = "/v3/ip-country/";

    public static IpInfoUtil getInstance() {
        return ourInstance;
    }

    private IpInfoUtil() {
    }

    public String getCountryCodeFromIp(String ipAddress){
        String countryCode = "GB";
        if(ipAddress != null && !"127.0.0.1".equals(ipAddress) && !"0:0:0:0:0:0:0:1".equals(ipAddress)){
            try{
                log.debug("Trying for: " + ipAddress);
                URL url = new URL(host + path + "?key=" + key + "&format=xml&ip=" + ipAddress);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();  // 200, 404, etc
                String responseMsg = connection.getResponseMessage(); // OK, Forbidden, etc
                if(responseCode == 200 && "OK".equals(responseMsg)) {
                    countryCode = parseCountryCode(connection);
                    log.debug("CountryCode for: " + ipAddress + " is " + countryCode);
                }
            } catch(Exception ex){
                log.error(ex.getMessage(), ex);
            }
        } else {
            log.debug("Return default country code for: " + ipAddress);
        }
        return countryCode;
    }

    private String parseCountryCode(HttpURLConnection connection) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        String countryCode = null;
        XPathFactory xPathfactory = XPathFactory.newInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(connection.getInputStream());
        XPath xpath = xPathfactory.newXPath();
        XPathExpression xPathExpression = xpath.compile("/Response/statusCode");
        String statusCode = (String) xPathExpression.evaluate(doc, XPathConstants.STRING);
        if("OK".equals(statusCode)){
            xPathExpression = xpath.compile("/Response/countryCode");
            countryCode = (String) xPathExpression.evaluate(doc, XPathConstants.STRING);
        }
        return countryCode;
    }
}
