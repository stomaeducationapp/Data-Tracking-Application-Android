package XML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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
     * The hour value of the cutoff time for the daily review, daily review is 9:00AM to 8:59AM
     */
    private static final int DAILY_REVIEW_CUTOFF_TIME = 9;
    private static final String REGEX_FOR_DATE_TIME = "-";

    private static final String ROOT_NODE = "Medical_Information";
    private static final String MEDICAL_NODE = "Medical_Entry";
    private static final int MEDICAL_NODE_INDEX = 0;
    private static final int DATE_TIME_NODE_INDEX = 0;

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
    public Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws XML_Writer_File_Layout_Exception{
        Boolean success = false;
        try {//Get Keys of Map
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(account_File);
            Node root_Node = document.getFirstChild();
            if (root_Node.getNodeName().equals(ROOT_NODE)) {
                if (null == task) {
                    //ERROR
                } else //check which task is to be done
                switch (task) {
                    case Modify:
                        success = Modify(root_Node, values, account_File);
                        break;
                    case New:
                        values.remove(Tags_To_Write.New.toString());
                        success = Add(root_Node, values, account_File);
                        break;
                    case Export:
                        values.remove(Tags_To_Write.Export.toString());
                        success = Export(root_Node, values, account_File);
                        break;
                    case Create:
                        //TODO: 3-Oct-18 need to do a first creation of tags etc etc
                        break;
                    default:
                        break;
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

    private Boolean Modify(Node root_Node, Map<String, String> values, File account_File) throws XML_Writer_File_Layout_Exception {
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
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Urine.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Hydration.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.WellBeing.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Location.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Entry_Time.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));
                } else if (node_Name.equals(Tags_To_Write.Medical_State.toString())) {
                    if(values.containsKey(node_Name))
                        node.setTextContent(values.get(node_Name));

                } else {
                   throw new XML_Writer_File_Layout_Exception();
                }
            }
            return true;
        } else {
            throw new XML_Writer_File_Layout_Exception();
        }
    }

    private Boolean Add(Node root_Node, Map<String, String> values, File account_File) {
        Boolean success;
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
            time.appendChild(new_Document.createTextNode(Get_TimeDate()));
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
            for (int ii = 0; ii < old_Information.getLength(); ii++) {//WARNING 0(n) For loop, n = number of medical entries containing a O(n) for loop, n = number of entries in each medical entry. total length = document length
                Node medical = old_Information.item(ii);
                NodeList medical_List = medical.getChildNodes();
                Element medical_Entry = new_Document.createElement(MEDICAL_NODE);
                for (int jj = 0; jj < medical_List.getLength(); jj++) {
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
        String[] cutoff_DateTime = Setup_Date_For_Export();
        boolean success;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document new_Document = dBuilder.newDocument();
            Element root_element = new_Document.createElement(ROOT_NODE);
            new_Document.appendChild(root_element);
            NodeList old_Information = root_Node.getChildNodes();
            //While loop until the first invalid entry is found, then exit as the medical file has newest to oldest from top to bottom layout allowing for quicker exit
            int ii = 0;
            Boolean finished = false;
            while (ii < old_Information.getLength() && !finished) {
                Node medical = old_Information.item(ii);
                NodeList medical_List = medical.getChildNodes();
                Element medical_Entry = new_Document.createElement(MEDICAL_NODE);
                //check the datetime, which is 1st element of the medical node
                Node date_Time = medical_List.item(DATE_TIME_NODE_INDEX);
                String date_Time_Entry = date_Time.getTextContent();
                if (Check_Date_Times(date_Time_Entry, cutoff_DateTime)) {
                    for (int jj = 0; jj < medical_List.getLength(); jj++) {//WARNING 0(n) For loop, n = number of medical entries containing a O(n) for loop, n = number of entries in each medical entry. total length = up to document length


                        Node temp_Node = medical_List.item(jj);
                        Element temp_Element = new_Document.createElement(temp_Node.getNodeName());
                        temp_Element.appendChild(new_Document.createTextNode(temp_Node.getTextContent()));
                        medical_Entry.appendChild(temp_Element);
                    }
                    root_element.appendChild(medical_Entry);
                } else { // Once first invalid found no more will be valid after
                    finished = true;
                }
                ii++;
            }
            success = Write_To_File(new_Document, account_File);
            return success;
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException();// TODO: 30-Sep-18 need to create a new custom exception here
        }
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

    private boolean Write_To_File(Document document, File medical_File) {

        Boolean success = true;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(medical_File);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            // TODO: 30-Sep-18   need to create a new custom exception here
            success = false;
        }
        return success;
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
}
