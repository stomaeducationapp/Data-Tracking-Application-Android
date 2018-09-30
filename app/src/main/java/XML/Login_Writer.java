package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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

public class Login_Writer implements XML_Writer {
    private static final String ROOT_NODE = "Login_information";
    private static final String ACCOUNT_NODE = "Account";
    private static final int ACCOUNT_NODE_INDEX = 0;
    private static final int PASSWORD_NODE_INDEX = 1;

    /**
     * @param account_File Represents the File Object used to write users data to the specified file stored on the
     *                     device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     */
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Set<String> keys = values.keySet();
        Boolean success = false;
        try {//Get Keys of Map
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(account_File);
            Node root_Node = document.getFirstChild();
            if (root_Node.getNodeName().equals(ROOT_NODE)) {
                //// TODO: 30-Sep-18 Move this code into another method as it should be separated out as dealing with  modifying
                if (task == Tags_To_Write.Modify) { // Find Name and modify as needed
                    //Search for each account name and check if it is the one specified
                    NodeList information_Nodes = root_Node.getChildNodes();
                    int ii = 0;
                    Boolean found = false;
                    //// TODO: 30-Sep-18 Move this code into another method as it should be separated out as dealing with a subset of nodes
                    while (ii < information_Nodes.getLength() && !found) {
                        Node node = information_Nodes.item(ii);
                        NodeList account_List = node.getChildNodes();
                        Node account_Name = account_List.item(ACCOUNT_NODE_INDEX);
                        String node_Name = account_Name.getNodeName();
                        //Check if Account name
                        if (node_Name.equals(Tags_To_Write.Account_Name.toString())) {
                            //if it is get the name stored
                            if (account_Name.getTextContent().equals(values.get(node_Name))) {
                                Node password = information_Nodes.item(PASSWORD_NODE_INDEX);
                                node_Name = password.getNodeName();
                                if (node_Name.equals(Tags_To_Write.Password.toString())) {
                                    password.setTextContent(values.get(node_Name));
                                    found = true;
                                    success = true;
                                }
                            } else {
                                //Throw Error File is corrupt as it is missing password node
                            }
                        }
                        ii++;
                    }
                    //// TODO: 30-Sep-18 Move this code into another method as it should be separated out as dealing with adding and not modifying
                    // TODO: 30-Sep-18 also need to change the check to an ifelse chain or switch and only put in valid information. will still throw an error if invalid is found but removes the boolean if check, will also remove the keys
                } else if (task == Tags_To_Write.New) {// Add new section to the end
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
                        success = true;
                    } else {
                        //ERROR AS NEED BOTH NAME AND PASSWORD throw exception!!!
                    }
                } else {
                    //ERROR
                }
                //Write Back to File
                //todo move this to a separate method as to do with writing to file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(account_File);
                transformer.transform(source, result);
            } else {
                //ERROR
                // TODO: 27-Sep-18 need to handle error as it is the wrong/corrupted file!!!
            }
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex)

        {
            // TODO: 27-Sep-18 will need to throw a custom exception. May need to split the catches us based on what type of error has occured
        }
        return success;
    }
}

