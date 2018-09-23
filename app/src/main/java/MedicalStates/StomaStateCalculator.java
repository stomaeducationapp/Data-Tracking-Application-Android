package MedicalStates;

import android.content.Context;

import org.xml.sax.XMLReader;

import java.util.HashMap;
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
    private int userDailyOutput;        //user entered average stoma output volume
    private int urineCount;      //total urine frequency counter
    private int outputVolume;    //total output record
    private int numEntries;      //number of entries for the current day


    public StomaStateCalculator() {
        state_Context = new GreenState(3); //Probably refactor into factory at some point
        factory = Factory.Get_Factory();
        urineCount = 0;
        outputVolume = 0;
        numEntries = 0;
        //sys_Ref = ;
    }

    //Alternate constructor if calculator needs to be reconstructed
    public StomaStateCalculator(int prevState) {
        if (prevState > 0 && prevState < 5) {
            state_Context = new GreenState(prevState);
        }
        else if (prevState > 4 && prevState < 8) {
            state_Context = new YellowState(prevState);
        }
        else if (prevState > 7 && prevState < 11) {
            state_Context = new RedState(prevState);
        }
        factory = Factory.Get_Factory();
        urineCount = 0;
        outputVolume = 0;
        numEntries = 0;
        //sys_Ref = ;
    }

    public boolean Calculate_State() {
        boolean success = true;
        numEntries++;
        try {
            //Read in the account data
            if (!Get_Account_Data()) {
                throw new IllegalArgumentException("Problem reading from file");
            }
            Map<String, Integer> flags = Get_Flags_From_Data(); //retrieve and simplify the flag info from the input data

            if (!Calculate_New_State(flags)) {  //calculate and set the hydration state with the newly calculated data
                throw new IllegalArgumentException("Problem calculating new state");
            }

        }
        catch (IllegalArgumentException e) {success = false;}  //change to specific exception when possible
        return success;
    }

    private boolean Get_Account_Data() {
        boolean success = true;
        //read in account data from XML file
        XMLReader dataIn = factory.Make_Reader(Factory.XML_Reader_Choice.Medical);    //needs xml reader class
        try {
            data = dataIn.Read_Medical_Data();  //update method call when reader becomes available
        }
        catch (Exception e) {success = false;}

        return success;
    }

    private Map<String, Integer> Get_Flags_From_Data() {
        //Parse account data for presence of flags
        Map<String, Integer> presentFlags = new HashMap<>();
        String[] attributes;

        attributes = (String[])data.keySet().toArray();

        //parse full data and extract only relevant key-value pairs
        for (int i = 0; i < attributes.length; i++) {
            //iterate all data elements and only copy relevant fields to the new Map
            String temp = attributes[i];
            if (temp.equals("UrineColour")) {
                int value = Integer.parseInt(data.get(temp));
                presentFlags.put("UrineColour", value); //may need to change depending on format of stored data
            }
            else if (temp.equals("UrineFrequency")) {   //only add if total for current day
                //frequency code
                int freq = Integer.parseInt(data.get(temp));
                urineCount += freq;
                if (numEntries > 1) {   //don't use urine frequency until there has been at least 2 entries
                    presentFlags.put("UrineFrequency", urineCount);
                }
            }
            else if (temp.equals("Volume")) {   //only add if total for current day
                //volume code
                int vol = Integer.parseInt(data.get(temp));
                outputVolume += vol;
                if (numEntries > 1) {   //don't use total volume until there has been at least 2 entries
                    presentFlags.put("Volume", outputVolume);
                }
            }
            else if (temp.equals("Consistency")) {
                int value = Integer.parseInt(data.get(temp));
                presentFlags.put("Consistency", value); //may need to change depending on format of stored data
            }
            else if (temp.equals("PhysicalCharacteristics")) {
                //Physical characteristics should be stored as CSV format. OR just store the number true from the start
                String value = data.get("PhysicalCharacteristics");
                String[] splitString = value.split(",");
                presentFlags.put("PhysicalCharacteristics", splitString.length);

                //presentFlags.put("PhysicalCharacteristics", Integer.parseInt(value));
            }
        }
        return presentFlags;
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
            if (temp.equals("UrineColour")) {   //change hydration depending on urine colour
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
            else if (temp.equals("UrineFrequency")) {
                int scale = currFlags.get(temp);
                if (scale < 3) {
                    stateIdx += 1.0;
                }
                else if (scale > 2 && scale < 5) {
                    stateIdx += 0;
                }
                else if (scale > 4 && scale < 8) {
                    stateIdx -= 1;
                }
                else if (scale > 7 && scale < 10) {
                    stateIdx += 0;
                }
                else if (scale > 10) {
                    stateIdx += 1;
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
            else if (temp.equals("Volume")) {
                int scale = currFlags.get(temp);
                if (scale < userDailyOutput - 200) {
                    stateIdx += 2.0;
                }
                else if (scale > userDailyOutput - 199 && scale < userDailyOutput - 99) {
                    stateIdx += 0.0;
                }
                else if (scale > userDailyOutput - 100 && scale < userDailyOutput + 100) {
                    stateIdx -= 1.0;
                }
                else if (scale > userDailyOutput + 101 && scale < userDailyOutput + 199) {
                    stateIdx += 0.0;
                }
                else if (scale > userDailyOutput + 200) {
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

    public void reset() {
        urineCount = 0;
        outputVolume = 0;
        numEntries = 0;
    }
}
