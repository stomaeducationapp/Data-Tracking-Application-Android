package Integration.StreamOne;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import EncryptExport.EncryptHandlerException;
import EncryptExport.Retrieval;
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

public class StreamOneTest {

    private File in = mock(File.class);
    private File out = in; //We don't care what the files are since we never use them
    //Mock objects we will change each test
    private Factory f;
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
        when(f.Make_Reader(Factory.XML_Reader_Choice.Medical)).thenReturn(mr);

        //Case 1: Retrieval with an empty map
        Retrieval testR = new Retrieval();
        Map<String, String> map = new HashMap<String, String>();
        map.put("TESTKEY", "TESTVALUE"); //Initialise to the opposite of what we want

        //Now we verify all mocked methods were called
        try
        {
            verify(mr, times(1)).Read_File((File) any(), ArgumentMatchers.<XML_Reader.Tags_To_Read>anyList(), (String) isNull());
            verify(f, times(1)).Make_Reader(Factory.XML_Reader_Choice.Medical);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Map was empty", mockMap, map);

    }
}
