package Observers;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patrick on 25-Aug-18.
 * Last Edited by Patrick on 25-Aug-18 8:20pm
 * <p>
 * Changes:
 * 25th Aug
 * Created 'Update_Data_For_Account_From_File', and added 'Update_Information' method
 * Added Comment Block
 * <p>
 * 26th Aug
 * Created XML Parser methods, based on the code provided by Android Developer Website https://developer.android.com/training/basics/network-ops/xml#instantiate
 * TODO: add https://developer.android.com/training/basics/network-ops/xml#instantiate reference to methods required (XML based)
 * 30th Aug
 * Updated XML reader code to comply with XML document format
 * Started JavaDoc Commenting
 *
 */

class Update_Data_For_Account_From_File implements Update_Data {
    //Change this as needed for number of bits of info for account
    /**
     *
     */
    private static final int ARRAY_LENGTH = 4;
    /**
     *
     */
    private static final String ENTRY_TAG = "Account Information";
    /**
     *
     */
    private static final String TAG_1 = "Account Name";
    /**
     *
     */
    private static final String TAG_2 = "Export_Data_Settings";
    /**
     *
     */
    private static final String TAG_3 = "Notification_Settings";
    /**
     *
     */
    private static final String TAG_4 = "Gamification_Settings";
    //Don't need name space
    /**
     *
     */
    private static final String NAME_SPACE = null;


    /**
     * @param input_Stream
     * @return
     * @throws NullPointerException
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
     * @param xmlPullParser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    // TODO: 26-Aug-18 Will need to move all xml reader code into a separate package as changing 1 thing is easier than all xml sections in packages
    private Map<String, String> readData(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = null;
        int event = xmlPullParser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
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
     * @param xmlPullParser
     * @throws XmlPullParserException
     * @throws IOException
     */
    private void Skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int depth = 1;
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
     * @param xmlPullParser
     * @param tag
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readTag(XmlPullParser xmlPullParser, String tag) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, tag);
        String tag_Information = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, NAME_SPACE, tag);
        return tag_Information;
    }

    /**
     * @param parser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    // For valid tags, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    /**
     * @param xmlPullParser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private Map<String, String> readEntry(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, ENTRY_TAG);
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String name = xmlPullParser.getName();
            String tag_Information;
            switch (name) {
                case TAG_1:
                    tag_Information = readTag(xmlPullParser, TAG_1);
                    account_Information.put(TAG_1, tag_Information);
                    break;
                case TAG_2:
                    tag_Information = readTag(xmlPullParser, TAG_2);
                    account_Information.put(TAG_2, tag_Information);
                    break;
                case TAG_3:
                    tag_Information = readTag(xmlPullParser, TAG_3);
                    account_Information.put(TAG_3, tag_Information);
                    break;
                case TAG_4:
                    tag_Information = readTag(xmlPullParser, TAG_4);
                    account_Information.put(TAG_4, tag_Information);
                    break;
                default:
                    Skip(xmlPullParser);
                    break;
            }
        }
        return account_Information;
    }
}

