package xml_test;

import XML.Login_Reader;
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
 * @author Patrick Created on Sep 20, 2018
 */
public class Login_Reader_Test {

    private int tests_Passed;
    private int tests_Run;
    private XML_Reader xML_Reader;

    public void Run_Tests() {
        xML_Reader = new Login_Reader();
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TESTING LOGIN_READER");

        tests_Passed = 0;
        tests_Run = 0;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            //Testing file can be opened first
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Empty File"
                    + System.lineSeparator());
            tests_Run += 2;
            if (Open_File(xpp, "empty_file.xml")) { // Do test otherwise failure of rhat set of tests due to file not found

                tests_Passed++;
                System.out.println("File Opened Test Passed --- Not Null");
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                try {
                    xML_Reader.Read_File(xpp, tags, "Bob");
                    System.out.println("Empty File Test Failed --- Empty File didn't Throw XML_Reader_Exception Exception");
                } catch (XML_Reader_Exception ex) {
                    tests_Passed++;
                    System.out.println("Empty File Test Passed --- XML_Reader_Exception Exception thrown correctly: " + ex);
                }
            } else {
                System.out.println("Empty File Test Failed --- Failed to Open File");
            }
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Null Account Name"
                    + System.lineSeparator());
            tests_Run++;
            if (Open_File(xpp, "empty_file.xml")) { // Do test otherwise failure of rhat set of tests due to file not found
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Name);
                try {
                    xML_Reader.Read_File(xpp, tags, null);
                    System.out.println("Null Account Test Failed --- Empty File didn't Throw XML_Reader_Exception Exception");
                } catch (NullPointerException ex) {
                    tests_Passed++;
                    System.out.println("Null Account Test Passed --- XML_Reader_Exception Exception thrown correctly: " + ex);
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Null Account Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                }
            } else {
                System.out.println("Null Account Test Failed --- Failed to Open File");
            }

            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File Empty Information" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 2;
            if (Open_File(xpp, "valid_login_file_empty_info.xml")) {
                try {
                    List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                    try {
                        xML_Reader.Read_File(xpp, tags, "Bob");
                        System.out.println("Valid Empty File Test Failed --- Empty File didn't Throw Null Pointer Exception");
                    } catch (XML_Reader_Exception ex) {
                        System.out.println("Valid Empty File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                    }
                } catch (NullPointerException ex) {
                    System.out.println("Valid Empty File Test Passed --- NullPointerException Exception thrown Correctly: " + ex);
                    tests_Passed++;
                }
                //Asking for all, should return Nothing
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Account_Name);
                tags.add(XML_Reader.Tags_To_Read.Password);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, "Bob");
                    if (!info.isEmpty() && info.containsKey("Account_Name") && info.containsKey("Password")) {
                        System.out.println("Valid Empty File Test Failed --- Map Given Back with Keys");
                    } else {
                        System.out.println("Valid Empty File Test Passed --- Empty Map Given Back");
                        tests_Passed++;
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid Empty File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("Valid Empty File Test Failed --- NullPointerException Exception thrown: " + ex);
                    Logger.getLogger(Account_Reader_Test.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("Valid Empty File Test Failed --- Failed to Open File");
            }

            //Valid layout and information, Asking for all
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File for Bob" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 3;
            if (Open_File(xpp, "valid_login_file.xml")) {
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Account_Name);
                tags.add(XML_Reader.Tags_To_Read.Password);
                try {
                    //Bob first
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, "Bob");
                    if (!info.isEmpty()) {
                        System.out.println("Valid File Test (Bob) Passed --- Non Empty Map Given Back");
                        tests_Passed++;
                        if (info.get("Account_Name").equals("Bob")) {
                            tests_Passed++;
                            System.out.println("Valid File Test (Bob) Passed --- Valid Name value Given Back");
                        }
                        if (info.get("Password").equals("78")) {
                            tests_Passed++;
                            System.out.println("Valid File Test (Bob) Passed --- Valid Password value Given Back");
                        }

                    } else {
                        System.out.println("Valid File Test (Bob) Failed --- Empty Map Given Back");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);

                } catch (NullPointerException ex) {
                    Logger.getLogger(Account_Reader_Test.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Valid File Test (Alice) Failed --- NullPointerException Exception thrown: " + ex);
                }

            } else {
                System.out.println("Valid Empty File Test (Alice) Failed --- Failed to Open File");
            }

            //Valid layout and information, Asking for all
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File for Alice" + System.lineSeparator()
            );
            xpp = factory.newPullParser();
            tests_Run += 3;
            if (Open_File(xpp, "valid_login_file.xml")) {
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Account_Name);
                tags.add(XML_Reader.Tags_To_Read.Password);
                try {
                    //Bob first
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, "Alice");
                    if (!info.isEmpty()) {
                        System.out.println("Valid File Test (Alice) Passed --- Non Empty Map Given Back");
                        tests_Passed++;
                        if (info.get("Account_Name").equals("Alice")) {
                            tests_Passed++;
                            System.out.println("Valid File Test (Alice) Passed --- Valid Name value Given Back");
                        }
                        if (info.get("Password").equals("42")) {
                            tests_Passed++;
                            System.out.println("Valid File Test (Alice) Passed --- Valid Password value Given Back");
                        }
                    } else {
                        System.out.println("Valid File Test (Alice) Failed --- Empty Map Given Back");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File Test Failed --- XML_Reader_Exception Exception thrown: " + ex);

                } catch (NullPointerException ex) {
                    Logger.getLogger(Account_Reader_Test.class
                            .getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Valid File Test (Alice) Failed --- NullPointerException Exception thrown: " + ex);
                }

            } else {
                System.out.println("Valid Empty File Test (Alice) Failed --- Failed to Open File");
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
