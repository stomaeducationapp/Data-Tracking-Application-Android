package EncryptExport;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import XML.Medical_Reader;
import XML.Medical_Writer;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 16/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 18/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the test file for all testing related to the EncryptExport package (individual class tests, and full package run through tests)
 */

/* VERSION HISTORY
 * 16/10/2018 - Created test harnesses
 * 18/10/2018 - Fixed and completed testing
 */

/* REFERENCES
 * Android studio test research on https://drive.google.com/open?id=11L9vChloJtNgOaYAJQpCSJlA2hajMQCe
 * How to mock objects and check if a method was called learned from https://stackoverflow.com/questions/9841623/mockito-how-to-verify-method-was-called-on-an-object-created-within-a-method
 * How to create static maps learned from https://stackoverflow.com/questions/6802483/how-to-directly-initialize-a-hashmap-in-a-literal-way
 * How to set mock return parameters learned from https://stackoverflow.com/questions/2684630/how-can-i-make-a-method-return-an-argument-that-was-passed-to-it
 * Mock argument matcher for null learned from https://stackoverflow.com/questions/12707609/how-to-match-null-passed-to-parameter-of-classt-with-mockito
 * Mock argument matcher for files learned from https://stackoverflow.com/questions/1778744/using-mockitos-generic-any-method
 * And all related documentation on https://developer.android.com
 */

public class EncryptExportTest {

    private File in = mock(File.class);
    private File out = in; //We don't care what the files are since we never use them
    //Mock objects we will change each test
    private Factory f;
    private Retrieval r;
    private Encrypt e;
    private Medical_Reader mr;
    private Medical_Writer mw;

