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
 */
public class Update_Data_For_Account_From_File implements Update_Data {
    //Change this as needed for number of bits of info for account
    private static final int ARRAY_LENGTH = 4;
    private static final String START_TAG = ""; // TODO: 26-Aug-18 Change when xml layout is known
    private static final String TAG_1 = ""; // TODO: 26-Aug-18 Change when xml layout is known
    private static final String TAG_2 = ""; // TODO: 26-Aug-18 Change when xml layout is known
    private static final String TAG_3 = ""; // TODO: 26-Aug-18 Change when xml layout is known
    private static final String TAG_4 = ""; // TODO: 26-Aug-18 Change when xml layout is known
    private static final String TAG_5 = ""; // TODO: 26-Aug-18 Change when xml layout is known

    /**
     * This Method should be called by a child thread from the activity. input stream from context is
     * created in the calling activity to stop memory leaks with the context attached to that activity.
     *
     * @param input_Stream
     * @param file_Name
     * @return
     */
    // TODO: 26-Aug-18 Work out how to proceed if error is thrown. Retry or exit back out of the async completely?
    @Override
    public Map<String, String> Update_Information(InputStream input_Stream, String file_Name) throws NullPointerException{
        if(input_Stream != null) {
            Map<String, String> account_Information = null;
            XmlPullParser xmlPullParser = Xml.newPullParser();
            try {
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(input_Stream, null);
                xmlPullParser.nextTag();
                account_Information = readData(xmlPullParser);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return account_Information;
        }else{
            throw new NullPointerException("input stream object is null");
        }
    }

    /**
     * @param xmlPullParser
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    // TODO: 26-Aug-18 Need the structure of the data, may need to construct it ourselves through java xml. or do we have a data object in observers?
    private Map<String, String> readData(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Map<String, String> account_Information = new HashMap<>();
        xmlPullParser.require(XmlPullParser.START_TAG, null, START_TAG);
        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            String entry = xmlPullParser.getName();
            switch (entry) {
                case TAG_1:
                    String info = readTag(xmlPullParser, TAG_1);
                    account_Information.put(TAG_1, info);
                    break;
                default:
                    Skip(xmlPullParser);
                    break;
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
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readText(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String result = "";
        if (xmlPullParser.next() == XmlPullParser.TEXT) {
            result = xmlPullParser.getText();
            xmlPullParser.nextTag();
        }
        return result;
    }

    /**
     * @param xmlPullParser
     * @param tag
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    private String readTag(XmlPullParser xmlPullParser, String tag) throws IOException, XmlPullParserException {
        xmlPullParser.require(XmlPullParser.START_TAG, null, tag);
        String text = readText(xmlPullParser);
        xmlPullParser.require(XmlPullParser.END_TAG, null, tag);
        return text;
    }


}

