package XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Account_Reader implements XML_Reader {

    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;
    /**
     * Name of the initial value which Account Information is Stored Under
     */
    private static final String ENTRY_TAG = "Account_File";

    private static final String ACCOUNT_TAG = "Account_Information";

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

        if (xmlPullParser != null && !enum_Tags.isEmpty()) {
            //Convert Enum to strings
            List<String> tags = new LinkedList<>();
            for (XML_Reader.Tags_To_Read tag : enum_Tags) {
                tags.add(tag.toString());
            }
            Map<String, String> account_Information = null;
            try {
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.nextTag();
                account_Information = readData(xmlPullParser, tags);
            } catch (XmlPullParserException | IOException ex) {
                throw new XML_Reader_Exception("Failed to Read Login XML File: " + ex);
            }
            return account_Information;
        } else {
            throw new NullPointerException("Input Stream Object is Null or No Tags Given");
        }
    }

    /**
     * Method parses the XML File until the initial Tag "ENTRY_TAG' is found or
     * End_DOCUMENT event is reached.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
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
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String entry = xmlPullParser.getName();
            if (entry.equals(ACCOUNT_TAG)) {
                account_Information = readEntry(xmlPullParser, tags);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * This Method finds the Tag and then calls readText to retrieve the
     * information attached to the tag
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      account information file stored on the device
     * @param tag           The value of the Tag name who's information is to be
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

    /**
     * Method iterates through the 'ENTRY_TAG' information until corresponding
     * END_TAG is found
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users
     *                      data file stored on the device
     * @return
     * @throws IOException            if XMLPullParser Class throws an
     *                                IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an
     *                                XmlPullParserException when reading from
     *                                file
     */
    private Map<String, String> readEntry(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ACCOUNT_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
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

}