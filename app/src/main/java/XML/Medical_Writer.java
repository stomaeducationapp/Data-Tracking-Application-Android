package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
 * <h1>NOTE</h1>
 * Modify will only modify the first entry in the document as the functionality has been created for the medical state
 * calculator primarily
 */


public class Medical_Writer implements XML_Writer {
    private static final String ROOT_NODE = "Medical_Information";
    private static final String MEDICAL_NODE = "Medical_Entry";
    private static final int MEDICAL_NODE_INDEX = 0;

    /**
     * @param account_File Represents the File Object used to write users data to the specified file stored on the
     *                     device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     */

    //For creating a new document
    //https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
    //then merge existing one from file into it!!!! for adding a new entry
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws IOException, ParserConfigurationException, SAXException {
        Boolean success = false;
        try {//Get Keys of Map
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(account_File);
            Node root_Node = document.getFirstChild();
            if (root_Node.getNodeName().equals(ROOT_NODE)) {
                //check which task is to be done
                if (task == Tags_To_Write.Modify) {
                    success = Modify(root_Node, values, account_File);
                } else if (task == Tags_To_Write.New) {
                    values.remove(Tags_To_Write.New.toString());
                    success = Add(root_Node, values, account_File);
                } else if (task == Tags_To_Write.Export) {
                    values.remove(Tags_To_Write.Export.toString());
                    success = Export(root_Node, values, account_File);
                } else {
                    //ERROR
                }
                //// TODO: 30-Sep-18 put this in a new method that takes a Document, as Add will have a separate document
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(account_File);
                transformer.transform(source, result);

            } else {
                //ERROR
            }

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
            // TODO: 27-Sep-18 will need to throw a custom exception. May need to split the catches us based on what type of error has occurred
        }
        return success;
    }

    private Boolean Modify(Node root_Node, Map<String, String> values, File account_File) {
        NodeList nodeList = root_Node.getChildNodes();
        Node medical = nodeList.item(MEDICAL_NODE_INDEX);
        String node_Name = medical.getNodeName();
        //Check if Account name
        if (node_Name.equals(MEDICAL_NODE)) {
            NodeList medicalList = medical.getChildNodes();
            //look for State Node
            for (int ii = 0; ii < medicalList.getLength(); ii++) {
                Node node = medicalList.item(ii);
                node_Name = node.getNodeName();
                if (node_Name.equals(Tags_To_Write.Bags.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Urine.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Hydration.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.WellBeing.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Location.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Entry_Time.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Medical_State.toString())) {
                    node.setTextContent(values.get(node_Name));
                } else {
                    //ERROR invalid node found file maybe corrupt or the wrong one
                }
            }
        } else {
            //ERROR)
        }
        return null;
    }

    private Boolean Add(Node root_Node, Map<String, String> values, File account_File) {
        Boolean success = false;
        // create new document
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document new_Document = dBuilder.newDocument();
            Element root_element = new_Document.createElement(ROOT_NODE);
            new_Document.appendChild(root_element);
            Element new_medical_Entry = new_Document.createElement(MEDICAL_NODE);
            root_element.appendChild(new_medical_Entry);
            Element time = new_Document.createElement(Tags_To_Write.Entry_Time.toString());
// TODO: 30-Sep-18 need to use calendar to get the current date time of the entry and set that as the information
            time.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Entry_Time.toString())));
            new_medical_Entry.appendChild(time);

            Element location = new_Document.createElement(Tags_To_Write.Location.toString());
            location.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Location.toString())));
            new_medical_Entry.appendChild(location);

            Element bags = new_Document.createElement(Tags_To_Write.Bags.toString());
            bags.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Bags.toString())));
            new_medical_Entry.appendChild(bags);

            Element urine = new_Document.createElement(Tags_To_Write.Urine.toString());
            urine.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Urine.toString())));
            new_medical_Entry.appendChild(urine);

            Element hydration = new_Document.createElement(Tags_To_Write.Hydration.toString());
            hydration.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Hydration.toString())));
            new_medical_Entry.appendChild(hydration);

            Element wellbeing = new_Document.createElement(Tags_To_Write.WellBeing.toString());
            wellbeing.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.WellBeing.toString())));
            new_medical_Entry.appendChild(wellbeing);

            Element medical_State = new_Document.createElement(Tags_To_Write.Medical_State.toString());
            medical_State.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Medical_State.toString())));
            new_medical_Entry.appendChild(medical_State);

            //Now add old information to new document
            //WILL NEED TO LOOP FOR ALL INFORMATION CURRENTLY IN THE OLD FILE AND ADD ALL MEDICAL ENTRIES TO NEW DOCUMENT
            NodeList old_Information = root_Node.getChildNodes();
            for(int ii = 0; ii < old_Information.getLength(); ii++){//WARNING 0(n) For loop, n = number of medical entries containing a O(n) for loop, n = number of entries in each medical entry. total length = document length
                Node medical = old_Information.item(ii);
                NodeList medical_List = medical.getChildNodes();
                Element medical_Entry = new_Document.createElement(MEDICAL_NODE);
                for(int jj =0; jj < medical_List.getLength(); jj++){
                    Node temp_Node = medical_List.item(jj);
                    Element temp_Element = new_Document.createElement(temp_Node.getNodeName());
                    temp_Element.appendChild(new_Document.createTextNode(temp_Node.getTextContent()));
                    medical_Entry.appendChild(temp_Element);
                }
                root_element.appendChild(medical_Entry);
            }
            success = Write_To_File(new_Document, account_File);
            return success;
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException();// TODO: 30-Sep-18 need to create a new custom exception here
        }
    }
//WILL NEED TO ONLY COPY THE ELEMENTS UP TO CURRENT 24hour PERIOD FOR THE REVIEW DEVELOPMENT
    private Boolean Export(Node root_Node, Map<String, String> values, File account_File) {

        return null;
    }

    private boolean Write_To_File(Document document, File medical_File) {

        Boolean success = true;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(medical_File);
            transformer.transform(source, result);
        } catch (TransformerException ex){
            // TODO: 30-Sep-18   need to create a new custom exception here
            success = false;
        }
        return success;
    }
}
