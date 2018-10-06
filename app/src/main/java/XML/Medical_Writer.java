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
 * <h1>NOTE</h1>
 * Modify will only modify the first entry in the document as the functionality
 * has been created for the medical state calculator primarily
 * <p>
 * <p>
 */
public class Medical_Writer implements XML_Writer {

    /**
     * Section the hour value is stored under in time arrays
     */
    private static final int HOUR = 0;

    /**
     * Section the day value is stored under in time arrays
     */
    private static final int DAY = 1;

    /**
     * Section the month value is stored under in time arrays
     */
    private static final int MONTH = 2;

    /**
     * Section the year value is stored under in time arrays
     */
    private static final int YEAR = 3;

    /**
     * The hour value of the cutoff time for the daily review, daily review is
     * 9:00AM to 8:59AM
     */
    private static final int DAILY_REVIEW_CUTOFF_TIME = 9;
    private static final String REGEX_FOR_DATE_TIME = "-";
    private static final String FIRST_LINE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
    private static final String ROOT_NODE = "Medical_Information";
    private static final String MEDICAL_NODE = "Medical_Entry";
    private static final String DEFAULT_NODE_ENTRY = "No Entry";

    /**
     * @param medical_File
     * @param values       Map with string pair values, where the Keys correlate
     *                     to the Enum Tags_To_Write values
     * @param task
     * @return True if successful otherwise false
     * @throws XML.XML_Writer_Failure_Exception
     * @throws XML.XML_Writer_File_Layout_Exception
     */
    //For creating a new document
    //https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
    //then merge existing one from file into it!!!! for adding a new entry
    @Override
    public Boolean Write_File(File medical_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_Failure_Exception, XML_Writer_File_Layout_Exception {
        if (values != null && medical_File != null && medical_File.canWrite() && task != null) {
            Boolean success;
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Quieten_BD(documentBuilder);
                Document document;
                Node root_Node;
                switch (task) {
                    case Modify:
                        document = documentBuilder.parse(medical_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Modify(root_Node, values, medical_File, document);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Medical_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", medical_File.getName()));
                        }
                        break;
                    case New:
                        document = documentBuilder.parse(medical_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = New(root_Node, values, medical_File, documentBuilder);

                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Medical_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", medical_File.getName()));
                        }
                        break;
                    case Export:
                        document = documentBuilder.parse(medical_File);
                        root_Node = document.getFirstChild();
                        if (root_Node.getNodeName().equals(ROOT_NODE)) {
                            success = Export(root_Node, documentBuilder, medical_File);
                        } else {
                            throw new XML_Writer_File_Layout_Exception(String.format("Cannot find 'Medical_Information' Root XML Tag/Node in the File '%s'. File is either corrupt or invalid", medical_File.getName()));
                        }
                        break;
                    case Create:
                        success = Create(medical_File);
                        break;
                    default:
                        throw new XML_Writer_Failure_Exception("Invalid task given must be either Modify or Create");
                }
                return success;
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException ex) {
                throw new XML_Writer_Failure_Exception((String.format("Failed to Write file '%s' due to '%s'", medical_File.getName(), ex)));
            }
        } else {
            throw new XML_Writer_Failure_Exception("Failed to execute due to File is Null, or Map is Null or Empty");
        }
    }

