package XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h>Note</h>
 * Due to the way the XML reader assumes that the daily report will be done
 * before any new entries for the day it will cause an error if done after an
 * entry. To Reduce this risk a new value will be added to Account data file
 * with the date of the latest daily review and will trigger a daily review
 */
//Todo need to look up catching if a attribute is present as dates attributes will determine the entry, and differentiate between them
public class Medical_Reader implements XML_Reader {

    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;
    /**
     * Name of the initial value which Account Information is Stored Under
     */
    private static final String ENTRY_TAG = "Medical_Information";
    private static final String MEDICAL_TAG = "Medical_Entry";
    private static final int DAILY_REVIEW_CUTOFF_TIME = 9;//9:00AM as daily review is 9:00AM to 8:59AM
    private Tags_To_Read entries_Required;
    private int number_Of_Entries;
    private int prefix_Of_Key;
    //Attributes for comparing datetime tags
    private String[] previous_Day_Date;

    /**
     * Read file map.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      data file stored on the device
     * @param tags          the tags to read from the XML file specified
     * @return a Map with string pair values, with Tag name attached to the
     *         value read in, if empty it will be 'NaN' value
     */
    @Override
    public Map<String, String> Read_File(XmlPullParser xmlPullParser, List<Tags_To_Read> enum_Tags, String account_Name) throws NullPointerException, XML_Reader_Exception {
        if (xmlPullParser != null && enum_Tags != null && !enum_Tags.isEmpty()) {
            number_Of_Entries = 0;
            prefix_Of_Key = 1;
            entries_Required = null;
            Map<String, String> account_Information = null;
            // retrieve and remove tag specifying the entries needed to retrieve and set to an Object Attribute. Also reduces the O(n) of the boolean
            if (enum_Tags.contains(Tags_To_Read.Daily_Data)) {
                entries_Required = Tags_To_Read.Daily_Data;
                enum_Tags.remove(Tags_To_Read.Daily_Data);
                //Setup date values
                Setup_Date_Tags();
            }
            else if (enum_Tags.contains(Tags_To_Read.Export_Data)) {
                entries_Required = Tags_To_Read.Export_Data;
                enum_Tags.remove(Tags_To_Read.Export_Data);
            }
            else if (enum_Tags.contains(Tags_To_Read.Last_Entry)) {
                entries_Required = Tags_To_Read.Last_Entry;
                enum_Tags.remove(Tags_To_Read.Last_Entry);
            }
            else{
                throw new XML_Reader_Exception("Invalid ENUM given for medical entries required for task provided, please read documentation");
            }
            try {
                //Convert the ENUM list to String List
                List<String> tags = new LinkedList<>();
                for (XML_Reader.Tags_To_Read tag : enum_Tags) {
                    tags.add(tag.toString());
                }
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.nextTag();
                account_Information = readData(xmlPullParser, tags);
            } catch (XmlPullParserException | IOException e) {
                throw new XML_Reader_Exception("Failed to Read Login XML File: " + e);
            }
            return account_Information;
        } else {
            throw new NullPointerException("One of the following Errors has occurred: XMLPullParser is NULL, List<Tags_To_Read> is NULL or empty");
        }
    }

    /**
     * Method parses the XML File until the initial Tag "ENTRY_TAG' is found or
     * End_DOCUMENT event is reached.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @param tags
     * @return Map Object with string pair values of account information if
     *         successful, otherwise empty or null Map Object
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    private Map<String, String> readData(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        Map<String, String> tempMap;
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        Boolean entry_Found = false;
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT && !entry_Found) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String entry = xmlPullParser.getName();
            if (entry.equals(MEDICAL_TAG)) {
                switch (entries_Required) {
                    case Last_Entry:
                        account_Information = Read_Entry(xmlPullParser, tags);
                        number_Of_Entries++;
                        prefix_Of_Key++;
                        entry_Found = true;
                        break;
                    case Daily_Data:
                        tempMap = Read_Entry(xmlPullParser, tags);
                        if (Entry_Date_Valid(tempMap.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + prefix_Of_Key))) {
                            account_Information.putAll(tempMap);
                            number_Of_Entries++;
                            prefix_Of_Key++;
                        } else { //Because the entries are in Chronological order from Present -> History (Top -> Bottom) the reader can stop once the entry is outside of the values
                            entry_Found = true;
                        }
                        break;
                    case Export_Data:
                        account_Information.putAll(Read_Entry(xmlPullParser, tags));
                        number_Of_Entries++;
                        prefix_Of_Key++;
                        break;
                    default:
                        throw new RuntimeException("Invalid Entry type recieved, Shouldn't get here Critical Fault");
                }
            } else {
                Skip(xmlPullParser);
            }
        }
        if (number_Of_Entries > 0) {
            account_Information.put(XML_Reader.Tags_To_Read.Entries_Retrieved.toString(), Integer.toString(number_Of_Entries));
        }
        return account_Information;
    }

    public boolean Entry_Date_Valid(String entry_Date) {
        Boolean valid = false;
        String[] entry_Date_Values = entry_Date.split("-");
        valid = Arrays.equals(entry_Date_Values, previous_Day_Date);
        return valid;
    }

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding
     * END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    private Map<String, String> Read_Entry(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, MEDICAL_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            String tag_Information;
            if (tags.contains(name)) {//WARNING THIS BOOLEAN CHECK IS A LOOP of O(n) = tags.length
                tag_Information = readTag(xmlPullParser, name);
                account_Information.put(name + Integer.toString(prefix_Of_Key), tag_Information);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * Method Skips current Tag, iterating until corresponding 'END_TAG' is
     * found This Method is taken from
     * https://developer.android.com/training/basics/network-ops/xml#instantiate
     * provided to Android Developers
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    private void Skip(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int depth = 1;
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (depth > 0) {
            switch (xmlPullParser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    /**
     * This Method finds the Tag and then calls readText to retrieve the
     * information attached to the tag
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @param tag           The value of the Tag name whos information is to be
     *                      read.
     * @return String containing the information read from the xml file for the
     *         corresponding tag provided, else ""
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    private String readTag(XmlPullParser xmlPullParser, String tag) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, tag);
        String tag_Information = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, NAME_SPACE, tag);
        return tag_Information;
    }

    /**
     * This Method reads the text information between the tags given in the XML
     * file
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @return String containing the information read from the xml file, else ""
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    // For valid tags, extracts their text values.
    private String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String result = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            result = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        return result;
    }

    private void Setup_Date_Tags() {
        int current_Day, current_Month, current_Year, previous_Day, previous_Month, previous_Year;
        Calendar calender = Calendar.getInstance();
        current_Day = calender.get(Calendar.DAY_OF_MONTH);
        current_Month = calender.get(Calendar.MONTH);
        current_Year = calender.get(Calendar.YEAR);
        // Now get Yesterdays date and also
        //Check Start of Year first as that is a special Case
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
        previous_Day_Date = new String[4];
        previous_Day_Date[0] = Integer.toString(DAILY_REVIEW_CUTOFF_TIME);
        previous_Day_Date[1] = Integer.toString(previous_Day);
        previous_Day_Date[2] = Integer.toString(previous_Month);
        previous_Day_Date[3] = Integer.toString(previous_Year);
    }
}
