package xml_test;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import XML.Account_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Patrick Created on Sep 17, 2018
 */
public class Account_Reader_Test {

    private int tests_Passed;
    private int tests_Run;

    public void Run_Tests() {
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TESTING ACCOUNT_READER"
                + System.lineSeparator()
                + "***************************************************"
                + System.lineSeparator());

        tests_Passed = 0;
        tests_Run = 0;
        //TODO Need to open a log file for the test
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            //Testing file can be opened first
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Empty File" + System.lineSeparator()
                    + "***************************************************");
            tests_Run += 2;
            if (Open_File(xpp, "empty_file.xml")) { // Do test otherwise failure of rhat set of tests due to file not found

                tests_Passed += 1;
                System.out.println("File Opened Test Passed --- Not Null");
                XML_Reader xML_Reader = new Account_Reader();
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                try {
                    xML_Reader.Read_File(xpp, tags);
                    System.out.println("Empty File Test Failed --- Empty File didn't Throw XML_Reader_Exception Exception");
                } catch (XML_Reader_Exception ex) {
                    tests_Passed += 1;
                    System.out.println("Empty File Test Passed --- Exception thrown correctly: " + ex);
                }
            }
        } catch (XmlPullParserException | NullPointerException ex) {
            Logger.getLogger(Account_Reader_Test.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        //Printout, need to send to file
        Print_Results();

    }

    private Boolean Open_File(XmlPullParser xpp, String fileName) throws XmlPullParserException {

        Boolean pass = false;
        InputStream in;
        in = this.getClass().getResourceAsStream("XML_Test_Files/" + fileName);
        xpp.setInput(in, null);
        if (in != null) {
            pass = true;
        }
        return pass;
    }

    private void Print_Results() {
        if (tests_Run != 0) {
            System.out.println("Tests Run = " + tests_Run);
            System.out.println("Tests Passed = " + tests_Passed);
            System.out.println("% Passed = " + ((double) tests_Passed / (double) tests_Run) * 100);
        } else {
            System.out.println("Tests Run = " + tests_Run);
            System.out.println("Tests Passed = " + tests_Passed);
            System.out.println("% Passed = NULL ");
        }
    }
}
