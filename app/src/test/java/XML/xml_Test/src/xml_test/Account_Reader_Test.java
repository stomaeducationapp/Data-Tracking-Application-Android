package xml_test;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author Patrick Created on Sep 17, 2018
 */
public class Account_Reader_Test {

    public Boolean Run_Tests() {
        Boolean success = true;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

        } catch (XmlPullParserException ex) {
            Logger.getLogger(Account_Reader_Test.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }

}
