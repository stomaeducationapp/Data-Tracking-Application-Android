package MedicalStates;

import org.junit.Test;
import static org.junit.Assert.*;

public class IndividualStatesTest {
    /**
     * Testing available Green State methods
     */
    @Test
    public void Test_Green_State() {
        //middle case
        StomaState state = new GreenState(3);
        assertNotNull(state);
        assertEquals(3.0, state.getStateVal(), 0.0);
        assertEquals("Green", state.getState());

        //lower edge case
        state = new GreenState(1);
        assertNotNull(state);
        assertEquals(1.0, state.getStateVal(), 0.0);
        assertEquals("Green", state.getState());

        //upper edge case
        state = new GreenState(4);
        assertNotNull(state);
        assertEquals(4.0, state.getStateVal(), 0.0);
        assertEquals("Green", state.getState());
    }

    /**
     * Testing available Yellow State methods
     */
    @Test
    public void Test_Yellow_State() {
        //middle case
        StomaState state = new YellowState(6);
        assertNotNull(state);
        assertEquals(6.0, state.getStateVal(), 0.0);
        assertEquals("Yellow", state.getState());

        //lower edge case
        state = new YellowState(5);
        assertNotNull(state);
        assertEquals(5.0, state.getStateVal(), 0.0);
        assertEquals("Yellow", state.getState());

        //upper edge case
        state = new YellowState(7);
        assertNotNull(state);
        assertEquals(7.0, state.getStateVal(), 0.0);
        assertEquals("Yellow", state.getState());
    }

    /**
     * Testing available Red State methods
     */
    @Test
    public void Test_Red_State() {
        //middle case
        StomaState state = new RedState(9);
        assertNotNull(state);
        assertEquals(9.0, state.getStateVal(), 0.0);
        assertEquals("Red", state.getState());

        //lower edge case
        state = new RedState(8);
        assertNotNull(state);
        assertEquals(8.0, state.getStateVal(), 0.0);
        assertEquals("Red", state.getState());

        //upper edge case
        state = new RedState(10);
        assertNotNull(state);
        assertEquals(10.0, state.getStateVal(), 0.0);
        assertEquals("Red", state.getState());
    }
}
