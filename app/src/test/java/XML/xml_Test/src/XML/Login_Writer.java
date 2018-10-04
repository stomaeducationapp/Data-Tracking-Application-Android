package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class Login_Writer implements XML_Writer {

    private static final String ROOT_NODE = "Login_information";
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
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    case New:
                        document = documentBuilder.parse(login_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = New(root_Node, values, login_File, document);

                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", login_File.getName()));
                        }
                        break;
                    case Create:
                        success = Create(login_File, documentBuilder);
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

    private Boolean Create(File login_File, DocumentBuilder documentBuilder) throws TransformerException, FileNotFoundException, IOException {
        Document document = documentBuilder.newDocument();
        Element root_element = document.createElement(ROOT_NODE);
        document.appendChild(root_element);
        return Write_To_File(document, login_File);
    }

    private Boolean Modify(Node root_Node, Map<String, String> values, File login_File, Document document) throws TransformerException, FileNotFoundException, IOException {
        NodeList information_Nodes = root_Node.getChildNodes();
        Boolean found_Account = false;
        int ii = 0;
        int jj = 0;
        //// TODO: 30-Sep-18 Move this code into another method as it should be separated out as dealing with a subset of nodes
        while (ii < information_Nodes.getLength() && !found_Account) {
            Node account_Node = information_Nodes.item(ii);
            if (account_Node.getNodeName().equals(ACCOUNT_NODE)) {
                NodeList account_List = account_Node.getChildNodes();
                found_Account = false;
                //Account_Name, Password
                while (jj < account_List.getLength() && !found_Account) {
                    Node account_Child = account_List.item(jj);
                    if (account_Child.getNodeName().equals(Tags_To_Write.Account_Name.toString())) {
                        if (account_Child.getTextContent().equals(values.get(Tags_To_Write.Account_Name.toString()))) {
                            found_Account = true;
                            if (values.containsKey(Tags_To_Write.New_Account_Name.toString())) {
                                account_Child.setTextContent(values.get(Tags_To_Write.New_Account_Name.toString()));
                            }
                        }
                    } else if (account_Child.getNodeName().equals(Tags_To_Write.Password.toString()) && found_Account) {
                        if (values.containsKey(Tags_To_Write.Password.toString())) {
                            account_Child.setTextContent(values.get(Tags_To_Write.Password.toString()));
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
            root_Node.appendChild(account_Node);
            //Account_Name, Password,
            Element name = document.createElement(Tags_To_Write.Account_Name.toString());
            name.appendChild(document.createTextNode(values.get(Tags_To_Write.Account_Name.toString())));
            account_Node.appendChild(name);
            Element password = document.createElement(Tags_To_Write.Password.toString());
            password.appendChild(document.createTextNode(values.get(Tags_To_Write.Password.toString())));
            account_Node.appendChild(password);
            return Write_To_File(document, login_File);
        } else {
            throw new XML_Writer_Failure_Exception("Missing Account_Name and/or Password keys in Values Map");
        }
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
