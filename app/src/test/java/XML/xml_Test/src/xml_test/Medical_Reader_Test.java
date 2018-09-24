package xml_test;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import XML.Medical_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;

/**
 * @author Patrick Created on Sep 22, 2018
 */
public class Medical_Reader_Test {
    
    /*****************************************************************
     * PLEASE CHANGE THESE VALUES EACH NEW DAY
     *****************************************************************/
    private final int todays_date_date = 24;
    private final int yesterdays_date_date = 23;
    
    
    private int tests_Passed;
    private int tests_Run;
    private XML_Reader xML_Reader;

    public void Run_Tests() {
        xML_Reader = new Medical_Reader();
        System.out.println("***************************************************"
                + System.lineSeparator()
                + "TESTING MEDICAL_READER");

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
                tags.add(XML_Reader.Tags_To_Read.Last_Entry);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    if (!info.isEmpty()) {
                        System.out.println("Valid Empty File Test Passed --- Non Empty Map Given Back");
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
            tests_Run++;
            if (Open_File(xpp, "valid_medical_file_empty_info.xml")) {
                //Asking for all, should return keys only
                //Bags, Urine, Hydration, Wellbeing, Location, Entry_Time, Medical_State,
                List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
                tags.add(XML_Reader.Tags_To_Read.Bags);
                tags.add(XML_Reader.Tags_To_Read.Hydration);
                tags.add(XML_Reader.Tags_To_Read.Wellbeing);
                tags.add(XML_Reader.Tags_To_Read.Location);
                tags.add(XML_Reader.Tags_To_Read.Entry_Time);
                tags.add(XML_Reader.Tags_To_Read.Medical_State);
                tags.add(XML_Reader.Tags_To_Read.Last_Entry);
                try {
                    Map<String, String> info = xML_Reader.Read_File(xpp, tags, null);
                    if (!info.isEmpty() && info.containsKey(XML_Reader.Tags_To_Read.Urine.toString())) {
                        System.out.println("Valid Empty File Test Failed --- Map Given Back Urine Key when not asked for");
                    } else if (!info.isEmpty() && !info.containsKey(XML_Reader.Tags_To_Read.Urine.toString())) {
                        System.out.println("Valid Empty File Test Passed --- Map Given Back Without Urine Key when not asked for");
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
                    + "Testing Valid File for First Entry" + System.lineSeparator()
            );
            xpp = factory.newPullParser();

            tests_Run += 8;
            if (Open_File(xpp, "valid_medical_file.xml")) {
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
                    //Should return first Entry
                    if (!info.isEmpty()) {
                        System.out.println("Valid File for First Entry --- Non Empty");
                        tests_Passed++;
                        if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + "1").equals("a")) {
                            System.out.println("Valid File for First Entry Passed --- Location value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + "1").equals("b")) {
                            System.out.println("Valid File for First Entry Passed --- Bags value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + "1").equals("c")) {
                            System.out.println("Valid File for First Entry Passed --- Urine value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + "1").equals("d")) {
                            System.out.println("Valid File for First Entry Passed --- Hydration value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + "1").equals("e")) {
                            System.out.println("Valid File for First Entry Passed --- Wellbeing value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + "1").equals("f")) {
                            System.out.println("Valid File for First Entry Passed --- Medical_State value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + "1").equals("14-"+yesterdays_date_date+ "-9-2018")) {
                            System.out.println("Valid File for First Entry Passed --- Entry_Time value is correct");
                            tests_Passed++;
                        } else {
                            System.out.println("Valid File for First Entry Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                        }
                    } else {
                        System.out.println("Valid File for First Entry Failed --- Empty Map Given Back");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File for First Entry Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("Valid File for First Entry Failed --- NullPointerException Exception thrown: " + ex);
                }
            } else {
                System.out.println("Valid File for First Entry Failed --- Failed to Open File");
            }

            //Valid Layout and Information
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File for Export" + System.lineSeparator()
            );

            xpp = factory.newPullParser();

            tests_Run += 22;
            if (Open_File(xpp, "valid_medical_file.xml")) {
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
                    //Should return first Entry
                    if (!info.isEmpty()) {
                        System.out.println("Valid File Export Data Passed --- Non Empty");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Empty Map Given Back");
                    }
                    int ii = 1;
                    if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + ii).equals("a")) {
                        System.out.println("Valid File for Export Data Passed --- Location value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Location value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + ii).equals("b")) {
                        System.out.println("Valid File for Export Data Passed --- Bags value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Bags value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + ii).equals("c")) {
                        System.out.println("Valid File for Export Data Passed --- Urine value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Urine value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + ii).equals("d")) {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + ii).equals("e")) {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + ii).equals("f")) {
                        System.out.println("Valid File for Export Data Passed --- Medical_State value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Medical_State value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + ii).equals("14-"+yesterdays_date_date+"-9-2018")) {
                        System.out.println("Valid File for Export Data Passed --- Entry_Time value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                    }
                    ii = 2;
                    if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + ii).equals("g")) {
                        System.out.println("Valid File for Export Data Passed --- Location value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Location value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + ii).equals("h")) {
                        System.out.println("Valid File for Export Data Passed --- Bags value is correct");
                        tests_Passed++;
                    } else
                    {
                        System.out.println("Valid File for Export Data Passed --- Bags value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + ii).equals("i")) {
                        System.out.println("Valid File for Export Data Passed --- Urine value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Urine value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + ii).equals("j")) {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + ii).equals("k")) {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + ii).equals("l")) {
                        System.out.println("Valid File for Export Data Passed --- Medical_State value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Medical_State value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + ii).equals("4-"+todays_date_date+"-9-2018")) {
                        System.out.println("Valid File for Export Data Passed --- Entry_Time value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                    }
                    ii = 3;
                    if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + ii).equals("m")) {
                        System.out.println("Valid File for Export Data Passed --- Location value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Location value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + ii).equals("n")) {
                        System.out.println("Valid File for Export Data Passed --- Bags value is correct");
                        tests_Passed++;
                    } else
                    {
                        System.out.println("Valid File for Export Data Passed --- Bags value is correct");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + ii).equals("o")) {
                        System.out.println("Valid File for Export Data Passed --- Urine value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Urine value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + ii).equals("p")) {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Hydration value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + ii).equals("q")) {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Passed --- Wellbeing value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + ii).equals("r")) {
                        System.out.println("Valid File for Export Data Passed --- Medical_State value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Medical_State value is Wrong");
                    }
                    if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + ii).equals("14-20-9-2018")) {
                        System.out.println("Valid File for Export Data Passed --- Entry_Time value is correct");
                        tests_Passed++;
                    } else {
                        System.out.println("Valid File for Export Data Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                    }

                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File for Export Data Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("Valid File for Export Data Failed --- NullPointerException Exception thrown: " + ex);
                }
            } else {
                System.out.println("Valid File for Export Data Failed --- Failed to Open File");
            }

            //Valid Layout and Information
            // NOTE THE TEST VALUE TO BE GIVEN BACK WONT BE CORRECT IF DONE ON A DIFFERENT DAY DUE TO THE CHECK BASED ON TIME
            // THE XML FILE 'valid_medical_file.xml' entries 1 and 2 will need to be updated. The <Entry_Time> value will need to be updated to the day before for the current day test
            System.out.println("***************************************************"
                    + System.lineSeparator()
                    + "Testing Valid File for Daily Review" + System.lineSeparator()
            );
            xpp = factory.newPullParser();

            tests_Run += 16;
            if (Open_File(xpp, "valid_medical_file.xml")) {
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
                    //Should return first Entry

                    if (!info.isEmpty()) {
                        System.out.println("Valid File for Daily Review Passed --- Non Empty");
                        tests_Passed++;
                        if (info.get(XML_Reader.Tags_To_Read.Entries_Retrieved.toString()).equals("2")) {
                            System.out.println("Valid File for Daily Review Passed --- Number of Entries value is correct");
                            tests_Passed++;
                        }
                        int ii = 1;
                        if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + ii).equals("a")) {
                            System.out.println("Valid File for Daily Review Passed --- Location value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + ii).equals("b")) {
                            System.out.println("Valid File for Daily Review Passed --- Bags value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + ii).equals("c")) {
                            System.out.println("Valid File for Daily Review Passed --- Urine value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + ii).equals("d")) {
                            System.out.println("Valid File for Daily Review Passed --- Hydration value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + ii).equals("e")) {
                            System.out.println("Valid File for Daily Review Passed --- Wellbeing value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + ii).equals("f")) {
                            System.out.println("Valid File for Daily Review Passed --- Medical_State value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + ii).equals("14-"+yesterdays_date_date+"-9-2018")) {
                            System.out.println("Valid File for Daily Review Passed --- Entry_Time value is correct");
                            tests_Passed++;
                        } else {
                            System.out.println("Valid File for Daily Review Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                        }
                        ii++;
                        if (info.get(XML_Reader.Tags_To_Read.Location.toString() + "-" + ii).equals("g")) {
                            System.out.println("Valid File for Daily Review Passed --- Location value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Bags.toString() + "-" + ii).equals("h")) {
                            System.out.println("Valid File for Daily Review Passed --- Bags value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Urine.toString() + "-" + ii).equals("i")) {
                            System.out.println("Valid File for Daily Review Passed --- Urine value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Hydration.toString() + "-" + ii).equals("j")) {
                            System.out.println("Valid File for Daily Review Passed --- Hydration value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Wellbeing.toString() + "-" + ii).equals("k")) {
                            System.out.println("Valid File for Daily Review Passed --- Wellbeing value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Medical_State.toString() + "-" + ii).equals("l")) {
                            System.out.println("Valid File for Daily Review Passed --- Medical_State value is correct");
                            tests_Passed++;
                        }
                        if (info.get(XML_Reader.Tags_To_Read.Entry_Time.toString() + "-" + ii).equals("4-"+todays_date_date+"-9-2018")) {
                            System.out.println("Valid File for Daily Review Passed --- Entry_Time value is correct");
                            tests_Passed++;
                        } else {
                            System.out.println("Valid File for Daily Review Failed --- Entry_Time value is Wrong May need to update test value to new date entered in the xml file as that needs to change for the daily review");
                        }
                    } else {
                        System.out.println("Valid File for Daily Review Failed --- Empty Map Given Back, Check dates as they are based on current day of testing");
                    }
                } catch (XML_Reader_Exception ex) {
                    System.out.println("Valid File for Daily Review Failed --- XML_Reader_Exception Exception thrown: " + ex);
                } catch (NullPointerException ex) {
                    System.out.println("Valid File for Daily Review Failed --- NullPointerException Exception thrown: " + ex);
                }
            } else {
                System.out.println("Valid File for Daily Review Failed --- Failed to Open File");
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
        Xml_Test.passed += tests_Passed;
        Xml_Test.total += tests_Run;
    }
}
