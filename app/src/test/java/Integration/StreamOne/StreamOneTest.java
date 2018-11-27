package Integration.StreamOne;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import Observers.Export_Data;
import Observers.Time_Observer;

import static org.junit.Assert.assertEquals;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 27/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 27/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the test file for all testing related to the EncryptExport package integration with it's connected features (XML to read files and Observers to call the encrypt)
 */

/* VERSION HISTORY
 * 27/11/2018 - Created test harnesses
 * 28/11/2018 - Fixed and completed testing
 */

/* REFERENCES
 * Android studio test research on https://drive.google.com/open?id=11L9vChloJtNgOaYAJQpCSJlA2hajMQCe
 * And all related documentation on https://developer.android.com
 */

public class StreamOneTest {

    private Factory f;

    /* FUNCTION INFORMATION
     * NAME - setUp
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that creates the initial factory object we will reuse
     */
    @Before
    public void setUp()
    {
        f = Factory.Get_Factory();
    }

    /* FUNCTION INFORMATION
     * NAME - StreamOneTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each end result of the integration of Encrypt/Export with observers
     */
    @Test
    public void StreamOneTestCase1()
    {

        boolean valid = false;
        String message = "";

        //First we create the fileMap we need for the test
        Map<Time_Observer.Files, File> fileMap =  new HashMap<Time_Observer.Files, File>();
        fileMap.put(Time_Observer.Files.Medical, new File("/app/src/test/java/Integration/StreamOne/iso_valid_file.xml"));

        try
        {
            Export_Data e = (Export_Data) f.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
            valid  = e.Notify(fileMap);
            message = e.dTest();
        }
        catch (NullPointerException e)
        {
            System.out.println("Failed to read in user data" + e.getMessage());
        }

        //Now we assert the final value is what we expected
        assertEquals("Operation was successful", true, valid);
        System.out.println(message);

    }
}
