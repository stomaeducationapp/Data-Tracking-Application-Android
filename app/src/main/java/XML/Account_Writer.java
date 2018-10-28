package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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

/**
 * <h1>Account_Writer</h1>
 * The Account_Writer Java Class is used to modify and/or add information from the medical_information.xml file stored
 * on the users device. Implements XML_Writer interface
 * <p>
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 17-Oct-2018
 * Patrick Crockford
 * <h1>References</h1>
 * https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
 * http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm
 */
public class Account_Writer implements XML_Writer {

    private static final String REGEX_FOR_DATE_TIME = "-";
    private static final String ROOT_NODE = "Account_File";
    private static final String ACCOUNT_INFORMATION_NODE = "Account_Information";
    private static final String DEFAULT_NODE_ENTRY = "No Entry";

    /**
     * Public Method Call to writer information specified to the file references by the File object and returns Boolean
     * if successful or not.
     * This method can only handle the task values of 'Modify' and 'Create'
     *
     * @param account_File Represents the File Object that references the account_information.xml file
     * @param values       The Map containing String pair values, with the key representing the field to write to and
     *                     the value what will be written.
     * @param task         Defines what task should be carried out on the account_information.xml file
     * @return true if the method was successful
     * @throws XML.XML_Writer_File_Layout_Exception if the XML document given by the account_File object doesn't contain
     *                                              the expected XML layout
     * @throws XML.XML_Writer_Failure_Exception     if writing to the XML document encounters an unrecoverable error.
     */
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception {
        Boolean success;
        if (values != null && account_File != null && account_File.canWrite() && task != null) {
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                //This section is to make the Document Builder Quiet as it outputs Errors on the err output even when all are within valid try catch
                Quieten_BD(documentBuilder);
                switch (task) {
                    case Modify:
                        Document document = documentBuilder.parse(account_File);
                        Node root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Modify(root_Node, values, account_File, document);
                            Write_To_File(document, account_File);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
                        }
                        break;
                    case Create:
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

    /**
     * This private method functionality is to modify the account information in the account_information.xml file. To
     * achieve this functionality the SAX parser steps through looking for the account_Information node and then
     * stepping into that. All the children of the account information node are then checked against the keys within the
     * values Map. If the map contains the keys the attached information is then put into the text value of the node.
     * If no entries were modified the document writer will not be invoked and false will be returned. This is to
     * minimise the number of unnecessary IO calls within the method.
     * <h1>Warning</h1>
     * This method contains nested For loops due to the recursive nature of XML documents.
     *
     * @param root_Node    Object representing the Root Node of the original XML file
     * @param values       The Map containing String pair values, with the key representing the field to write to and
     *                     the value what will be written.
     * @param account_File Represents the File Object that references the account_information.xml file
     * @param document     Object representing the document parsed from the account_information.xml file, which will be
     *                     parsed to the Write_To_File() method if modification of the xml information has occurred
     * @return true if the method was successful
     * @throws XML_Writer_File_Layout_Exception if the account information node isn't parsed from the
     *                                          account_information.xml file
     * @throws TransformerException             if an errors occurs from the document builder, SAX parser for
     */
    private Boolean Modify(Node root_Node, Map<String, String> values, File account_File, Document document) throws XML_Writer_File_Layout_Exception, TransformerException {
        NodeList account_List = root_Node.getChildNodes();
        Boolean found = false;
        for (int jj = 0; jj < account_List.getLength(); jj++) {
            Node temp = account_List.item(jj);
            if (temp.getNodeName().equals(ACCOUNT_INFORMATION_NODE)) {
                found = true;
                NodeList information_Nodes = temp.getChildNodes();
                for (int ii = 0; ii < information_Nodes.getLength(); ii++) {
                    Node node = information_Nodes.item(ii);
                    String node_Name = node.getNodeName();
                    if (node_Name.equals(Tags_To_Write.Gamification.toString())) {
                        if (values.containsKey(Tags_To_Write.Gamification.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Gamification.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Notification.toString())) {
                        if (values.containsKey(Tags_To_Write.Notification.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Notification.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.State.toString())) {
                        if (values.containsKey(Tags_To_Write.State.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.State.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Name.toString())) {
                        if (values.containsKey(Tags_To_Write.Name.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Name.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                        if (values.containsKey(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Last_Daily_Review_Date.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Export_Settings.toString())) {
                        if (values.containsKey(Tags_To_Write.Export_Settings.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Export_Settings.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Last_Export_Date.toString())) {
                        if (values.containsKey(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Last_Daily_Review_Date.toString()));
                            found = true;
                        }
                    } else if (node_Name.equals(Tags_To_Write.Last_Export_Date.toString())) {
                        if (values.containsKey(Tags_To_Write.Last_Export_Date.toString())) {
                            node.setTextContent(Get_Current_Date_Time());
                            found = true;
                        }
                    }
                }
            }
        }
        if (found) {
            return Write_To_File(document, account_File);
        } else {
            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Account_Information XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
        }
    }

    /**
     * This private method functionality is to create the XML layout of the account_information.xml file. This create
     * is
     * also done in conjunction with adding in values, as the create will correspond with the creation of the account
     * so
     * all relevant information will be entered at the same time, defined by program functionality. To allow for this
     * functionality a new XML document object is created and the required XML information and root node are generated.
     * the account information node is then generated. The children of the account information node are generated and
     * the values in the values Map corresponding the the given key are added into the text information other node. This
     * document is then sent to the Write_To_File() method;
     *
     * @param values          The Map containing String pair values, with the key representing the field to write to and
     *                        the value what will be written.
     * @param account_File    Represents the File Object that references the account_information.xml file
     * @param documentBuilder Object representing the document builder factor used to create the new XML document to
     *                        transfer the new layout to
     * @return true if the method was successful
     * @throws TransformerException if an errors occurs from the document builder, SAX parser for transformer
     */
    private Boolean Create(Map<String, String> values, File account_File, DocumentBuilder documentBuilder) throws TransformerException {
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
        account_Information.appendChild(name);
        Element state = document.createElement(Tags_To_Write.State.toString());
        if (values.containsKey(Tags_To_Write.State.toString())) {
            state.appendChild(document.createTextNode(values.get(Tags_To_Write.State.toString())));
        } else {
            state.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        account_Information.appendChild(state);

        Element last_Daily_Review_Date = document.createElement(Tags_To_Write.Last_Daily_Review_Date.toString());
        if (values.containsKey(Tags_To_Write.Last_Daily_Review_Date.toString())) {
            last_Daily_Review_Date.appendChild(document.createTextNode(values.get(Tags_To_Write.Last_Daily_Review_Date.toString())));
        } else {
            last_Daily_Review_Date.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        account_Information.appendChild(last_Daily_Review_Date);

        Element export_Date = document.createElement(Tags_To_Write.Gamification.toString());
        export_Date.appendChild(document.createTextNode(Get_Current_Date_Time()));
        account_Information.appendChild(export_Date);

        Element notification = document.createElement(Tags_To_Write.Notification.toString());
        if (values.containsKey(Tags_To_Write.Notification.toString())) {
            notification.appendChild(document.createTextNode(values.get(Tags_To_Write.Notification.toString())));
        } else {
            notification.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        account_Information.appendChild(notification);

        Element export_Settings = document.createElement(Tags_To_Write.Export_Settings.toString());
        if (values.containsKey(Tags_To_Write.Export_Settings.toString())) {
            export_Settings.appendChild(document.createTextNode(values.get(Tags_To_Write.Export_Settings.toString())));
        } else {
            export_Settings.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        account_Information.appendChild(export_Settings);


        Element gamification = document.createElement(Tags_To_Write.Gamification.toString());
        if (values.containsKey(Tags_To_Write.Gamification.toString())) {
            gamification.appendChild(document.createTextNode(values.get(Tags_To_Write.Gamification.toString())));
        } else {
            gamification.appendChild(document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        account_Information.appendChild(gamification);

        return Write_To_File(document, account_File);
    }

    /**
     * This private method functionality is to write the xml information contained within the document object to the
     * account_information.xml file.
     *
     * @param account_File Represents the File Object that references the account_information.xml file to be written to
     * @param document     Object representing the xml file to be written to the account_information.xml file
     * @return True if document is successfully written
     * @throws TransformerException if an errors occurs from the transformer
     */
    private boolean Write_To_File(Document document, File account_File) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(account_File));
        return true;
    }

    /**
     * This private method functionality is to remove the verboseness of the Document builder, as it pushes messages to
     * the err output channel when and error occurs. This occurs within valid try-catch blocks
     *
     * @param documentBuilder Representing the document builder object.
     */
    private void Quieten_BD(DocumentBuilder documentBuilder) {
        documentBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException sax) throws SAXException {
                throw new SAXException(sax);
            }

            @Override
            public void error(SAXParseException sax) throws SAXException {
                throw new SAXException(sax);
            }

            @Override
            public void fatalError(SAXParseException sax) throws SAXException {
                throw new SAXException(sax);
            }
        });
    }

    /**
     * This private method functionality generates the current date time of when the method is called.
     * This Information is setup as (hour)-(day)-(month)-(year)
     *
     * @return String containing the for current date time in the pre determined format
     */
    private String Get_Current_Date_Time() {
        Calendar calender = Calendar.getInstance();
        return String.valueOf(calender.get(Calendar.HOUR_OF_DAY)) + REGEX_FOR_DATE_TIME + calender.get(Calendar.DAY_OF_MONTH) + REGEX_FOR_DATE_TIME + (calender.get(Calendar.MONTH) + 1) + REGEX_FOR_DATE_TIME + calender.get(Calendar.YEAR);
    }

}
