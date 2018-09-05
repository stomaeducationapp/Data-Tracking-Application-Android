package XML;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
    private Tags_To_Read entries_Required;
    private int number_Of_entries;
    private Boolean entry_Found;// Used to exit XML parser early if required, Usually due to needing a specific entry or date time is outside of requirements for the rest of the entries
    //Please Be careful when using this as it is Class Object Scope and not method!!!!

    /**
     * Read file map.
     *
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @param tags         the tags to read from the XML file specified
     * @return a Map with string pair values, with Tag name attached to the value read in, if empty it will be 'NaN' value
     */
    @Override
    public Map<String, String> Read_File(FileInputStream input_Stream, List<Tags_To_Read> tags) throws NullPointerException, XML_Reader_Exception {
        if (input_Stream != null) {
            number_Of_entries = 0;
            entries_Required = null;
            entry_Found = false;
            Map<String, String> account_Information = null;
            XmlPullParser xmlPullParser = Xml.newPullParser();
            try {
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(input_Stream, NAME_SPACE);
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
        // retrieve and remove tag specifying the entries needed to retrieve and set to an Object Attribute. Also reduces the O(n) of the boolean
        if(tags.contains(Tags_To_Read.Daily_Data)){
            entries_Required = Tags_To_Read.Daily_Data;
            tags.remove(Tags_To_Read.Daily_Data);
        }
        if(tags.contains(Tags_To_Read.Export_Data)){
            entries_Required = Tags_To_Read.Export_Data;
            tags.remove(Tags_To_Read.Export_Data);
        }
        if(tags.contains(Tags_To_Read.Last_Entry)){
            entries_Required = Tags_To_Read.Last_Entry;
            tags.remove(Tags_To_Read.Last_Entry);
        }
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
        Map<String,String> current_Entry = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
//todo Set Date Format to day (int) Month (int) Year(int) and use that to see if attribute date time of entry is within requirements. This should only be done if requiring multiple entries!
        //todo if single latest entry skip all this and get it
        //Get Current Date and Time to use as required
        Date current = Calendar.getInstance().getTime();

        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG || !entry_Found) {
            String name = xmlPullParser.getName();
            if (tags.contains(DATA_TAG)) {
                //Need to check if datetime attribute falls within required information.
                if(entries_Required == Tags_To_Read.Export_Data || entries_Required == Tags_To_Read.Daily_Data)
                {//Check Entry Attribute first!!!! and compare to requirements.
                 //todo compare attribute code here
                    current_Entry = readEntry(xmlPullParser, tags);
                    account_Information.putAll(current_Entry);

                }
                else{
                    //Get First Entry and set found = true to exit out of reader;
                    current_Entry = readEntry(xmlPullParser, tags);
                    account_Information.putAll(current_Entry);
                    entry_Found = true;
                }
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
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
    private String readTag(XmlPullParser xmlPullParser, String tag) throws IOException, XmlPullParserException {
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
    private String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String result = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            result = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        return result;
    }

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users account information file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
     */
    private Map<String, String> readEntry(XmlPullParser xmlPullParser, List<Tags_To_Read> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String name = xmlPullParser.getName();
            String tag_Information;
            if (tags.contains(name)) {//WARNING THIS BOOLEAN CHECK IS A LOOP of O(n) = tags.length
                tag_Information = readTag(xmlPullParser, name);
                account_Information.put(name, tag_Information);
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

}
