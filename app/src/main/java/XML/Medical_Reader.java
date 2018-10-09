package XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Medical_Reader</h1>
 * The Medical_Reader Java Class is used to read in information from the data file stored on the
 * users device that contains the medical information of the current account logged into. Implements XML_Reader
 * interface
 * <p>
 * <h>Note</h>
 * Due to the way the XML reader assumes that the daily report will be done before any new entries for the
 * day it will cause an error if done after an entry. To Reduce this risk a new value will be added to Account data file
 * with the date of the latest daily review and will trigger a daily review
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * Patrick Crockford
 */

public class Medical_Reader implements XML_Reader {

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

    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;

    /**
     * The Name of the tag that all information is stored within for the XML file
     */
    private static final String ENTRY_TAG = "Medical_Information";

    /**
     * The Name of the tag that the medical information is stored within for the XML file
     */
    private static final String MEDICAL_TAG = "Medical_Entry";

    private Tags_To_Read entries_Required;
    private int number_Of_Entries;
    private int prefix_Of_Key;
    private String[] previous_Day_Date;

    /**
     * Public call method used to retrieve the information required from the file connected to the XMLPullParser Object
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users data file stored on the device
     * @param enum_Tags     the tags to read from the XML file specified. The ENUM for the account name is required for
     *                      the method call to successfully return the required information, otherwise the account name
     *                      information will not be read and therefore cannot be compared
     * @param account_Name  Represents the name of the account to retrieve the login information for. This must be a
     *                      valid string otherwise method call will fail
     * @return a Map with string pair values, with Tag name attached to the value read in, if empty it will be "";
     * @throws NullPointerException if XmlPullParser Object is Null, No Tags Given, or account_Name Null
     * @throws XML_Reader_Exception if an XMLPullParserException or IOException occurs when trying to read and parse the
     *                              login data file
     */
    @Override
    public Map<String, String> Read_File(XmlPullParser xmlPullParser, List<Tags_To_Read> enum_Tags, String account_Name) throws NullPointerException, XML_Reader_Exception {
        if (xmlPullParser != null && enum_Tags != null && !enum_Tags.isEmpty()) {
            number_Of_Entries = 0;
            prefix_Of_Key = 1;
            entries_Required = null;
            Map<String, String> account_Information;
            // retrieve and remove tag specifying the entries needed to retrieve and set to an Object Attribute. Also reduces the O(n) of the boolean
            if (enum_Tags.contains(Tags_To_Read.Daily_Data)) {
                entries_Required = Tags_To_Read.Daily_Data;
                enum_Tags.remove(Tags_To_Read.Daily_Data);
                Setup_Previous_Days_Date();
            } else if (enum_Tags.contains(Tags_To_Read.Export_Data)) {
                entries_Required = Tags_To_Read.Export_Data;
                enum_Tags.remove(Tags_To_Read.Export_Data);
            } else if (enum_Tags.contains(Tags_To_Read.Last_Entry)) {
                entries_Required = Tags_To_Read.Last_Entry;
                enum_Tags.remove(Tags_To_Read.Last_Entry);
            } else {
                throw new XML_Reader_Exception("Invalid ENUM given for medical entries required for task provided, please read documentation");
            }
            //Convert the ENUM list to String List
            List<String> tags = new LinkedList<>();
            for (XML_Reader.Tags_To_Read tag : enum_Tags) {
                tags.add(tag.toString());
            }
            try {
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
     * This private method parses through the XML file if the Entry point tag is found. It looks for the single account
     * information tags and calls the readEntry() method if found. If any other tags are found they are skipped through
     * the skip method call. Based on the value of 'entries_Required' private class member the method looks until until
     * all required entries are found or End of Medical
     * Information tag is found. The Map object is then returned with any information found
     * <h1>Warning</h1>
     * This method contains an O(n) While loop, where n is equal to the length of the account information tag entry
     * with an additional switch within the While loop to check what event needs to occur
     *
     * @param xmlPullParser XMLPullParser Object connected to the file
     * @param tags          the tags to read from the XML file specified, in String Format for boolean checking
     * @return A Map Object with the information, if found, in string pairs otherwise will be an empty map
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> readData(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        Map<String, String> tempMap;
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        Boolean entry_Found = false;
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
                        if (Entry_Date_Valid(tempMap.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + prefix_Of_Key))) {
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
                        throw new RuntimeException("Invalid Entry type received, Shouldn't get here -- Critical Fault");
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

    /**
     * This private method check the values of the entry date of the medical entry parsed in against the date of the
     * previous day.
     *
     * @param entry_Date The time of the entry to be checked
     * @return True if the value is between 9AM of the previous day and 8:59AM of the current day, otherwise False
     */
    public boolean Entry_Date_Valid(String entry_Date) {
        Boolean valid = false;
        try {
            String[] entry_Date_Values = entry_Date.split("-");
            //Entry from Yesterday 9am - 12 Midnight or Today 12:01AM to 8:59AM
            if (Integer.parseInt(entry_Date_Values[YEAR]) >= Integer.parseInt(previous_Day_Date[YEAR])) {
                if (Integer.parseInt(entry_Date_Values[MONTH]) >= Integer.parseInt(previous_Day_Date[MONTH])) {
                    if (Integer.parseInt(entry_Date_Values[DAY]) == Integer.parseInt(previous_Day_Date[DAY])) {
                        if (Integer.parseInt(entry_Date_Values[HOUR]) >= Integer.parseInt(previous_Day_Date[HOUR])) {
                            valid = true;
                        }
                    } else if (Integer.parseInt(entry_Date_Values[DAY]) > Integer.parseInt(previous_Day_Date[DAY])) {
                        if (Integer.parseInt(entry_Date_Values[HOUR]) < Integer.parseInt(previous_Day_Date[HOUR])) {
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

    /**
     * This private method parses the account entry found and retrieves the data from the entries required. This
     * information is put into a Map object with the key being the Tag it is stored under, that corresponds to the
     * relevant enum value. Any entries found that are not required are skipped over. This proceeds until the end of
     * the entry tag is found and the Map is returned with all information found.
     * <h1>Warning</h1>
     * This method contains an O(n) While loop, where n is equal to the length of the Account Entry There is also a
     * Boolean check with O(n) = length of the List tags
     *
     * @param xmlPullParser XMLPullParser Object connected to the file
     * @param tags          the tags to read from the XML file specified, in String Format for boolean checking
     * @return A Map Object with the information, if found, in string pairs otherwise will be an empty map
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> Read_Entry(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, MEDICAL_TAG);
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            String tag_Information;
            if (tags.contains(name)) {
                tag_Information = read_Tag_Information(xmlPullParser, name);
                account_Information.put(name + "-" + Integer.toString(prefix_Of_Key), tag_Information);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * Method Skips current Tag, iterating until corresponding 'END_TAG' is found This Method is taken from
     * https://developer.android.com/training/basics/network-ops/xml#instantiate provided to Android Developers
     * <h1>Warning</h1>
     * This method contains an O(n) While loop, where n is equal to the length of the Tag (Start_Tag to End_Tag)
     *
     * @param xmlPullParser XMLPullParser Object connected to the file
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
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
     * This private method retrieves the information stored under the tag found in the XML file. This information is
     * read and returned.
     *
     * @param xmlPullParser XMLPullParser Object connected to the file
     * @return A String Object with the information, if found, in string pairs otherwise will be an empty map
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private String read_Tag_Information(XmlPullParser xmlPullParser, String tag) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, tag);
        String tag_Information = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            tag_Information = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        xmlPullParser.require(XmlPullParser.END_TAG, NAME_SPACE, tag);
        return tag_Information;
    }

    /**
     * This private method generates the previous days date from the information provided by the Calendar API. This
     * method accounts for the edge cases of: New Month, New Year and Leap Year. The values are then added to the
     * 'previous_Day_Date' private class member
     */
    private void Setup_Previous_Days_Date() {
        int current_Day, current_Month, current_Year, previous_Day, previous_Month, previous_Year;
        Calendar calender = Calendar.getInstance();
        current_Day = calender.get(Calendar.DAY_OF_MONTH);
        current_Month = calender.get(Calendar.MONTH) + 1; //DUE TO JAVA API DOCUMENTATION JAN = 0, DEC = 11 FOR SOME STUPID REASON
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
        previous_Day_Date[HOUR] = Integer.toString(DAILY_REVIEW_CUTOFF_TIME);
        previous_Day_Date[DAY] = Integer.toString(previous_Day);
        previous_Day_Date[MONTH] = Integer.toString(previous_Month);
        previous_Day_Date[YEAR] = Integer.toString(previous_Year);
    }
}
