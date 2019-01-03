package Integration.StreamThree;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import MedicalReview.ReviewData;
import MedicalReview.ReviewHandler;
import Observers.Export_Data;
import Observers.Time_Observer;

import static org.junit.Assert.assertEquals;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 03/01/2018
 * LAST MODIFIED BY - Jeremy Dunnet 03/01/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is part of the integration test for StreamThree (24hrReview) - this test is to make sure all additional classes apart from the Activity work with the new integration change
 */

/* VERSION HISTORY
 * 03/01/2018 - Created test harnesses and completed testing
 */

/* REFERENCES
 * Android studio test research on https://drive.google.com/open?id=11L9vChloJtNgOaYAJQpCSJlA2hajMQCe
 * And all related documentation on https://developer.android.com
 */

public class ReviewExtraTest {

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
     * PURPOSE - These are the tests that performs asserts on each end result of the integration of Review Handler extra classes (not the main activity) with XML
     */
    @Test
    public void StreamThreeTestCase1() {

        ReviewData r = f.Make_Review_Data_Reader();
        Map<String, String> map = r.loadData();

        //Check that the expected amount of entries are present
        assertEquals("Map loaded with values", map.get("Entries_Retrieved"), "3");

    }

    /* FUNCTION INFORMATION
     * NAME - StreamOneTestCaseX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on each end result of the integration of Review Handler extra classes (not the main activity) with XML
     */
    @Test
    public void StreamThreeTestCase2() {

        ReviewHandler h = f.Make_Stoma_Review_Handler();
        boolean success = h.generateReview();

        //Check no exceptions thrown - we need to do activity testing to confirm graphs load correctly
        assertEquals("Operation successful", success, true);

    }
}
