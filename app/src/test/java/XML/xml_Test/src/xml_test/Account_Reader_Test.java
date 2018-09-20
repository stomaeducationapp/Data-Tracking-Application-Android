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
import java.util.Map;

/**
 * @author Patrick Created on Sep 17, 2018
 */
public class Account_Reader_Test {

    private int tests_Passed;
    private int tests_Run;
    private XML_Reader xML_Reader;

    public void Run_Tests() {
        xML_Reader = new Account_Reader();
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TESTING ACCOUNT_READER");

        tests_Passed = 0;
        tests_Run = 0;
        //TODO Need to open a log file for the test
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            //Testing file can be opened first
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Empty File" + System.lineSeparator()
            );
            tests_Run += 2;
            if (Open_File(xpp, "empty_file.xml")) { // Do test otherwise failure of rhat set of tests due to file not found

                tests_Passed++;
                System.out.println("File Opened Test Passed --- Not Null");
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                try {
                    xML_Reader.Read_File(xpp, tags, null);
                    System.out.println("Empty File Test Failed --- Empty File didn't Throw XML_Reader_Exception Exception");
                } catch (XML_Reader_Exception ex) {
                    tests_Passed++;
                    System.out.println("Empty File Test Passed --- XML_Reader_Exception Exception thrown correctly: " + ex);
                }
            } else {
                System.out.println("Empty File Test Failed --- Failed to Open File");
            }
            //Valid layout but no information in any of them, Dont want anything (list is empty)\
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File Empty Information" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 2;
            if (Open_File(xpp, "valid_account_file_empty_info.xml")) {
                try {
                    List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                    try {
                        xML_Reader.Read_File(xpp, tags, null);
                        System.out.println("Valid Empty File Test Failed --- Empty File didn't Throw Null Pointer Exception");
                    } catch (XML_Reader_Exception ex) {
                        System.out.println("Valid Empty File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Valid Empty File Test Passed --- NullPointerException Exception thrown Correctly: " + ex);
                    tests_Passed++;
                }
                //Asking for all, should return keys only
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                tags.add(XML_Reader.Tags_To_Read.Gamification);
                tags.add(XML_Reader.Tags_To_Read.Notification);
                tags.add(XML_Reader.Tags_To_Read.State);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    if (!info.isEmpty() && info.containsKey("Name") && info.containsKey("Gamification") && info.containsKey("Notification") && info.containsKey("State")) {
                        System.out.println("Valid Empty File Test Passed --- Map Given Back with all Keys");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid Empty File Test Failed --- Empty Map Given Back");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid Empty File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("Valid Empty File Test Failed --- NullPointerException Exception thrown: " + ex);
                }

            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
            }

            //Valid layout and information, Asking for all
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 6;
            if (Open_File(xpp, "valid_account_file.xml")) {
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                tags.add(XML_Reader.Tags_To_Read.Gamification);
                tags.add(XML_Reader.Tags_To_Read.Notification);
                tags.add(XML_Reader.Tags_To_Read.State);
                tags.add(XML_Reader.Tags_To_Read.Bags);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    if (!info.isEmpty()) {
                        System.out.println("Valid File Test Passed --- Non Empty Given Back");
                        tests_Passed++;
                        if (info.get("Name").equals("a")) {
                            tests_Passed++;
                            System.out.println("Valid File Test Passed --- Valid Name value Given Back");
                        }
                        if (info.get("Notification").equals("b")) {
                            tests_Passed++;
                            System.out.println("Valid File Test Passed --- Valid Notifications value Given Back");
                        }
                        if (info.get("State").equals("c")) {
                            tests_Passed++;
                            System.out.println("Valid File Test Passed --- Valid State value Given Back");
                        }
                        if (info.get("Gamification").equals("d")) {
                            tests_Passed++;
                            System.out.println("Valid File Test Passed --- Valid Gamification value Given Back");
                        }
                        if (!info.containsKey("Bags")) {
                            tests_Passed++;
                            System.out.println("Valid File Test Passed --- No Bags Key");
                        }
                    } else {
                        System.out.println("Valid File Test Failed --- Empty Map Given Back");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                    
                } catch (NullPointerException ex) {
                    Logger.getLogger(Account_Reader_Test.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Valid File Test Failed --- NullPointerException Exception thrown: " + ex);
                }

            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
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
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TEST RESULTS");
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
