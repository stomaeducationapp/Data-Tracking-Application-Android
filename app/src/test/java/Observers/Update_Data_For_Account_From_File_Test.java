package Observers;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * <h>Update_Data_For_Account_From_File_Test</h>
 * Tests the logic of Update_Data_For_Account_From_File Class to the fullest extent possible
 *
 * @author Patrick Crockford
 * @version 1.0
 * @since 29-Aug-2018
 * <h>Note</h>
 * Cannot test a valid FileInputStream class in the unit testing as it requires a context
 * which is only available in an activity class
 * This will be done during integration testing at a later date and has been noted in the Test Form
 */
public class Update_Data_For_Account_From_File_Test {

    private Update_Data_Observer update_dataObserver;

    /**
     * Initialize.
     */
    @Before
    public void initialize() {
        update_dataObserver = new Update_Data_For_Account_From_File();
    }

    /**
     * File stream null.
     */
    @Test(expected = NullPointerException.class)
    public void File_Stream_Null() {
        assertEquals(update_dataObserver.Update_Information(null), false);
    }
}