package Factory;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <h>Factory_Unit_Test</h>
 * Tests the logic of Factory Class to the fullest extent possible
 *
 * @author Patrick Crockford
 * @version 1.0
 * @since 03-Sep-2018
 * <h>Note</h>
 * Only Testing Constructor and Singleton pattern requirements as the 'Make' methods require external
 * packages and classes that haven't been created yet. This will need to be either unit tested after all
 * have been created, or covered during integration testing
 */
public class Factory_Unit_Test {
    /**
     * Valid Object Creation
     */
    @Test
    public void Test_Factory_Construction() {
        Factory factory = Factory.Get_Factory();
        assertNotNull(factory);
    }

    /**
     * Singleton Pattern Adherence
     */
    @Test
    public void Test_Factory_Is_Singleton() {
        Factory factory1 = Factory.Get_Factory();
        Factory factory2 = Factory.Get_Factory();
        assertEquals(factory1.hashCode(), factory2.hashCode());
    }
}