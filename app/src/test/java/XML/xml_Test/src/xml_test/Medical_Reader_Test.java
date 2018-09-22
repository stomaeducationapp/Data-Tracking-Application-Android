package xml_test;

import XML.Account_Reader;
import XML.Medical_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * @author Patrick Created on Sep 22, 2018
 */
public class Medical_Reader_Test {

    private int tests_Passed;
    private int tests_Run;
    private XML_Reader xML_Reader;

    public void Run_Tests() {
        xML_Reader = new Medical_Reader();
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
                tags.add(XML_Reader.Tags_To_Read.Bags);
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
                    + "Testing Valid File Empty Information For Latest Entry" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 3;
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
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
            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
            }
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
                try {
                    List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                    tags.add(XML_Reader.Tags_To_Read.Name);
                    try {
                        xML_Reader.Read_File(xpp, tags, null);
                        System.out.println("Valid Empty File Test Failed --- Empty File didn't Throw Null Pointer Exception");
                    } catch (XML_Reader_Exception ex) {
                        System.out.println("Valid Empty File Test Passed --- XML_Reader_Exception Exception thrown: " + ex);
                        tests_Passed++;
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Valid Empty File Test Failed --- NullPointerException Exception thrown Correctly: " + ex);
                    
                }
            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
            }
            
            
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
                //Asking for all, should return keys only
                //Bags, Urine, Hydration, Wellbeing, Location, Entry_Time, Medical_State,
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Bags);
                tags.add(XML_Reader.Tags_To_Read.Urine);
                tags.add(XML_Reader.Tags_To_Read.Hydration);
                tags.add(XML_Reader.Tags_To_Read.Wellbeing);
                tags.add(XML_Reader.Tags_To_Read.Location);
                tags.add(XML_Reader.Tags_To_Read.Entry_Time);
                tags.add(XML_Reader.Tags_To_Read.Medical_State);
                tags.add(XML_Reader.Tags_To_Read.Last_Entry);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    if (!info.isEmpty() && info.containsKey("Bags" + 1) && info.containsKey("Urine" + 1) && info.containsKey("Hydration" + 1) && info.containsKey("Wellbeing" + 1)
                            && info.containsKey("Location" + 1) && info.containsKey("Entry_Time" + 1) && info.containsKey("Medical_State" + 1) && info.containsKey("Entries_Retrieved")) {
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

            //Valid layout but no information in any of them, Dont want anything (list is empty)\
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File Empty Information For Export Data" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run++;
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
                //Asking for all, should return keys only
                //Bags, Urine, Hydration, Wellbeing, Location, Entry_Time, Medical_State,
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Bags);
                tags.add(XML_Reader.Tags_To_Read.Urine);
                tags.add(XML_Reader.Tags_To_Read.Hydration);
                tags.add(XML_Reader.Tags_To_Read.Wellbeing);
                tags.add(XML_Reader.Tags_To_Read.Location);
                tags.add(XML_Reader.Tags_To_Read.Entry_Time);
                tags.add(XML_Reader.Tags_To_Read.Medical_State);
                tags.add(XML_Reader.Tags_To_Read.Export_Data);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    //Should return 3 entires
                    if (!info.isEmpty() && info.containsValue("3")) {
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
//Valid layout but no information in any of them, Dont want anything (list is empty)\
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File Empty Information For Daily" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run++;
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
                //Asking for all, should return keys only
                //Bags, Urine, Hydration, Wellbeing, Location, Entry_Time, Medical_State,
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Bags);
                tags.add(XML_Reader.Tags_To_Read.Urine);
                tags.add(XML_Reader.Tags_To_Read.Hydration);
                tags.add(XML_Reader.Tags_To_Read.Wellbeing);
                tags.add(XML_Reader.Tags_To_Read.Location);
                tags.add(XML_Reader.Tags_To_Read.Entry_Time);
                tags.add(XML_Reader.Tags_To_Read.Medical_State);
                tags.add(XML_Reader.Tags_To_Read.Daily_Data);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    //Should return 3 entires
                    if (!info.isEmpty()) {
                        System.out.println("Valid Empty File Test Failed --- Map Given Back with Information");
                    } else {
                        System.out.println("Valid Empty File Test Passed --- Empty Map Given Back");
                        tests_Passed++;
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid Empty File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    Logger.getLogger(Account_Reader_Test.class
                    .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Valid Empty File Test Failed --- NullPointerException Exception thrown: " + ex);
                }

            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
            }
            
            
            
            
            
            //Valid Layout and Information
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File for " + System.lineSeparator()
            );

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
