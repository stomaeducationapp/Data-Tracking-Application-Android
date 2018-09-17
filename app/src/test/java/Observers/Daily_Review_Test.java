package Observers;

import org.junit.Before;
import org.junit.Test;

import Factory.Factory;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * <h>Daily_Review_Test</h>
 * Tests the logic of Daily_Review Class to the fullest extent possible
 *
 * @author Patrick Crockford
 * @version 1.0
 * @since 29-Aug-2018
 * <h>Note</h>
 * Cannot test a valid FileInputStream class in the unit testing as it requires a context
 * which is only available in an activity class
 * This will be done during integration testing at a later date and has been noted in the Test Form
 */
public class Daily_Review_Test {

    private Time_Observer time_Observer;

    /**
     * Initialize.
     */
    @Before
    public void initialize() {
        time_Observer = new Daily_Review(Factory.Get_Factory());
    }

    /**
     * File streams null.
     */
    @Test(expected = NullPointerException.class)
    public void File_Stream_Null() {
        assertEquals(time_Observer.Notify(null, null), false);
    }
}