package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
 * <h1>Login_Writer</h1>
 * The Login_Writer Java Class is used to modify and/or add information from the login_information.xml file stored on
 * the users device. Implements XML_Writer interface
 * <p>
 *
 * @author Patrick Crockford
 * @version 1.1
 * <h1>Last Edited</h1>
 * Jeremy Dunnet
 * <h1>References</h1>
 * https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
 * http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm
 */
public class Login_Writer implements XML_Writer {

    /**
     * Information line of the login_information.xml file for initialising due to issues with the required java API we
     * are using.
     */
    private static final String FIRST_LINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";

    /**
     * The Name value of the root node of the login_information.xml file
     */
    private static final String ROOT_NODE = "Login_Information";
    /**
     * The name value of the container node of a account entry in the login_information.xml file.
     */
    private static final String ACCOUNT_NODE = "Account";

    /**
     * Public Method Call to writer information specified to the file references by the File object and returns Boolean
     * if successful or not.
     * This method can only handle the task values of 'Modify', 'New', 'Create', and 'Delete'
     *
     * @param login_File Represents the File Object that references the login_information.xml file to write and or
     *                   modify login
     *                   information to.
     * @param values     The Map containing String pair values, with the key representing the field to write to and
     *                   the value what will be written.
     * @param task       Defines what task should be carried out on the login_information.xml file
     * @return True if the writer is successful otherwise false
     * @throws XML.XML_Writer_File_Layout_Exception if the XML document given by the account_File object doesn't contain
     *                                              the expected XML layout
     * @throws XML.XML_Writer_Failure_Exception     if writing to the XML document encounters an unrecoverable error.
     */
    @Override
    public Boolean Write_File(File login_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_Failure_Exception, XML_Writer_File_Layout_Exception {
        if (values != null && login_File != null && login_File.canWrite() && task != null) {
            Boolean success;
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Quieten_BD(documentBuilder);
                Document document;
                Node root_Node;
                switch (task) {
                    case Modify:
                        document = documentBuilder.parse(login_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Modify(root_Node, values, login_File, document);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Login_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    case New:
                        document = documentBuilder.parse(login_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = New(root_Node, values, login_File, document);

                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Login_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    case Create:
                        success = Create(login_File);
                        break;
                    case Delete:
                        document = documentBuilder.parse(login_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Delete(root_Node, values, login_File, documentBuilder);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Login_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    default:
                        throw new XML_Writer_Failure_Exception("Invalid task given : " + task.toString());
                }
                return success;
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
                throw new XML_Writer_Failure_Exception((String.format("Failed to Write file '%s' due to '%s'", login_File.getName(), ex)));
            }
        } else {
            throw new XML_Writer_Failure_Exception("Failed to execute due to File is Null, or Map is Null or Empty");
        }

    }

    /**
     * This private method functionality is to delete an account from the login_information.xml file, allow for the
     * functionality of account delete to be available to the user. This is achieved by creating a new XML Document
     * object. It looks for the account name attached to the 'XML_Writer.Tags_To_Write.Account_Name' key. If this is
     * found the account entry will be ignored when transferring the entries across, deleting it from the new
     * login_information.xml file written.
     * When the account name is found the check is then ignored to reduce the execution time of the method.
     * If the entry is not found the document writer will not be invoked and false will be returned. This is to
     * minimise the number of unnecessary IO calls within the method.
     * If no entries are left after deletion has occurred the login_information.xml file is re initialised to remove
     * any unwanted code or errors that could be present.
     * <h1>Warning</h1>
     * This method contains nested For loops due to the recursive nature of XML documents.
     *
     * @param root_Node       Object representing the Root Node of the original XML file
     * @param values          Map that contains the name of the account to delete from the login_information.xml file,
     *                        under the key value 'XML_Writer.Tags_To_Write.Account_Name'
     * @param login_File      Represents the File Object that references the login_information.xml file
     * @param documentBuilder Object representing the document builder factor used to create the new XML document to
     *                        transfer the new layout to
     * @return true if the account was found and deleted successfully, otherwise false
     * @throws TransformerException  if an errors occurs from the document builder, SAX parser for transformer
     * @throws FileNotFoundException if the document builder, or Write_To_File() method cant find the file represented
     *                               by the login_File object
     * @throws IOException           if an error occurs when trying to read and write from the login_File object
     */
    private Boolean Delete(Node root_Node, Map<String, String> values, File login_File, DocumentBuilder documentBuilder) throws TransformerException, FileNotFoundException, IOException {
        Document new_Document = documentBuilder.newDocument();
        Element root_element = new_Document.createElement(ROOT_NODE);
        new_Document.appendChild(root_element);
        NodeList old_Information = root_Node.getChildNodes();
        boolean found = false;
        Boolean success = false;
        int entries_Left = 0;
        for (int ii = 0; ii < old_Information.getLength(); ii++) {
            Node old_Account = old_Information.item(ii);
            if (old_Account.getNodeName().equals(ACCOUNT_NODE)) {
                entries_Left++;
                NodeList old_Account_Children = old_Account.getChildNodes();
                Element account_Entry = new_Document.createElement(ACCOUNT_NODE);
                for (int jj = 0; jj < old_Account_Children.getLength(); jj++) {
                    Node temp_Node = old_Account_Children.item(jj);
                    if (Valid_Node(temp_Node)) {
                        Element temp_Element = new_Document.createElement(temp_Node.getNodeName());
                        temp_Element.appendChild(new_Document.createTextNode(temp_Node.getTextContent()));
                        account_Entry.appendChild(temp_Element);
                    }
                }
                //Check if account to change
                if (!found) {
                    for (int jj = 0; jj < old_Account_Children.getLength(); jj++) {
                        Node temp_Node = old_Account_Children.item(jj);
                        if (temp_Node.getNodeName().equals(XML_Writer.Tags_To_Write.Account_Name.toString())) {
                            if (temp_Node.getTextContent().equals(values.get(XML_Writer.Tags_To_Write.Account_Name.toString()))) {
                                found = true;
                                entries_Left--;
                            } else {
                                root_element.appendChild(account_Entry);
                            }
                        }
                    }
                } else {
                    root_element.appendChild(account_Entry);
                }
            }
        }
        if (entries_Left == 0 && found) {
            success = Create(login_File);
        } else if (entries_Left > 0 && found) {
            success = Write_To_File(new_Document, login_File);
        }
        return success;
    }

    /**
     * This private method functionality is to initialise the login_information.xml for used by other writer methods and
     * XML_Reader interface/concrete classes.
     * <h1>Note</h1>
     * Due to current issues with the JAVA API and xml writers the information is manually entered, but due to the
     * simplicity of the initial file structure this results in less code that invoking the xml writer/transformer
     *
     * @param login_File Represents the File Object that references the login_information.xml file to write and or
     *                   modify login
     *                   information to.
     * @return true when it reaches the end of the method as it is successful
     * @throws IOException if an error occurs when trying to read and write from the login_File object
     */
    private Boolean Create(File login_File) throws IOException {
        try (PrintWriter pw = new PrintWriter(login_File)) {
            pw.println(FIRST_LINE);
            pw.println("<" + ROOT_NODE + ">");
            pw.print("</" + ROOT_NODE + ">");
            pw.flush();
        }
        return true;
    }

    /**
     * This private method functionality is to modify the information for a specified account. Currently this method
     * can modify the account name, password value and related account file of a given account. This is achieved by stepping through the
     * document using the SAX parser and document checking all the Account_Name node text values until the required one
     * is found, or the end of the document is found.
     * If the entry is not found the document writer will not be invoked and false will be returned. This is to
     * minimise the number of unnecessary IO calls within the method.
     * <h1>Warning</h1>
     * This method contains nested For loops due to the recursive nature of XML documents.
     *
     * @param root_Node  Object representing the Root Node of the original XML file
     * @param values     The Map containing String pair values, with the key representing the field to write to and
     *                   the value what will be written.
     * @param login_File Represents the File Object that references the login_information.xml file to write and or
     *                   modify login information to.
     * @param document   Object representing the document parsed from the login_information.xml file, which will be
     *                   parsed to the Write_To_File() method if modification of the xml information has occurred
     * @return true if the account was found and modified successfully, otherwise false
     * @throws TransformerException  if an errors occurs from the document builder, SAX parser for transformer
     */
    private Boolean Modify(Node root_Node, Map<String, String> values, File login_File, Document document) throws TransformerException{
        NodeList information_Nodes = root_Node.getChildNodes();
        Boolean found_Account = false;
        Boolean completed = false;
        Boolean success = false;
        int ii = 0;
        int jj;
        while (ii < information_Nodes.getLength() && !completed) {
            jj = 0;
            Node account_Node = information_Nodes.item(ii);
            if (account_Node.getNodeName().equals(ACCOUNT_NODE)) {
                NodeList account_List = account_Node.getChildNodes();
                found_Account = false;
                while (jj < account_List.getLength() && !completed) {
                    Node account_Child = account_List.item(jj);
                    if (account_Child.getNodeName().equals(Tags_To_Write.Account_Name.toString())) {
                        if (account_Child.getTextContent().equals(values.get(Tags_To_Write.Account_Name.toString()))) {
                            found_Account = true;
                            if (values.containsKey(Tags_To_Write.New_Account_Name.toString())) {
                                account_Child.setTextContent(values.get(Tags_To_Write.New_Account_Name.toString()));
                                if (!values.containsKey(Tags_To_Write.Password.toString())) {
                                    completed = true;
                                }
                            }
                        }
                    } else if (account_Child.getNodeName().equals(Tags_To_Write.Password.toString()) && found_Account) {
                        if (values.containsKey(Tags_To_Write.Password.toString())) {
                            account_Child.setTextContent(values.get(Tags_To_Write.Password.toString()));
                            completed = true;
                        }
                    }
                    else if (account_Child.getNodeName().equals(Tags_To_Write.Account_File.toString()) && found_Account) {
                        if (values.containsKey(Tags_To_Write.Account_File.toString())) {
                            account_Child.setTextContent(values.get(Tags_To_Write.Account_File.toString()));
                            completed = true;
                        }
                    }
                    jj++;
                }
            }
            ii++;
        }
        if (found_Account) {
            success = Write_To_File(document, login_File);
        }
        return success;
    }

    /**
     * This private method functionality is to add a new account entry to the login_information.xml file. This is
     * achieved by creating a new element from the document element and adding the information from the values Map
     * to the corresponding nodes. This element is then appended to the root_Node object. The new document is then sent
     * to the 'Write_To_File' method
     *
     * @param root_Node  Object representing the Root Node of the original XML file
     * @param values     Map that contains the name of the account to delete from the login_information.xml file,
     *                   under the key value 'XML_Writer.Tags_To_Write.Account_Name'
     * @param login_File Represents the File Object that references the login_information.xml file to write and or
     *                   modify login
     *                   information to.
     * @param document   Object representing the document parsed from the login_information.xml file, which will be
     *                   parsed to the Write_To_File() method if modification of the xml information has occurred
     * @return true if the account was found and modified successfully.
     * @throws TransformerException         if an errors occurs from the SAX parser or the transformer
     * @throws XML_Writer_Failure_Exception if the required information is not found within the values Map when creating
     *                                      a new account entry
     */
    private Boolean New(Node root_Node, Map<String, String> values, File login_File, Document document) throws TransformerException, XML_Writer_Failure_Exception {
        // Add new section to the end
        if (values.containsKey(Tags_To_Write.Account_Name.toString()) && values.containsKey(Tags_To_Write.Account_Name.toString())) {
            Element account_Node = document.createElement(ACCOUNT_NODE);
            //Account_Name, Password,
            Element name = document.createElement(Tags_To_Write.Account_Name.toString());
            name.appendChild(document.createTextNode(values.get(Tags_To_Write.Account_Name.toString())));
            account_Node.appendChild(name);
            Element password = document.createElement(Tags_To_Write.Password.toString());
            password.appendChild(document.createTextNode(values.get(Tags_To_Write.Password.toString())));
            account_Node.appendChild(password);
            Element accountFile = document.createElement(Tags_To_Write.Account_File.toString());
            accountFile.appendChild(document.createTextNode(values.get(Tags_To_Write.Account_File.toString())));
            account_Node.appendChild(accountFile);
            root_Node.appendChild(account_Node);
            return Write_To_File(document, login_File);
        } else {
            throw new XML_Writer_Failure_Exception("Missing Account_Name and/or Password keys in Values Map");
        }
    }

    /**
     * This private method functionality is to check the name of the node provided against hte list of predetermined
     * valid nodes for the login_information.xml file.
     *
     * @param node The node to be check against valid nodes for the login_information.xml file
     * @return true if the node is valid, otherwise false
     */
    private Boolean Valid_Node(Node node) {
        Boolean valid = false;
        if (node.getNodeName().equals(XML_Reader.Tags_To_Read.Account_Name.toString())) {
            valid = true;
        }
        if (node.getNodeName().equals(XML_Reader.Tags_To_Read.Password.toString())) {
            valid = true;
        }
        return valid;
    }

    /**
     * This private method functionality is to write the xml information contained within the document object to the
     * login_information.xml file.
     *
     * @param login_File Represents the File Object that references the login_information.xml file to be written to
     * @param document   Object representing the xml file to be written to the login_information.xml file
     * @return True if document is successfully written
     * @throws TransformerException  if an errors occurs from the transformer
     */
    private boolean Write_To_File(Document document, File login_File) throws TransformerException{
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(login_File));
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
}
