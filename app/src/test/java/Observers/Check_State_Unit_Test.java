package Observers;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * <h>Check_State_Unit_Test</h>
 * Tests the logic of Check_State Class to the fullest extent possible
 *
 * @author Patrick Crockford
 * @version 1.0
 * @since 27-Aug-2018
 * <h>Note</h>
 * Cannot test a valid FileInputStream or FileOutputStream class in the unit testing as it requires a context
 * which is only available in an activity class
 * This will be done during integration testing at a later date and has been noted in the Test Form
 */
public class Check_State_Unit_Test {

    private State_Observer state_Observer;

    /**
     * Initialize.
     */
    @Before
    public void initialize() {
        state_Observer = new Check_State();
    }

    /**
     * InputStream and OutputStream are Null
     */
    @Test(expected = NullPointerException.class)
    public void Input_Stream_And_Output_Stream_Null() {
        assertEquals(state_Observer.Notify(null, null), false);
    }
}