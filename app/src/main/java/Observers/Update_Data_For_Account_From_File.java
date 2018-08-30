package Observers;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Update_Data_For_Account_From_File</h1>
 * The Update_Data_For_Account_From_File Java Class is used to Read In Account Information from XML file stored on the device.
 * This Observer has been created to reduce the coupling that would be required between the calling package and Operating system.
 * Implements Update_Data_Observer interface
 * <h>NOTES</h>
 * XML code is adapted from https://developer.android.com/training/basics/network-ops/xml#instantiate provided by Android for developers.
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Changes:</h1>
 * 25th Aug
 * Created 'Update_Data_For_Account_From_File', and added 'Update_Information' method, Patrick Crockford
 * Added Comment Block, Patrick Crockford
 * <p>
 * 26th Aug
 * Created XML Parser methods, based on the code provided by Android Developer Website https://developer.android.com/training/basics/network-ops/xml#instantiate, Patrick Crockford
 * <p>
 * 30th Aug
 * Updated XML reader code to comply with XML document format, Patrick Crockford
 * Started JavaDoc Commenting, Patrick Crockford
 * Finished JavaDoc Commenting, Patrick Crockford
 * <h>NOTE</h>
 * Need to update how IOException and XmlPullParserException are handled in the class.
 */

class Update_Data_For_Account_From_File implements Update_Data_Observer {
    /**
     * Name of the initial value which Account Information is Stored Under
     */
    private static final String ENTRY_TAG = "Account Information";
    /**
     * Name of the tag name the account name information is stored under
     */
    private static final String ACCOUNT_NAME = "Account Name";
    /**
     * Name of the tag name the export data information is stored under
     */
    private static final String EXPORT_DATA_SETTINGS = "Export_Data_Settings";
    /**
     * Name of the tag name the notification information is stored under
     */
    private static final String NOTIFICATION_SETTINGS = "Notification_Settings";
    /**
     * Name of the tag name the account gamification information is stored under
     */
    private static final String GAMIFICATION_SETTINGS = "Gamification_Settings";
    /**
     * Not Using Name Spaces so = NULL
     */
    private static final String NAME_SPACE = null;

    /**
     * Handles XmlPullParserException and IOException internally.
     *
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @return Map Object with string pair values of account information if successful, otherwise empty or null Map Object
     * @throws NullPointerException if input_Stream Object is null
     */
    @Override
    public Map<String, String> Update_Information(InputStream input_Stream) throws NullPointerException {
        if (input_Stream != null) {
            Map<String, String> account_Information = null;
            XmlPullParser xmlPullParser = Xml.newPullParser();
            try {
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(input_Stream, NAME_SPACE);
                xmlPullParser.nextTag();
                account_Information = readData(xmlPullParser);
                // TODO: 26-Aug-18 Work out how to proceed if error is thrown. Retry or exit back out of the async completely?
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return account_Information;
        } else {
            throw new NullPointerException("input stream object is null");
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
    // TODO: 26-Aug-18 Will need to move all xml reader code into a separate package as changing 1 thing is easier than all xml sections in packages
    private Map<String, String> readData(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = null;
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            String entry = xmlPullParser.getName();
            if (entry.equals(ENTRY_TAG)) {
                account_Information = readEntry(xmlPullParser);
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
    private Map<String, String> readEntry(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        //Loop with O(n) = number of lines between start and end tag (Dynamic length based on XML document information)
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String name = xmlPullParser.getName();
            String tag_Information;
            switch (name) {
                case ACCOUNT_NAME:
                    tag_Information = readTag(xmlPullParser, ACCOUNT_NAME);
                    account_Information.put(ACCOUNT_NAME, tag_Information);
                    break;
                case EXPORT_DATA_SETTINGS:
                    tag_Information = readTag(xmlPullParser, EXPORT_DATA_SETTINGS);
                    account_Information.put(EXPORT_DATA_SETTINGS, tag_Information);
                    break;
                case NOTIFICATION_SETTINGS:
                    tag_Information = readTag(xmlPullParser, NOTIFICATION_SETTINGS);
                    account_Information.put(NOTIFICATION_SETTINGS, tag_Information);
                    break;
                case GAMIFICATION_SETTINGS:
                    tag_Information = readTag(xmlPullParser, GAMIFICATION_SETTINGS);
                    account_Information.put(GAMIFICATION_SETTINGS, tag_Information);
                    break;
                default:
                    Skip(xmlPullParser);
                    break;
            }
        }
        return account_Information;
    }
}

