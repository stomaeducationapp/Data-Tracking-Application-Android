package xml_test;

import XML.XML_Writer;
import XML.Account_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick Created on Oct 1, 2018
 */
public class Account_Writer_Test {

    private int tests_Passed;
    private int tests_Run;
    private XML_Writer xML_Writer;
    private File file;

    public void Run_Tests() {
        xML_Writer = new Account_Writer();
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TESTING ACCOUNT_WRITER");
        tests_Run += 4;
        try {
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "1. Testing Empty File and Null/Empty Parameters"
                    + System.lineSeparator());
            if (Open_File("empty_file.xml")) {
                tests_Passed++;
                //empty file should do nothing
                Map<String, String> values = new HashMap<>();
                values.put(XML_Writer.Tags_To_Write.Name.toString(), "bob");
                XML_Writer.Tags_To_Write tag = XML_Writer.Tags_To_Write.Modify;
                try {
                    xML_Writer.Write_File(file, values, tag);
                } catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception ex) {
                    tests_Passed++;
                    System.out.println("Empty File, valid parameters Passed: " + ex);
                    System.out.flush();
                }
                //Will need to test for sending nulls etc
                try {
                    xML_Writer.Write_File(file, null, tag);
                } catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception ex) {
                    tests_Passed++;
                    System.out.println("Empty File, Null Values Map Passed: " + ex);
                    System.out.flush();
                }

                try {
                    xML_Writer.Write_File(file, values, null);
                } catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception ex) {
                    tests_Passed++;
                    System.out.println("Empty File, Null task enum Passed: " + ex);
                    System.out.flush();
                }

            } else {
                System.out.println("Failed to open File for Test 1");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("xml_test.Account_Writer_Test.Run_Tests()");
            System.out.flush();
        }

        try {

            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "2. Testing Added Tags to a new file (Create Command), with no data in the map"
                    + System.lineSeparator());
            tests_Run += 1;
            if (Open_File("account_writer_target_new_file.xml")) {
                XML_Writer.Tags_To_Write tag = XML_Writer.Tags_To_Write.Create;
                Map<String, String> values = new HashMap<>();
                if (xML_Writer.Write_File(file, values, tag)) {
                    tests_Passed++;
                    System.out.println("Create New layout, no map data Passed");
                    System.out.flush();
                } else {
                    System.out.println("Create New layout, no map data Failed");
                }

            } else {
                System.out.println("Failed to open File for Test 2");
            }

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("xml_test.Account_Writer_Test.Run_Tests()");
            System.out.flush();
        }

                try {

            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "3. Testing Modifying Tags in a file (Modify Command)"
                    + System.lineSeparator());
            tests_Run += 1;
            if (Open_File("account_writer_target_state_change.xml")) {
                //Name, Gamification, Notification, State, Last_Daily_Review_Date
                XML_Writer.Tags_To_Write tag = XML_Writer.Tags_To_Write.Modify;
                Map<String, String> values = new HashMap<>();
                values.put(XML_Writer.Tags_To_Write.Name.toString(), "name1");
                values.put(XML_Writer.Tags_To_Write.Gamification.toString(), "lots");
                values.put(XML_Writer.Tags_To_Write.Notification.toString(), "all");
                values.put(XML_Writer.Tags_To_Write.State.toString(), "blue");
                values.put(XML_Writer.Tags_To_Write.Last_Daily_Review_Date.toString(), "today");
                if (xML_Writer.Write_File(file, values, tag)) {
                    tests_Passed++;
                    System.out.println("Create New layout, no map data Passed");
                    System.out.flush();
                } else {
                    System.out.println("Create New layout, no map data Failed");
                }

            } else {
                System.out.println("Failed to open File for Test 2");
            }

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("xml_test.Account_Writer_Test.Run_Tests()");
            System.out.flush();
        }
        
        Print_Results();
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
        Xml_Test.passed += tests_Passed;
        Xml_Test.total += tests_Run;
    }

    private Boolean Open_File(String fileName) {
        Boolean pass = false;
        System.out.println("opening file");
        file = new File("Test_XML/" + fileName);
        if (file.exists()) {
            pass = true;
        }

        return pass;
    }
}