    /* FUNCTION INFORMATION
     * NAME - retrieveTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each function call/return value of the retrieve method in Retrieval.java
     */
    @Test
    public void retrieveTestCase1()
    {

        //First mock needed objects and tailor mock returns
        mr = mock(Medical_Reader.class);
        Map<String, String> mockMap = new HashMap<String, String>();

        try
        {
            when(mr.Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), anyString())).thenReturn(mockMap);

        }
        catch ( XML_Reader_Exception | NullPointerException e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        f = mock(Factory.class);
        when(f.Make_Medical_Reader()).thenReturn(mr);

        //Case 1: Retrieval with an empty map
        Retrieval testR = new Retrieval();
        Map<String, String> map = new HashMap<String, String>();
        map.put("TESTKEY", "TESTVALUE"); //Initialise to the opposite of what we want
        try
        {
            map = testR.retrieve(in, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mr, times(1)).Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull());
            verify(f, times(1)).Make_Medical_Reader();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was empty", mockMap, map);

    }

    @Test
    public void retrieveTestCase2()
    {
        //First mock needed objects and tailor mock returns
        Map<String, String> mockMap = new HashMap<String, String>(); //Create fake map
        mockMap.put("TESTKEY", "TESTVALUE");
        mr = mock(Medical_Reader.class);

        try
        {
            when(mr.Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull())).thenReturn(mockMap);
        }
        catch ( XML_Reader_Exception | NullPointerException e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        f = mock(Factory.class);
        when(f.Make_Medical_Reader()).thenReturn(mr);

        //Case 2: Retrieval with a map with values (could be valid/invalid - would be set up the same)
        Retrieval testR = new Retrieval();
        Map<String, String> map = null;

        try
        {
            map = testR.retrieve(in, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mr, times(1)).Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull());
            verify(f, times(1)).Make_Medical_Reader();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map had a key", true, map.containsKey("TESTKEY")); //Check that the map we got back has the values we put in
        assertEquals("Map had a value associated with key", "TESTVALUE", map.get("TESTKEY"));
    }

    /* FUNCTION INFORMATION
     * NAME - bookKeepingTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each function call/return value of the bookKeeping method in Retrieval.java
     */
    @Test
    public void bookKeepingTestCase1()
    {

        Map<String, String> mockMap = new HashMap<String, String>(); //Create fake map
        mockMap.put("TESTKEY", "TESTVALUE");
        mw = mock(Medical_Writer.class);
        try
        {
            when(mw.Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any())).thenReturn(true);
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        f = mock(Factory.class);
        when(f.Make_Medical_Writer()).thenReturn(mw);

        //Case 1: BookKeeping was successful (Only real cases are if external code returns correctly)
        Retrieval testR = new Retrieval();
        boolean success = false; //Initialise to the opposite of what we want
        try
        {
            success = testR.bookKeeping(out, mockMap, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mw, times(1)).Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any());
            verify(f, times(1)).Make_Medical_Writer();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was empty", true, success);

    }

    @Test
    public void bookKeepingTestCase2()
    {

        Map<String, String> mockMap = new HashMap<String, String>(); //Create fake map
        mockMap.put("TESTKEY", "TESTVALUE");
        mw = mock(Medical_Writer.class);
        try
        {
            when(mw.Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any())).thenReturn(false);
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        f = mock(Factory.class);
        when(f.Make_Medical_Writer()).thenReturn(mw);

        //Case 2: BookKeeping failed
        Retrieval testR = new Retrieval();
        boolean success = false; //Initialise to the opposite of what we want
        try
        {
            success = testR.bookKeeping(out, mockMap, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mw, times(1)).Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any());
            verify(f, times(1)).Make_Medical_Writer();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was empty", false, success);

    }

    /* FUNCTION INFORMATION
     * NAME - encryptHandlerTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each function call/return value of the encryptHandler method in Encrypt.java
     */
    @Test
    public void encryptHandlerTestCase1()
    {

        //First mock needed objects
        Map<String, String> map = new HashMap<String, String>(); //Create fake map
        map.put("", "");

        String mockMapString = "";
        for( Map.Entry<String, String> entry : map.entrySet() )
        {
            mockMapString = mockMapString + entry.getKey() + ":" + entry.getValue() + ",";
        }
        mockMapString = mockMapString + "\n"; //Set up what final string should look like

        //Case 1: Encrypt an empty map
        Encrypt testE = new Encrypt();
        String finalForm = "";
        try //Need to capture the required exceptions
        {
            byte[] encrypted = testE.encryptHandler(map); //Encrypt does not use any external code not designed by me or Android (Android is static an not based on user input - like a Context so does not need to be mocked)
            finalForm = testE.decrypt(encrypted); //Since the encrypted value can change each time depending on the key used (Coupled with a SecureRandom number) - asserting on the encrypted
            //is not viable - instead we test that what went in comes back out
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was empty", mockMapString, finalForm); //Since an empty map gets a newline added that's what we expect to come out

    }

    @Test
    public void encryptHandlerTestCase2()
    {

        //First mock needed objects and tailor mock returns
        Map<String, String> map = new HashMap<String, String>(); //Create fake map
        map.put("TESTKEY", "TESTVALUE");

        String mockMapString = "";
        for( Map.Entry<String, String> entry : map.entrySet() )
        {
            mockMapString = mockMapString + entry.getKey() + ":" + entry.getValue() + ",";
        }
        mockMapString = mockMapString + "\n"; //Set up what final string should look like

        //Case 2: Encrypt and invalid map
        Encrypt testE = new Encrypt();
        String finalForm = "";
        try //Need to capture the required exceptions
        {
            byte[] encrypted = testE.encryptHandler(map); //Encrypt does not use any external code not designed by me or Android (Android is static an not based on user input - like a Context so does not need to be mocked)
            finalForm = testE.decrypt(encrypted); //Since the encrypted value can change each time depending on the key used (Coupled with a SecureRandom number) - asserting on the encyrpted
            //is not viable - instead we test that what went in comes back out
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was invalid form", mockMapString, finalForm); //Since an invalid map has no tags picked up, we get a newline added so that's what we expect to come out


    }

    @Test
    public void encryptHandlerTestCase3()
    {

        //First mock needed objects and tailor mock returns

        Map<String, String> map = new HashMap<String, String>(); //Create fake map
        map.put("Entry_Time", "TESTVALUE");
        map.put("Location", "TESTVALUE");
        map.put("Bags", "TESTVALUE");
        map.put("Urine", "TESTVALUE");
        map.put("Hydration", "TESTVALUE");
        map.put("Wellbeing", "TESTVALUE");
        map.put("Medical_State", "TESTVALUE");

        String mockMapString = "";
        for( Map.Entry<String, String> entry : map.entrySet() )
        {
            mockMapString = mockMapString + entry.getKey() + ":" + entry.getValue() + ",";
        }
        mockMapString = mockMapString + "\n"; //Set up what final string should look like

        //Case 3: Encrypt a valid map
        Encrypt testE = new Encrypt();
        String finalForm = "";
        try //Need to capture the required exceptions
        {
            byte[] encrypted = testE.encryptHandler(map); //Encrypt does not use any external code not designed by me or Android (Android is static an not based on user input - like a Context so does not need to be mocked)
            finalForm = testE.decrypt(encrypted); //Since the encrypted value can change each time depending on the key used (Coupled with a SecureRandom number) - asserting on the encyrpted
            //is not viable - instead we test that what went in comes back out
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was valid form", mockMapString, finalForm);

    }

    /* FUNCTION INFORMATION
     * NAME - entirePackageTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each function call/return value of the handle method in Detector.java (which will run through all previous tests done in this file
     *           giving us a entire package test)
     */
    @Test
    public void entirePackageTestCase1() {

        //First mock needed objects and tailor mock returns
        f = mock(Factory.class);
        //Mocked map for encrypt (don't really need it) - mainly for testing normal values and seeing if exceptions are thrown for Android issues
        Map<String, String> map = new HashMap<String, String>(); //Create fake map
        map.put("Entry_Time", "TESTVALUE");
        map.put("Location", "TESTVALUE");
        map.put("Bags", "TESTVALUE");
        map.put("Urine", "TESTVALUE");
        map.put("Hydration", "TESTVALUE");
        map.put("Wellbeing", "TESTVALUE");
        map.put("Medical_State", "TESTVALUE");

        String mockMapString = "";
        for( Map.Entry<String, String> entry : map.entrySet() )
        {
            mockMapString = mockMapString + entry.getValue() + ",";
        }
        mockMapString = mockMapString + "\n"; //Set up what final string should look like

        //Next mock the reader to return our map
        mr = mock(Medical_Reader.class);
        try
        {
            when(mr.Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull())).thenReturn(map);
        }
        catch ( XML_Reader_Exception | NullPointerException e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }

        when(f.Make_Medical_Reader()).thenReturn(mr);

        mw = mock(Medical_Writer.class);
        try
        {
            when(mw.Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any())).thenReturn(true);
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        when(f.Make_Medical_Writer()).thenReturn(mw);
        when(f.makeRetrieval()).thenReturn(new Retrieval()); //Since we have successfully tested these (any mocked the objects inside them - we can just construct them
        when(f.makeEncrypt()).thenReturn(new Encrypt());

        //Case 1: Entire process success
        Detector testD = new Detector();
        boolean success = false; //Initialise to the opposite of what we want
        try
        {
            success = testD.handle(in, out, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mr, times(1)).Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull());
            verify(f, times(1)).Make_Medical_Reader();
            verify(mw, times(1)).Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any());
            verify(f, times(1)).Make_Medical_Writer();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Successful export", true, success);



    }

    @Test
    public void entirePackageTestCase2()
    {

        //First mock needed objects and tailor mock returns
        f = mock(Factory.class);

        //Mocked map for encrypt (don't really need it) - mainly for testing normal values and seeing if exceptions are thrown for Android issues
        Map<String, String> map = new HashMap<String, String>(); //Create fake map
        map.put("Entry_Time", "TESTVALUE");
        map.put("Location", "TESTVALUE");
        map.put("Bags", "TESTVALUE");
        map.put("Urine", "TESTVALUE");
        map.put("Hydration", "TESTVALUE");
        map.put("Wellbeing", "TESTVALUE");
        map.put("Medical_State", "TESTVALUE");

        String mockMapString = "";
        for( Map.Entry<String, String> entry : map.entrySet() )
        {
            mockMapString = mockMapString + entry.getKey() + ":" + entry.getValue() + ",";
        }
        mockMapString = mockMapString + "\n"; //Set up what final string should look like

        //Next mock the reader to return our map
        mr = mock(Medical_Reader.class);
        try
        {
            when(mr.Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull())).thenReturn(map);
        }
        catch ( XML_Reader_Exception | NullPointerException e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        when(f.Make_Medical_Reader()).thenReturn(mr);
        when(f.makeRetrieval()).thenReturn(new Retrieval()); //Since we have successfully tested these (any mocked the objects inside them - we can just construct them
        when(f.makeEncrypt()).thenReturn(new Encrypt());

        mw = mock(Medical_Writer.class);
        try
        {
            when(mw.Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any())).thenReturn(false);
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }
        when(f.Make_Medical_Writer()).thenReturn(mw);

        //Case 2: Entire process fails
        Detector testD = new Detector();
        boolean success = true; //Initialise to the opposite of what we want
        try
        {
            success = testD.handle(in, out, f); //Create our object and call it with the mocked factory
        }
        catch (EncryptHandlerException e)
        {
            System.out.println(e.getMessage());
        }

        //Now we verify all mocked methods were called
        try
        {
            verify(mr, times(1)).Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull());
            verify(f, times(1)).Make_Medical_Reader();
            verify(mw, times(1)).Write_File((File) any(), ArgumentMatchers.<String, String>anyMap() , ArgumentMatchers.<XML_Writer.Tags_To_Write>any());
            verify(f, times(1)).Make_Medical_Writer();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Failed export", false, success);

    }

}