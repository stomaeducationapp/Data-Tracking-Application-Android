package XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Todo need to look up catching if a attribute is present as dates attributes will determine the entry, and differentiate between them
class Medical_Reader implements XML_Reader {
    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;
    /**
     * Name of the initial value which Account Information is Stored Under
     */
    private static final String ENTRY_TAG = "Medical Information";
    private static final String DATA_TAG = "Medical Entry";
    private static final String ENTRIES_RETRIEVED = "Entries";
    private static final int DAILY_REVIEW_CUTOFF_TIME = 9;//9AM
    private static final String ENTRY_ATTRIBUTE_NAME = "Date";
    private Tags_To_Read entries_Required;
    private int number_Of_entries;
    private Boolean entry_Found;// Used to exit XML parser early if required, Usually due to needing a specific entry or date time is outside of requirements for the rest of the entries
    //Please Be careful when using this as it is Class Object Scope and not method!!!!

    //Attributes for comparing datetime tags
    private String[] previous_Day_Date;

    /**
     * Read file map.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users data file stored on the device
     * @param tags          the tags to read from the XML file specified
     * @return a Map with string pair values, with Tag name attached to the value read in, if empty it will be 'NaN' value
     */
    @Override
    public Map<String, String> Read_File(XmlPullParser xmlPullParser, List<Tags_To_Read> tags) throws NullPointerException, XML_Reader_Exception {
        if (xmlPullParser != null) {
            number_Of_entries = 0;
            entries_Required = null;
            entry_Found = false;
            Map<String, String> account_Information = null;
            // retrieve and remove tag specifying the entries needed to retrieve and set to an Object Attribute. Also reduces the O(n) of the boolean
            if (tags.contains(Tags_To_Read.Daily_Data)) {
                entries_Required = Tags_To_Read.Daily_Data;
                tags.remove(Tags_To_Read.Daily_Data);
            }
            if (tags.contains(Tags_To_Read.Export_Data)) {
                entries_Required = Tags_To_Read.Export_Data;
                tags.remove(Tags_To_Read.Export_Data);
            }
            if (tags.contains(Tags_To_Read.Last_Entry)) {
                entries_Required = Tags_To_Read.Last_Entry;
                tags.remove(Tags_To_Read.Last_Entry);
            }
            try {
                xmlPullParser.nextTag();
                account_Information = readData(xmlPullParser, tags);
            } catch (XmlPullParserException e) {
                throw new XML_Reader_Exception("Failed to Read Login XML File: " + e);
            } catch (IOException e) {
                throw new XML_Reader_Exception("Failed to Read Login XML File: " + e);
            }
            account_Information.put(ENTRIES_RETRIEVED, Integer.toString(number_Of_entries));
            return account_Information;
        } else {
            throw new NullPointerException("Input Stream Object Null");
        }
    }

    /**
     * Method parses the XML File until the initial Tag "ENTRY_TAG' is found or End_DOCUMENT event is reached.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return Map Object with string pair values of account information if successful, otherwise empty or null Map Object
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> readData(XmlPullParser xmlPullParser, List<Tags_To_Read> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = null;
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT || !entry_Found) {
            String entry = xmlPullParser.getName();
            if (entry.equals(ENTRY_TAG)) {
                account_Information = Find_Entries(xmlPullParser, tags);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> Find_Entries(XmlPullParser xmlPullParser, List<Tags_To_Read> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        Map<String, String> current_Entry;
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        if (entries_Required == Tags_To_Read.Daily_Data) {
            //Setup date values
            Setup_Date_Tags();
            while (xmlPullParser.next() != XmlPullParser.END_TAG) {
                String name = xmlPullParser.getName();
                if (name.equals(DATA_TAG)) {
                    current_Entry = Read_Daily_Entry(xmlPullParser, tags);
                    account_Information.putAll(current_Entry);
                } else {
                    Skip(xmlPullParser);
                }
            }
        } else if (entries_Required == Tags_To_Read.Export_Data) {
            while (xmlPullParser.next() != XmlPullParser.END_TAG) {
                String name = xmlPullParser.getName();
                if (name.equals(DATA_TAG)) {
                    current_Entry = Read_Entries(xmlPullParser, tags);
                    account_Information.putAll(current_Entry);
                } else {
                    Skip(xmlPullParser);
                }
            }
        } else {//Get First Entry and set found = true to exit out of reader;
            while (xmlPullParser.next() != XmlPullParser.END_TAG || !entry_Found) {
                String name = xmlPullParser.getName();
                if (name.equals(DATA_TAG)) {
                    current_Entry = Read_Entries(xmlPullParser, tags);
                    account_Information.putAll(current_Entry);
                    entry_Found = true;
                } else {
                    Skip(xmlPullParser);
                }
            }
        }
        return account_Information;
    }

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> Read_Daily_Entry(XmlPullParser
                                                         xmlPullParser, List<Tags_To_Read> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        //Check the entry attribute for valid date-time
        String entry_Date = xmlPullParser.getAttributeValue(null, ENTRY_ATTRIBUTE_NAME);
        if (Entry_Date_Valid(entry_Date)) {
            number_Of_entries++;
            //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
            while (xmlPullParser.next() != XmlPullParser.END_TAG) {
                String name = xmlPullParser.getName();
                String tag_Information;
                if (tags.contains(name)) {//WARNING THIS BOOLEAN CHECK IS A LOOP of O(n) = tags.length
                    tag_Information = readTag(xmlPullParser, name);
                    account_Information.put(name + Integer.toString(number_Of_entries), tag_Information);
                } else {
                    Skip(xmlPullParser);
                }
            }
        }
        return account_Information;
    }

    //todo check when it was entered, based on Minutes as well!!!!!!!
    public boolean Entry_Date_Valid(String entry_Date) {
        Boolean valid = false;
        String[] entry_Date_Values = entry_Date.split("-");
        valid = Arrays.equals(entry_Date_Values, previous_Day_Date);
        return valid;
    }

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> Read_Entries(XmlPullParser
                                                     xmlPullParser, List<Tags_To_Read> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        number_Of_entries++;
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String name = xmlPullParser.getName();
            String tag_Information;
            if (tags.contains(name)) {//WARNING THIS BOOLEAN CHECK IS A LOOP of O(n) = tags.length
                tag_Information = readTag(xmlPullParser, name);
                account_Information.put(name + Integer.toString(number_Of_entries), tag_Information);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * Method Skips current Tag, iterating until corresponding 'END_TAG' is found
     * This Method is taken from https://developer.android.com/training/basics/network-ops/xml#instantiate provided to Android Developers
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
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
     * This Method finds the Tag and then calls readText to retrieve the information attached to the tag
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @param tag           The value of the Tag name whos information is to be read.
     * @return String containing the information read from the xml file for the corresponding tag provided, else ""
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private String readTag(XmlPullParser xmlPullParser, String tag) throws
            IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, tag);
        String tag_Information = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, NAME_SPACE, tag);
        return tag_Information;
    }

    /**
     * This Method reads the text information between the tags given in the XML file
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return String containing the information read from the xml file, else ""
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    // For valid tags, extracts their text values.
    private String readText(XmlPullParser xmlPullParser) throws
            IOException, XmlPullParserException {
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

