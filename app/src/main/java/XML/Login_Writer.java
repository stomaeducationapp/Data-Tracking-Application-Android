package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

public class Login_Writer implements XML_Writer {
    private static final String ROOT_NODE = "Login_information";
    private static final String ACCOUNT_NODE = "Account";

    /**
     * @param account_File Represents the File Object used to write users data to the specified file stored on the
     *                     device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     */
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Set<String> keys = values.keySet();
        Boolean success = false;
        try {//Get Keys of Map
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(account_File);
            Node root_Node = document.getFirstChild();
            if (root_Node.getNodeName().equals(ROOT_NODE)) {
                // NEED TO LOOP AND FIND EACH ACCOUNT AND CHECK THE NAME AGAINST THE NAME GIVEN IN THE MAP, IF DOESN'T EXIST ADD A NEW ONE TO IT.
                //MAY NEED TO LOOK AT A NEW TAG -MODIFY OR CREATE AND HAVE 2 DIFFERENT BITS OF LOGIC
                if (keys.contains(Tags_To_Write.Modify.toString())) { // Find Name and modify as needed

                } else if (keys.contains(Tags_To_Write.New.toString())) {// Add new section to the end
                    if (keys.contains(Tags_To_Write.Account_Name.toString()) && keys.contains(Tags_To_Write.Password.toString())) {//Need to have both name and password for this to be valid
                        Element account = document.createElement(ACCOUNT_NODE);
                        root_Node.appendChild(account);
                        //Account_Name, Password,
                        Element name = document.createElement(Tags_To_Write.Account_Name.toString());
                        name.appendChild(document.createTextNode(values.get(Tags_To_Write.Account_Name.toString())));
                        account.appendChild(name);
                        Element password = document.createElement(Tags_To_Write.Password.toString());
                        password.appendChild(document.createTextNode(values.get(Tags_To_Write.Password.toString())));
                        account.appendChild(password);
                    } else {
                        //ERROR AS NEED BOTH NAME AND PASSWORD throw exception!!!
                    }
                } else {
                    //ERROR
                }
                //Write Back to File
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(account_File);
                transformer.transform(source, result);

                /*
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

                } else {
                    //ERROR
                    // TODO: 27-Sep-18 need to handle error as it is the wrong/corrupted file!!!
                }*/
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

