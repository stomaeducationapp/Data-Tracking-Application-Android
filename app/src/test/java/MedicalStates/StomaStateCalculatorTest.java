package MedicalStates;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StomaStateCalculatorTest {
    /**
     * Test state calculator object creation
     */
    private StomaStateCalculator calc;
    @Test
    public void Test_Calculator_Default_Creation() {
        calc = new StomaStateCalculator();
        assertNotNull(calc);
    }

    @Test
    public void Test_Calculator_Alternate_Creation() {
        calc = new StomaStateCalculator(3, 800);
        assertNotNull(calc);
    }


    /**
     * Testing internal methods
     */
    @Test
    public void Test_Get_Relevant_Flags() {
        Map<String, String> inMap = new HashMap<>();
        Map<String, Integer> outMap;

        inMap.put("Name", "Test1");
        inMap.put("Time", "11:30:00");
        inMap.put("UrineColour", "2");
        inMap.put("UrineFrequency", "3");
        inMap.put("Volume", "600");
        inMap.put("Consistency", "2");
        inMap.put("PhysicalCharacteristics", "thirsty,fatigue,tiredness");

        outMap = calc.Get_Flags_From_Data(inMap);

        //if all conditions met, the returned map is correct and complete
        assertTrue(outMap.size() == 5);
        assertTrue(outMap.get("UrineColour") == 2);
        assertTrue(outMap.get("UrineFrequency") == 3);
        assertTrue(outMap.get("Volume") == 600);
        assertTrue(outMap.get("Consistency") == 2);
        assertTrue(outMap.get("PhysicalCharacteristics") == 6);
    }

    /**
     * Testing unchanging state
     */
    @Test
    public void Test_Calculate_New_State() {
        Map<String, String> inMap = new HashMap<>();
        calc = new StomaStateCalculator(3, 800);

        //state initially at 3
        assertTrue(calc.getStateVal() - 3.0 == 0.0);
        assertTrue(calc.getState().equals("Green"));
        inMap.put("UrineColour", "2");                  //make state 3.0
        inMap.put("UrineFrequency", "3");               //make state 3.0
        inMap.put("Volume", "600");                     //make state 1.0
        inMap.put("Consistency", "2");                  //make state 1.0
        inMap.put("PhysicalCharacteristics", "6");      //make state 1.5
        //state will be rounded to 2
        assertTrue(calc.getState().equals("Green"));
//        assertTrue(calc.getStateVal() - 2.0 == 0.0);
    }
}
