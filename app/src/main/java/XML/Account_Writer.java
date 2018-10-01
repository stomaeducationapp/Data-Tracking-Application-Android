package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * <h1>NOTES</h1>
 * Method Parameter for Write_File: Tags_To_Write task is not used but is there for future functionality if required in
 * the future.
 */
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
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception {
        if (values == null || values.isEmpty() || account_File == null) {
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(account_File);
                Node account = document.getFirstChild();
                if (account.getNodeName().equals(ROOT_NODE)) {
                    Node account_Information = document.getElementsByTagName(ACCOUNT_INFORMATION_NODE).item(0);
                    if (account_Information != null) {
                        NodeList information_Nodes = account_Information.getChildNodes();
                        Modify_Node_Values(information_Nodes, values);
                        Boolean success = Write_To_File(document, account_File);
                        return success;
                    } else {
                        throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_Information' XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
                    }
                } else {
                    throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Account_File' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", account_File.getName()));
                }
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
                throw new XML_Writer_Failure_Exception((String.format("Failed to Write file '%s' due to '%s'", account_File.getName(), ex)));
            }
        } else {
            throw new XML_Writer_Failure_Exception("Failed to execute due to File is Null, or Map is Null or Empty");
        }
    }

    private void Modify_Node_Values(NodeList information_Nodes, Map<String, String> values) throws XML_Writer_File_Layout_Exception {
        for (int ii = 0; ii < information_Nodes.getLength(); ii++) {
            Node node = information_Nodes.item(ii);
            String node_Name = node.getNodeName();
            // TODO: 27-Sep-18 Create Class XML_Writer_Invalid_Enum_Exception
            if (node_Name.equals(Tags_To_Write.Gamification.toString())) {
                if (values.containsKey(node_Name)) node.setTextContent(values.get(node_Name));
            } else if (node_Name.equals(Tags_To_Write.Notification.toString())) {
                if (values.containsKey(node_Name)) node.setTextContent(values.get(node_Name));
            } else if (node_Name.equals(Tags_To_Write.State.toString())) {
                if (values.containsKey(node_Name)) node.setTextContent(values.get(node_Name));
            } else if (node_Name.equals(Tags_To_Write.Name.toString())) {
                if (values.containsKey(node_Name)) node.setTextContent(values.get(node_Name));
            } else if (node_Name.equals(Tags_To_Write.Last_Daily_Review_Date.toString())) {
                if (values.containsKey(node_Name)) node.setTextContent(values.get(node_Name));
            } else {
                throw new XML_Writer_File_Layout_Exception(String.format("Invalid node '%s' found in file. WARNING file maybe modified from an external program and contain malicious code", node_Name));
            }
        }
    }

    private boolean Write_To_File(Document document, File account_File) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(account_File);
        transformer.transform(source, result);
        return true;
    }
}
