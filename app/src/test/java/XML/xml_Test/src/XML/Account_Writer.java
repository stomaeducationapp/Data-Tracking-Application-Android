package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 * <h1>NOTES</h1>
 * Method Parameter for Write_File: Tags_To_Write task is not used but is there
 * for future functionality if required in the future.
 */
//Note Have to use Document Builder and DOM as need to read all existing first and add in new then overwrite all
// http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm
public class Account_Writer implements XML_Writer {

    private static final String ROOT_NODE = "Account_File";
    private static final String ACCOUNT_INFORMATION_NODE = "Account_Information";
    private static final String DEFAULT_NODE_ENTRY = "No Entry";

    /**
     * @param account_File Represents the FileOutputStream Object used to write
     *                     users data to the specified file stored on the device
     * @param values       Map with string pair values, where the Keys correlate
     *                     to the Enum Tags_To_Write values
     * @param task
     * @return True if successful otherwise false
     * @throws XML.XML_Writer_File_Layout_Exception
     * @throws XML.XML_Writer_Failure_Exception
     */
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception {
        Boolean success = false;
        if (values != null && account_File != null && account_File.canWrite() && task != null) {
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //This section is to make the Document Builder Quiet as it outputs Errors on the err output even when all are within valid try catch
                Quieten_BD(documentBuilder);

                switch (task) {
                    case Modify:
                        System.out.println("Modify");
                        Document document = documentBuilder.parse(account_File);
                        Node root_Node = document.getFirstChild();
                        System.out.println(root_Node.getNodeName());
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Modify(root_Node, values, account_File, document);
                            Write_To_File(document, account_File);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
                        }
                        break;
                    case Create:
                        System.out.println("Create");
                        success = Create(values, account_File, documentBuilder);
                        break;
                    default:
                        throw new XML_Writer_Failure_Exception("Invalid task given must be either Modify or Create");
                }
                return success;

            } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
                throw new XML_Writer_Failure_Exception((String.format("Failed to Write file '%s' due to '%s'", account_File.getName(), ex)));
            }
        } else {
            throw new XML_Writer_Failure_Exception("Failed to execute due to File is Null, or Map is Null or Empty");
        }
    }

    private Boolean Modify(Node root_Node, Map<String, String> values, File account_File, Document doc) throws XML_Writer_File_Layout_Exception, TransformerException, FileNotFoundException, IOException {
        NodeList account_List = root_Node.getChildNodes();
        Boolean found = true;
        for (int jj = 0; jj < account_List.getLength(); jj++) {
            Node temp = account_List.item(jj);
            if (temp.getNodeName().equals(ACCOUNT_INFORMATION_NODE)) {
                NodeList information_Nodes = temp.getChildNodes();
                for (int ii = 0; ii < information_Nodes.getLength(); ii++) {
                    Node node = information_Nodes.item(ii);
                    String node_Name = node.getNodeName();
                    System.out.println(ii + "-" + node_Name);
                    // TODO: 27-Sep-18 Create Class XML_Writer_Invalid_Enum_Exception
                    if (node_Name.equals(Tags_To_Write.Gamification.toString())) {
                        if (values.containsKey(Tags_To_Write.Gamification.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Gamification.toString()));
                        }
                    } else if (node_Name.equals(Tags_To_Write.Notification.toString())) {
                        if (values.containsKey(Tags_To_Write.Notification.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Notification.toString()));
                        }
                    } else if (node_Name.equals(Tags_To_Write.State.toString())) {
                        if (values.containsKey(Tags_To_Write.State.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.State.toString()));
                        }
                    } else if (node_Name.equals(Tags_To_Write.Name.toString())) {
                        if (values.containsKey(Tags_To_Write.Name.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Name.toString()));
                        }
                    } else if (node_Name.equals(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                        if (values.containsKey(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Last_Daily_Review_Date.toString()));
                        }
                    }
                }
            }
        }

        if (!found) {
            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_Information' XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
        }

        return Write_To_File(doc, account_File);
    }

    private Boolean Create(Map<String, String> values, File account_File, DocumentBuilder documentBuilder) throws TransformerException, FileNotFoundException, IOException {
        Document document = documentBuilder.newDocument();
        Element root_element = document.createElement(ROOT_NODE);
        Element account_Information = document.createElement(ACCOUNT_INFORMATION_NODE);
        document.appendChild(root_element);
        root_element.appendChild(account_Information);
        //Add all nodes to it now and add any values given
        //Name, Gamification, Notification, State, Last_Daily_Review_Date
        Element name = document.createElement(Tags_To_Write.Name.toString());
        if (values.containsKey(Tags_To_Write.Name.toString())) {
            name.appendChild(document.createTextNode(values.get(Tags_To_Write.Name.toString())));
        } else {
            name.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        System.out.println("Name");
        account_Information.appendChild(name);

        Element state = document.createElement(Tags_To_Write.State.toString());
        if (values.containsKey(Tags_To_Write.State.toString())) {
            System.out.println("t");
            state.appendChild(document.createTextNode(values.get(Tags_To_Write.State.toString())));
        } else {
            System.out.println("f");
            state.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        System.out.println("State");
        account_Information.appendChild(state);

        Element last_Daily_Review_Date = document.createElement(Tags_To_Write.Last_Daily_Review_Date.toString());
        if (values.containsKey(Tags_To_Write.Last_Daily_Review_Date.toString())) {
            System.out.println("t");
            last_Daily_Review_Date.appendChild(document.createTextNode(values.get(Tags_To_Write.Last_Daily_Review_Date.toString())));
        } else {
            System.out.println("f");
            last_Daily_Review_Date.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        System.out.println("Last_Daily_Review_Date");
        account_Information.appendChild(last_Daily_Review_Date);

        Element notification = document.createElement(Tags_To_Write.Notification.toString());
        if (values.containsKey(Tags_To_Write.Notification.toString())) {
            System.out.println("t");
            notification.appendChild(document.createTextNode(values.get(Tags_To_Write.Notification.toString())));
        } else {
            System.out.println("f");
            notification.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        System.out.println("Notification");
        account_Information.appendChild(notification);

        Element gamification = document.createElement(Tags_To_Write.Gamification.toString());
        if (values.containsKey(Tags_To_Write.Gamification.toString())) {
            System.out.println("t");
            gamification.appendChild(document.createTextNode(values.get(Tags_To_Write.Gamification.toString())));
        } else {
            System.out.println(gamification.getNodeName());
            gamification.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        System.out.println("Gamification");
        account_Information.appendChild(gamification);
        Boolean success = Write_To_File(document, account_File);
        return success;
    }

    private boolean Write_To_File(Document document, File account_File) throws TransformerException, FileNotFoundException, IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(account_File));
        return true;
    }

    private void Quieten_BD(DocumentBuilder documentBuilder) {
        documentBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException saxpe) throws SAXException {
            }

            @Override
            public void error(SAXParseException saxpe) throws SAXException {
            }

            @Override
            public void fatalError(SAXParseException saxpe) throws SAXException {
            }
        });
    }

}
