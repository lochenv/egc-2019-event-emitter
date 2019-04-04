package egd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EgdFinderTest {

    @Test
    @Ignore("Do not execute this as it is a single test, goal is to create a nicer class")
    public void testDoubleCheckEgdPin() throws Exception {
        // Load the xml
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document xml = docBuilder.parse(new File("C:\\LocalData\\LOCHENV\\private\\egc\\all-subscribers.xml"));
        System.out.println(xml);

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();

        NodeList nodeList = (NodeList) xPath.evaluate("/pma_xml_export/database/table", xml, XPathConstants.NODESET);

        System.out.println(nodeList.getLength());
        Map<String, String> playersWithPin = new HashMap<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node singleNode = nodeList.item(i);
            String fullName = xPath.evaluate("column[@name='name']", singleNode);
            String[] names = fullName.split(" ");

            final String searchName;
            if (StringUtils.length(names[0]) < 4) {
                searchName = (names[0] + "%20" + names[1]);
            } else {
                searchName = names[0];
            }
            HttpClient client = HttpClients.createDefault();
            try {
                HttpGet get = new HttpGet(
                        String.format("http://www.europeangodatabase.eu/EGD/GetPlayerDataByData.php?lastname=%s&name=%s"
                                , searchName
                                , names[names.length - 1])
                );


                System.out.println(fullName);

                Map<String, Object> result = new ObjectMapper().readValue(
                        client.execute(get).getEntity().getContent(),
                        Map.class);

                ;
                if (result.get("retcode").equals("Ok")) {
                    System.out.println(result.get("retcode"));
                    List<Map<String, Object>> players = (List<Map<String, Object>>) result.get("players");

                    playersWithPin.put(fullName, players.get(0).get("Last_Name") + " " + players.get(0).get("Name") + " " +
                            players.get(0).get("Pin_Player"));
                }

            }catch(Exception e) {
                System.out.println("Cannot process" + fullName);
            }

        }
        System.out.println(playersWithPin);
        // Call the EGD
    }
}