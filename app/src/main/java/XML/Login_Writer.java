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

public class Login_Writer implements XML_Writer {

    private static final String FIRST_LINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
    private static final String ROOT_NODE = "Login_Information";
    private static final String ACCOUNT_NODE = "Account";

    /**
     * @param login_File
     * @param task
     * @param values     Map with string pair values, where the Keys correlate
     *                   to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     * @throws XML.XML_Writer_Failure_Exception
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
                        //todo need to add a delete method for removing an account from login information, will be parsing over the document into a new document and ignoring when account name found
                        document = documentBuilder.parse(login_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Delete(root_Node, values, login_File, documentBuilder);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Login_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    default:
                        throw new XML_Writer_Failure_Exception("Invalid task given must be either Modify or Create");
                }
                return success;
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
                throw new XML_Writer_Failure_Exception((String.format("Failed to Write file '%s' due to '%s'", login_File.getName(), ex)));
            }
        } else {
            throw new XML_Writer_Failure_Exception("Failed to execute due to File is Null, or Map is Null or Empty");
        }

    }

    private Boolean Delete(Node root_Node, Map<String, String> values, File login_File, DocumentBuilder documentBuilder) throws TransformerException, FileNotFoundException, IOException {
        Document new_Document = documentBuilder.newDocument();
        Element root_element = new_Document.createElement(ROOT_NODE);
        new_Document.appendChild(root_element);
        NodeList old_Information = root_Node.getChildNodes();
        boolean found = false;
        Boolean success;
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
        if (entries_Left == 0) {
            success = Create(login_File);
        } else {
            success = Write_To_File(new_Document, login_File);
        }
        return success;
    }

    private Boolean Create(File login_File) throws TransformerException, FileNotFoundException, IOException {
        PrintWriter pw = new PrintWriter(login_File);
        pw.println(FIRST_LINE);
        pw.print("<Login_Information>");
        pw.print("</Login_Information>");
        pw.flush();
        pw.close();
        return true;
    }

    private Boolean Modify(Node root_Node, Map<String, String> values, File login_File, Document document) throws TransformerException, FileNotFoundException, IOException {
        NodeList information_Nodes = root_Node.getChildNodes();
        Boolean found_Account = false;
        Boolean completed = false;
        int ii = 0;
        int jj = 0;
        //// TODO: 30-Sep-18 Move this code into another method as it should be separated out as dealing with a subset of nodes
        while (ii < information_Nodes.getLength() && !completed) {
            jj = 0;
            Node account_Node = information_Nodes.item(ii);
            if (account_Node.getNodeName().equals(ACCOUNT_NODE)) {
                NodeList account_List = account_Node.getChildNodes();
                found_Account = false;

                //Account_Name, Password
                while (jj < account_List.getLength() && !completed) {
                    Node account_Child = account_List.item(jj);
                    if (account_Child.getNodeName().equals(Tags_To_Write.Account_Name.toString())) {
                        System.out.println("at node " + account_Child.getNodeName());
                        System.out.println("Text value = " + account_Child.getTextContent());
                        if (account_Child.getTextContent().equals(values.get(Tags_To_Write.Account_Name.toString()))) {
                            found_Account = true;
                            System.out.println("Found Account");
                            if (values.containsKey(Tags_To_Write.New_Account_Name.toString())) {
                                account_Child.setTextContent(values.get(Tags_To_Write.New_Account_Name.toString()));
                                System.out.println("Changed Name");
                                if (!values.containsKey(Tags_To_Write.Password.toString())) {
                                    completed = true;
                                }
                            }
                        }
                    } else if (account_Child.getNodeName().equals(Tags_To_Write.Password.toString()) && found_Account) {
                        System.out.println(account_Child.getNodeName());
                        if (values.containsKey(Tags_To_Write.Password.toString())) {
                            System.out.println("Changing PW");
                            account_Child.setTextContent(values.get(Tags_To_Write.Password.toString()));
                            completed = true;
                        }
                    }
                    jj++;
                }
            }
            ii++;
        }
        return Write_To_File(document, login_File);
    }

    private Boolean New(Node root_Node, Map<String, String> values, File login_File, Document document) throws TransformerException, FileNotFoundException, IOException, XML_Writer_Failure_Exception {
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
            root_Node.appendChild(account_Node);
            return Write_To_File(document, login_File);
        } else {
            throw new XML_Writer_Failure_Exception("Missing Account_Name and/or Password keys in Values Map");
        }
    }

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

    private boolean Write_To_File(Document document, File login_File) throws TransformerException, FileNotFoundException, IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(login_File));
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
