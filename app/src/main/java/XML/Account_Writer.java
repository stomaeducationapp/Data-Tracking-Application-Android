package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


//Note Have to use Document Builder and DOM as need to read all existing first and add in new then overwrite all
// http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm

class Account_Writer implements XML_Writer {
    private static final String ROOT_NODE = "Account_File";
    private static final String ACCOUNT_INFORMATION_NODE = "Account_Information";

    /**
     * @param account_File Represents the FileOutputStream Object used to write users data to the specified file stored
     *                     on the device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     */
    // TODO: 27-Sep-18 Will need to catch the exceptions being thrown and throw a custom exception as most of these should never occur. If they do then there is a big error somewhere in the program running
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values) {
        Set<String> keys = values.keySet();
        Boolean success = false;
        try {//Get Keys of Map
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(account_File);
            Node account = document.getFirstChild();
            if (account.getNodeName().equals(ROOT_NODE)) {
                Node account_Information = document.getElementsByTagName(ACCOUNT_INFORMATION_NODE).item(0);
                if (account_Information != null) {
                    //get all nodes within the account_information and loop through modifying as required
                    NodeList information_Nodes = account_Information.getChildNodes();
                    for (int ii = 0; ii < information_Nodes.getLength(); ii++) {
                        Node node = information_Nodes.item(ii);
                        String node_Name = node.getNodeName();
                        //check if node is one to be changed
                        if (keys.contains(node_Name)) {
                            //Using an if/else if chain as enum needs to be converted to String values and cannot be done in Switch due to constant expressions required
                            //Gamification, Notification, State, Name, Last_Daily_Review_Date,
                            //Account Tags Only!!!! anything else will throw XML_Writer_Invalid_Enum_Exception
                            // TODO: 27-Sep-18 Create Class XML_Writer_Invalid_Enum_Exception
                            if (node_Name.equals(Tags_To_Write.Gamification.toString())) {
                                node.setTextContent(values.get(node_Name));
                            } else if (node_Name.equals(Tags_To_Write.Notification.toString())) {
                                node.setTextContent(values.get(node_Name));
                            } else if (node_Name.equals(Tags_To_Write.State.toString())) {
                                node.setTextContent(values.get(node_Name));
                            } else if (node_Name.equals(Tags_To_Write.Name.toString())) {
                                node.setTextContent(values.get(node_Name));
                            } else if (node_Name.equals(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                                node.setTextContent(values.get(node_Name));
                            } else {
                                //throw new XML_Writer_Invalid_Enum_Exception("Incorrect Enum given to Write_File, please read XML_Writer Documentation about the Tags_To_Write enum values");
                            }
                        }
                    }
                    //Write Back to File
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(account_File);
                    transformer.transform(source, result);
                } else {
                    //ERROR
                    // TODO: 27-Sep-18 need to handle error as it is the wrong/corrupted file!!!
                }
            } else {
                //ERROR
                // TODO: 27-Sep-18 need to handle error as it is the wrong/corrupted file!!!
            }
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
            // TODO: 27-Sep-18 will need to throw a custom exception. May need to split the catches us based on what type of error has occured
        }
        return success;
    }
}
