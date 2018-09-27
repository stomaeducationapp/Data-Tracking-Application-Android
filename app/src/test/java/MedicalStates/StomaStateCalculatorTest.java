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
    public void Test_Get_Relevant_Flags_Success() {
        calc = new StomaStateCalculator(3, 800);
        Map<String, String> inMap = new HashMap<>();
        Map<String, Integer> outMap = new HashMap<>();

        inMap.put("Name", "Test1");
        inMap.put("Time", "11:30:00");
        inMap.put("UrineColour", "2");
        inMap.put("UrineFrequency", "3");
        inMap.put("Consistency", "2");
        inMap.put("Volume", "600");
        inMap.put("PhysicalCharacteristics", "thirsty,fatigue,tiredness");

        outMap = new HashMap<>(calc.Get_Flags_From_Data(inMap));

        //if all conditions met, the returned map is correct and complete
        assertEquals(5, outMap.size());
        assertEquals(2, (int) outMap.get("UrineColour"));
        assertEquals(3, (int) outMap.get("UrineFrequency"));
        assertEquals(2, (int) outMap.get("Consistency"));
        assertEquals(600, (int) outMap.get("Volume"));
        assertEquals(6, (int) outMap.get("PhysicalCharacteristics"));
    }

    /**
     * Testing all possible state changes
     */

    //unchanging state
    @Test
    public void Test_Maintain_State() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(3, 800);

        //state initially at 4.5
        inMap.put("UrineColour", 2);                  //make state 4.5
        inMap.put("UrineFrequency", 3);               //make state 4.5
        inMap.put("Volume", 600);                     //make state 5.5
        inMap.put("Consistency", 2);                  //make state 5.5
        inMap.put("PhysicalCharacteristics", 6);      //make state 3.5

        assertEquals("Green", calc.getState());
        assertEquals(3.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Green", calc.getState());
        assertEquals(4.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Green_To_Yellow() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(3, 800);

        inMap.put("UrineColour", 1);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 600);
        inMap.put("Consistency", 1);
        inMap.put("PhysicalCharacteristics", 6);

        assertEquals("Green", calc.getState());
        assertEquals(3.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Yellow", calc.getState());
        assertEquals(6.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Green_To_Red() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(3, 800);

        inMap.put("UrineColour", 1);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 900);
        inMap.put("Consistency", 1);
        inMap.put("PhysicalCharacteristics", 8);

        assertEquals("Green", calc.getState());
        assertEquals(3.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Red", calc.getState());
        assertEquals(9.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Yellow_To_Green() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(6, 800);

        inMap.put("UrineColour", 3);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 600);
        inMap.put("Consistency", 3);
        inMap.put("PhysicalCharacteristics", 8);

        assertEquals("Yellow", calc.getState());
        assertEquals(6.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Green", calc.getState());
        assertEquals(4.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Yellow_To_Red() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(6, 800);

        inMap.put("UrineColour", 1);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 900);
        inMap.put("Consistency", 1);
        inMap.put("PhysicalCharacteristics", 8);

        assertEquals("Yellow", calc.getState());
        assertEquals(6.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Red", calc.getState());
        assertEquals(10.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Red_To_Green() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(8, 800);

        inMap.put("UrineColour", 3);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 600);
        inMap.put("Consistency", 3);
        inMap.put("PhysicalCharacteristics", 0);

        assertEquals("Red", calc.getState());
        assertEquals(8.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Green", calc.getState());
        assertEquals(2.0, calc.getStateVal(), 0.0);
    }

    @Test
    public void Test_Change_Red_To_Yellow() {
        Map<String, Integer> inMap = new HashMap<>();
        calc = new StomaStateCalculator(9, 800);

        inMap.put("UrineColour", 3);
        inMap.put("UrineFrequency", 3);
        inMap.put("Volume", 600);
        inMap.put("Consistency", 2);
        inMap.put("PhysicalCharacteristics", 4);

        assertEquals("Red", calc.getState());
        assertEquals(9.0, calc.getStateVal(), 0.0);

        calc.Calculate_New_State(inMap);

        assertEquals("Yellow", calc.getState());
        assertEquals(6.0, calc.getStateVal(), 0.0);
    }
}
