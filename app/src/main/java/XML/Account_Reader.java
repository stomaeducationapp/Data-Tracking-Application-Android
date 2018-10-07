package XML;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Account_Reader</h1>
 * The Account_Reader Java Class is used to read in information from the data file stored on the
 * users device that contains the current accounts information and settings. Implements XML_Reader interface
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * Patrick Crockford
 */
public class Account_Reader implements XML_Reader {

    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;

    /**
     * The Name of the tag that all information is stored within for the XML file
     */
    private static final String ENTRY_TAG = "Account_File";

    /**
     * The Name of the tag that the account information is stored within for the XML file
     */
    private static final String ACCOUNT_TAG = "Account_Information";

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

        if (xmlPullParser != null && !enum_Tags.isEmpty()) {
            //Convert Enum to strings
            List<String> tags = new LinkedList<>();
            for (XML_Reader.Tags_To_Read tag : enum_Tags) {
                tags.add(tag.toString());
            }
            Map<String, String> account_Information;
            try {
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
     * This private method parses through the XML file if the Entry point tag is found. It looks for the single account
     * information tags and calls the readEntry() method if found. If any other tags are found they are skipped through
     * the skip method call. The method looks until End of Account information tag is found. The Map object is then
     * returned with any information found
     * <h1>Warning</h1>
     * This method contains an O(n) While loop, where n is equal to the length of the account information tag entry
     *
     * @param xmlPullParser XMLPullParser Object connected to the file
     * @param tags          the tags to read from the XML file specified, in String Format for boolean checking
     * @return A Map Object with the information, if found, in string pairs otherwise will be an empty map
     * @throws IOException            if XMLPullParser Class throws an IOException when reading from file
     * @throws XmlPullParserException if XMLPullParser Class throws an XmlPullParserException when reading from file
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
    private Map<String, String> readEntry(XmlPullParser xmlPullParser, List<String> tags) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ACCOUNT_TAG);
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            String tag_Information;
            if (tags.contains(name)) {
                tag_Information = read_Tag_Information(xmlPullParser, name);
                account_Information.put(name, tag_Information);
            } else {
                Skip(xmlPullParser);
            }
        }
        return account_Information;
    }

    /**
     * This private method retrieve the information stored under the tag found in the XML file. This information is read
     * and returned.
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
     * Method Skips current Tag, iterating until corresponding 'END_TAG' is found This Method is taken from
     * https://developer.android.com/training/basics/network-ops/xml#instantiate provided to Android Developers
     * <h1>Warning</h1> This method contains an O(n) While loop, where n is equal to the length of the Tag (Start_Tag to
     * End_Tag)
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

}