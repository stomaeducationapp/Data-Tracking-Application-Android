package XML;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Account_Reader implements XML_Reader {
    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;
    /**
     * Name of the initial value which Account Information is Stored Under
     */
    private static final String ENTRY_TAG = "Account Information";

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
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            String entry = xmlPullParser.getName();
            if (entry.equals(ENTRY_TAG)) {
                account_Information = readEntry(xmlPullParser, tags);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * This Method finds the Tag and then calls readText to retrive the information attached to the tag
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
