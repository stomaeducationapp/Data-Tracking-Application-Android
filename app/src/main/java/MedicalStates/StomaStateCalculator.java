package MedicalStates;

import android.content.Context;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Factory.Factory;

//
//REFER TO STOMA STATE LOGIC DOCUMENT
//SOME METHODS WILL CHANGE DEPENDING ON DATA STORAGE
//

public class StomaStateCalculator {

    //private enum State {GREEN,YELLOW,RED}
    //private enum Flag {NUMBAGS,VOLUME}  //add more flags

    private StomaState state_Context;
    private Factory factory;
    private Context sys_Ref;
    //data fields
    private Map<String, String> data;   //might be refactored to become a local var in Calculate_State method


    public StomaStateCalculator() {
        state_Context = new GreenState(3); //Probably refactor into factory at some point
        factory = Factory.Get_Factory();
    }

    public boolean Calculate_State() {
        try {
            //Read in the account data
            if (!Get_Account_Data()) {
                //throw exception for data read fail
            }
            Map<String, Integer> flags = Get_Major_Flags();

            Calculate_New_State(flags);
        }
        catch (Exception e) {}  //change to specific exception when possible
    }

    private boolean Get_Account_Data() {
        boolean success = true;

        //read in account data from XML file
        XMLReader dataIn= factory.Make_Reader(Factory.XML_Reader_Choice.Medical);    //needs xml reader class
        try {
            data = dataIn.Read_Medical_Data();  //update method call when reader becomes available
        }
        catch (Exception e) {success = false;}

        return success;
    }

    private Map<String, Integer> Get_Major_Flags() {
        //Parse account data for presence of flags
        Map<String, Integer> presentFlags = new HashMap<>();
        String[] attributes;
        //parse full data and extract only relevant key-value pairs

        attributes = (String[])data.keySet().toArray();

        for (int i = 0; i < attributes.length; i++) {
            //iterate all data elements and only copy relevant fields to the new Map
            String temp = attributes[i];
            if (temp.equals("UrineColour")) {
                int value = Integer.parseInt(data.get(temp));
                presentFlags.put("UrineColour", value); //may need to change depending on format of stored data
            }
            else if (temp.equals("Volume")) {
                //volume code
            }
            else if (temp.equals("Consistency")) {
                int value = Integer.parseInt(data.get(temp));
                presentFlags.put("Consistency", value); //may need to change depending on format of stored data
            }
            else if (temp.equals("PhysicalCharacteristics")) {
                //Physical characteristics should be stored as CSV format
                String value = data.get("PhysicalCharacteristics");
                String[] splitString = value.split(",");
                presentFlags.put("PhysicalCharacteristics", splitString.length);
            }
        }
    }

    private boolean Calculate_New_State(Map<String, Integer> currFlags) {
        //Check what attributes have been flagged and determine the state
        double stateIdx = 3.0 + (state_Context.getStateVal()*0.5);    //Base for new state
        int stateRef;
        boolean success;
        String[] attributes;

        attributes = (String[])currFlags.keySet().toArray();

        for (int i = 0; i < attributes.length; i++) {
            String temp = attributes[i];
            if (temp.equals("UrineColour")) {
                int scale = currFlags.get(temp);
                if (scale == 1) {
                    stateIdx += 1.0;
                }
                else if (scale == 2) {
                    stateIdx += 0.5;
                }
                else if (scale == 3) {
                    stateIdx += 0.0;
                }
                else if (scale == 4) {
                    stateIdx -= 0.5;
                }
                else if (scale == 5) {
                    stateIdx -= 1.0;
                }
            }
            else if (temp.equals("Consistency")) {
                int scale = currFlags.get(temp);
                if (scale == 1) {
                    stateIdx += 1.0;
                }
                else if (scale == 2) {
                    stateIdx += 0.5;
                }
                else if (scale == 3) {
                    stateIdx += 0.0;
                }
                else if (scale == 4) {
                    stateIdx -= 0.5;
                }
                else if (scale == 5) {
                    stateIdx -= 1.0;
                }
            }
            else if (temp.equals("Volume")) {   //will need some work to function properly as these are daily totals
                int scale = currFlags.get(temp);
                if (scale < 500) {
                    stateIdx += 2.0;
                }
                else if (scale > 499 && scale < 600) {
                    stateIdx += 0.0;
                }
                else if (scale > 599 && scale < 1100) {
                    stateIdx -= 1.0;
                }
                else if (scale > 1099 && scale < 1200) {
                    stateIdx += 0.0;
                }
                else if (scale > 1199) {
                    stateIdx += 2.0;
                }
            }
            else if (temp.equals("PhysicalCharacteristics")) {
                int numTrue = currFlags.get(temp);
                if (numTrue == 0) {
                    stateIdx -= 1.0;
                }
                else if (numTrue > 7) {
                    stateIdx += 3.0;
                }
                else {
                    double dNumTrue = (double)numTrue;
                    stateIdx += (dNumTrue - 1.0)/2.0;
                }
            }
        }

        stateRef = (int)Math.round(stateIdx);
        if (stateRef < 1) {
            stateRef = 1;
        }
        else if (stateRef > 10) {
            stateRef = 10;
        }

        success = Change_State(stateRef);
        return success;
    }

    private boolean Change_State(int stateIdx) {
        boolean success;
        //Change to the required state
        if (stateIdx > 0 && stateIdx < 5)
        {
            //must be green state
            state_Context = new GreenState(stateIdx);
            success = true;
        }
        else if (stateIdx > 4 && stateIdx < 8)
        {
            //must be yellow state
            state_Context = new YellowState(stateIdx);
            success = true;
        }
        else if (stateIdx > 7 && stateIdx < 11)
        {
            //must be red state
            state_Context = new RedState(stateIdx);
            success = true;
        }
        else
        {
            //something went wrong - exception or return false
            //throw new IllegalArgumentException("Error calculating state");
            success = false;
        }
        return success;
    }
}