    private Boolean Modify(Node root_Node, Map<String, String> values, File medical_File, Document document) throws TransformerException, FileNotFoundException, IOException {
        NodeList medical_Entries_List = root_Node.getChildNodes();
        int jj = 0;
        Boolean found = false;
        while (!found && jj < medical_Entries_List.getLength()) {
            Node medical_Node = medical_Entries_List.item(jj);
            if (medical_Node.getNodeName().equals(MEDICAL_NODE)) {
                found = true;//found first node due to functionality go no futher
                NodeList medical_Entry = medical_Node.getChildNodes();
                //look for State Node
                for (int ii = 0; ii < medical_Entry.getLength(); ii++) {
                    Node node = medical_Entry.item(ii);
                    if (node.getNodeName().equals(Tags_To_Write.Bags.toString())) {
                        if (values.containsKey(Tags_To_Write.Bags.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Bags.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.Urine.toString())) {
                        if (values.containsKey(Tags_To_Write.Urine.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Urine.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.Hydration.toString())) {
                        if (values.containsKey(Tags_To_Write.Hydration.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Hydration.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.WellBeing.toString())) {
                        if (values.containsKey(Tags_To_Write.WellBeing.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.WellBeing.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.Location.toString())) {
                        if (values.containsKey(Tags_To_Write.Location.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Location.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.Entry_Time.toString())) {
                        if (values.containsKey(Tags_To_Write.Entry_Time.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Entry_Time.toString()));
                        }
                    } else if (node.getNodeName().equals(Tags_To_Write.Medical_State.toString())) {
                        if (values.containsKey(Tags_To_Write.Medical_State.toString())) {
                            node.setTextContent(values.get(Tags_To_Write.Medical_State.toString()));
                        }
                    }
                }
            }
            jj++;
        }
        return Write_To_File(document, medical_File);
    }

    private Boolean New(Node root_Node, Map<String, String> values, File account_File, DocumentBuilder documentBuilder) throws TransformerException, FileNotFoundException, IOException {
        // create new document
        Document new_Document = documentBuilder.newDocument();
        Element root_element = new_Document.createElement(ROOT_NODE);
        new_Document.appendChild(root_element);

        Element new_medical_Entry = new_Document.createElement(MEDICAL_NODE);
        root_element.appendChild(new_medical_Entry);

        Element time = new_Document.createElement(Tags_To_Write.Entry_Time.toString());
        time.appendChild(new_Document.createTextNode(Get_TimeDate()));
        new_medical_Entry.appendChild(time);

        Element location = new_Document.createElement(Tags_To_Write.Location.toString());
        if (values.containsKey(Tags_To_Write.Location.toString())) {
            location.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Location.toString())));
        } else {
            location.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(location);

        Element bags = new_Document.createElement(Tags_To_Write.Bags.toString());
        if (values.containsKey(Tags_To_Write.Bags.toString())) {
            bags.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Bags.toString())));
        } else {
            bags.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(bags);

        Element urine = new_Document.createElement(Tags_To_Write.Urine.toString());
        if (values.containsKey(Tags_To_Write.Urine.toString())) {
            urine.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Urine.toString())));
        } else {
            urine.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(urine);

        Element hydration = new_Document.createElement(Tags_To_Write.Hydration.toString());
        if (values.containsKey(Tags_To_Write.Hydration.toString())) {
            hydration.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Hydration.toString())));
        } else {
            hydration.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(hydration);

        Element wellbeing = new_Document.createElement(Tags_To_Write.WellBeing.toString());
        if (values.containsKey(Tags_To_Write.WellBeing.toString())) {
            wellbeing.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.WellBeing.toString())));
        } else {
            wellbeing.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(wellbeing);

        Element medical_State = new_Document.createElement(Tags_To_Write.Medical_State.toString());
        if (values.containsKey(Tags_To_Write.Medical_State.toString())) {
            medical_State.appendChild(new_Document.createTextNode(values.get(Tags_To_Write.Medical_State.toString())));
        } else {
            medical_State.appendChild(new_Document.createTextNode(DEFAULT_NODE_ENTRY));
        }
        new_medical_Entry.appendChild(medical_State);

        //Now add old information to new document
        //WILL NEED TO LOOP FOR ALL INFORMATION CURRENTLY IN THE OLD FILE AND ADD ALL MEDICAL ENTRIES TO NEW DOCUMENT
        NodeList old_Information = root_Node.getChildNodes();
        for (int ii = 0; ii < old_Information.getLength(); ii++) {//WARNING 0(n) For loop, n = number of medical entries containing a O(n) for loop, n = number of entries in each medical entry. total length = document length
            Node medical = old_Information.item(ii);
            if (medical.getNodeName().equals(MEDICAL_NODE)) {
                NodeList medical_List = medical.getChildNodes();
                Element medical_Entry = new_Document.createElement(MEDICAL_NODE);
                for (int jj = 0; jj < medical_List.getLength(); jj++) {
                    Node temp_Node = medical_List.item(jj);
                    if (Valid_Node(temp_Node)) {
                        Element temp_Element = new_Document.createElement(temp_Node.getNodeName());
                        temp_Element.appendChild(new_Document.createTextNode(temp_Node.getTextContent()));
                        medical_Entry.appendChild(temp_Element);
                    }
                }
                root_element.appendChild(medical_Entry);
            }
        }
        return Write_To_File(new_Document, account_File);

    }

    //WILL NEED TO ONLY COPY THE ELEMENTS UP TO CURRENT 24hour PERIOD FOR THE REVIEW DEVELOPMENT
    private Boolean Export(Node root_Node, DocumentBuilder documentBuilder, File medical_File) throws TransformerException, FileNotFoundException, IOException {
        String[] cutoff_DateTime = Setup_Date_For_Export();
        boolean success;
        Document new_Document = documentBuilder.newDocument();
        Element root_element = new_Document.createElement(ROOT_NODE);
        new_Document.appendChild(root_element);
        NodeList old_Information = root_Node.getChildNodes();
        //While loop until the first invalid entry is found, then exit as the medical file has newest to oldest from top to bottom layout allowing for quicker exit
        int ii = 0;
        Boolean found = false;
        int entries_Left = 0;
        while (ii < old_Information.getLength() && !found) {
            Node old_Medical_Node = old_Information.item(ii);
            if (old_Medical_Node.getNodeName().equals(MEDICAL_NODE)) {
                entries_Left++;
                System.out.println("Entries " + entries_Left);
                Element medical_Entry = new_Document.createElement(MEDICAL_NODE);
                NodeList old_Medical_List = old_Medical_Node.getChildNodes();
                for (int jj = 0; jj < old_Medical_List.getLength(); jj++) {
                    Node temp_Node = old_Medical_List.item(jj);
                    if (Valid_Node(temp_Node)) {
                        Element temp_Element = new_Document.createElement(temp_Node.getNodeName());
                        temp_Element.appendChild(new_Document.createTextNode(temp_Node.getTextContent()));
                        medical_Entry.appendChild(temp_Element);
                    }
                }
                if (!found) {

                    for (int jj = 0; jj < old_Medical_List.getLength(); jj++) {
                        Node temp_Node = old_Medical_List.item(jj);
                        if (temp_Node.getNodeName().equals(XML_Writer.Tags_To_Write.Entry_Time.toString())) {
                            if (!Check_Date_Times(temp_Node.getTextContent(), cutoff_DateTime)) {
                                found = true;
                                entries_Left--;
                            } else {
                                root_element.appendChild(medical_Entry);
                            }
                        }
                    }
                } else {
                    root_element.appendChild(medical_Entry);
                }
            }
            System.out.println("found = " + found);
            ii++;
        }
        if (entries_Left == 0) {
            System.out.println("Reseting File");
            success = Create(medical_File);
        } else {
            success = Write_To_File(new_Document, medical_File);
        }
        return success;
    }

    private Boolean Create(File login_File) throws TransformerException, FileNotFoundException, IOException {
        PrintWriter pw = new PrintWriter(login_File);
        pw.println(FIRST_LINE);
        pw.print("<Medical_Information>");
        pw.print("</Medical_Information>");
        pw.flush();
        pw.close();
        return true;
    }

    private Boolean Valid_Node(Node node) {
        Boolean valid = false;
        if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Bags.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Urine.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Hydration.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.WellBeing.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Location.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Entry_Time.toString())) {
            valid = true;
        } else if (node.getNodeName().equals(XML_Writer.Tags_To_Write.Medical_State.toString())) {
            valid = true;
        }
        return valid;
    }

    private Boolean Check_Date_Times(String entry_Date, String[] cutoff_DateTime) {
        Boolean valid = false;
        try {
            String[] entry_Date_Values = entry_Date.split(REGEX_FOR_DATE_TIME);
            //Entry from Yesterday 9am - 12 Midnight or Today 12:01AM to 8:59AM
            if (Integer.parseInt(entry_Date_Values[YEAR]) >= Integer.parseInt(cutoff_DateTime[YEAR])) {
                if (Integer.parseInt(entry_Date_Values[MONTH]) >= Integer.parseInt(cutoff_DateTime[MONTH])) {
                    if (Integer.parseInt(entry_Date_Values[DAY]) == Integer.parseInt(cutoff_DateTime[DAY])) {
                        if (Integer.parseInt(entry_Date_Values[HOUR]) >= Integer.parseInt(cutoff_DateTime[HOUR])) {
                            valid = true;
                        }
                    } else if (Integer.parseInt(entry_Date_Values[DAY]) > Integer.parseInt(cutoff_DateTime[DAY])) {
                        if (Integer.parseInt(entry_Date_Values[HOUR]) < Integer.parseInt(cutoff_DateTime[HOUR])) {
                            valid = true;
                        }
                    }
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            valid = false;
        }
        return valid;
    }

    private String Get_TimeDate() {
        Calendar calender = Calendar.getInstance();
        StringBuilder current_DateTime = new StringBuilder();
        current_DateTime.append(calender.get(Calendar.HOUR_OF_DAY));
        current_DateTime.append(REGEX_FOR_DATE_TIME);
        current_DateTime.append(calender.get(Calendar.DAY_OF_MONTH));
        current_DateTime.append(REGEX_FOR_DATE_TIME);
        current_DateTime.append((calender.get(Calendar.MONTH) + 1));
        current_DateTime.append(REGEX_FOR_DATE_TIME);
        current_DateTime.append(calender.get(Calendar.YEAR));
        return current_DateTime.toString();
    }

    private String[] Setup_Date_For_Export() {
        int current_Day, current_Month, current_Year, previous_Day, previous_Month, previous_Year, current_Time;
        Calendar calender = Calendar.getInstance();
        current_Day = calender.get(Calendar.DAY_OF_MONTH);
        current_Month = calender.get(Calendar.MONTH) + 1; //DUE TO JAVA API DOCUMENTATION JAN = 0, DEC = 11 FOR SOME STUPID REASON
        current_Year = calender.get(Calendar.YEAR);
        current_Time = calender.get(Calendar.HOUR_OF_DAY);
        if (current_Time <= 9) {
            //Need to check if between 12 Midnight and 8:59 am, if so get yesterdays day
            //else todays day is correct
            if (current_Month == 1 && current_Day == 1) {
                previous_Day = 31;
                previous_Month = 12;
                previous_Year = current_Year - 1;
            } else {
                //First check if start of month other than Jan have to then change month as well
                if (current_Day == 1) {
                    if (current_Month % 2 != 0) {//30 days in previous Month
                        if (current_Month == 2) {//FEB
                            //Check Leap Year!!
                            if ((current_Year % 4 == 0) && (current_Year % 100 == 0)) {//leap year
                                previous_Day = 29;
                            } else {
                                previous_Day = 28;
                            }
                        } else {
                            previous_Day = 30;
                        }
                    } else {//31 days in previous Month
                        previous_Day = 31;
                    }
                    previous_Month = current_Month - 1; // After Day is sorted go back to previous Month in Calendar
                } else {// otherwise just reduce day by 1
                    previous_Day = current_Day - 1;
                    previous_Month = current_Month;
                }
                previous_Year = current_Year;
            }
        } else {
            previous_Day = current_Day;
            previous_Month = current_Month;
            previous_Year = current_Year;
        }
        String[] cutoff_Date = new String[4];
        cutoff_Date[HOUR] = Integer.toString(DAILY_REVIEW_CUTOFF_TIME);
        cutoff_Date[DAY] = Integer.toString(previous_Day);
        cutoff_Date[MONTH] = Integer.toString(previous_Month);
        cutoff_Date[YEAR] = Integer.toString(previous_Year);
        return cutoff_Date;
    }

    private boolean Write_To_File(Document document, File medical_File) throws TransformerException, FileNotFoundException, IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(new DOMSource(document), new StreamResult(medical_File));
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
