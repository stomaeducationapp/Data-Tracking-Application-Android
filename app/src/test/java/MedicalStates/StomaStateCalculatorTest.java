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

        assertTrue(outMap.size() == 5);
    }
}
