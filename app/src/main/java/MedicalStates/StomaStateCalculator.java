package MedicalStates;

import android.content.Context;

import org.xml.sax.XMLReader;
import java.util.HashMap;
import java.util.Map;
import Factory.Factory;

public class StomaStateCalculator {

    private StomaState account_State;
    private Factory factory;
    private Context sys_Ref;
    //data fields
    //private Map<String, String> data;   //might be refactored to become a local var in Calculate_State method
    private int userDailyOutput;        //user entered average stoma output volume
    private int urineCount;      //total urine frequency counter
    private int outputVolume;    //total output record
    private int numEntries;      //number of entries for the current day


    public StomaStateCalculator() {
        account_State = new GreenState(3); //maybe refactor into factory at some point
        factory = Factory.Get_Factory();
        urineCount = 0;
        outputVolume = 0;
        numEntries = 0;
        //sys_Ref = ;
    }

    //Alternate constructor if calculator needs to be reconstructed
    public StomaStateCalculator(int prevState, int userOutput) {
        if (prevState > 0 && prevState < 5) {
            account_State = new GreenState(prevState);
        }
        else if (prevState > 4 && prevState < 8) {
            account_State = new YellowState(prevState);
        }
        else if (prevState > 7 && prevState < 11) {
            account_State = new RedState(prevState);
        }
        else {
            throw new IllegalArgumentException("Invalid state value");
        }
        factory = Factory.Get_Factory();
        urineCount = 0;
        outputVolume = 0;
        numEntries = 0;
        userDailyOutput = userOutput;
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
            //change Map<String, Integer> flags = Get_Flags_From_Data(); //retrieve and simplify the flag info from the input data

            //change if (!Calculate_New_State(flags)) {  //calculate and set the hydration state with the newly calculated data
            //change     throw new IllegalArgumentException("Problem calculating new state");
            //change }

        }
        catch (IllegalArgumentException e) {success = false;}  //change to specific exception when possible
        return success;
    }

    public boolean Get_Account_Data() {
        boolean success = true;
        //read in account data from XML file
        //change XMLReader dataIn = factory.Make_Reader(Factory.XML_Reader_Choice.Medical);    //needs xml reader class
        try {
            //change data = dataIn.Read_Medical_Data();  //update method call when reader becomes available
        }
        catch (Exception e) {success = false;}

        return success;
    }

    public Map<String, Integer> Get_Flags_From_Data(Map<String, String> data) {
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
                //change if (numEntries > 1) {   //don't use urine frequency until there has been at least 2 entries
                    presentFlags.put("UrineFrequency", urineCount);
                //change }
            }
            else if (temp.equals("Volume")) {   //only add if total for current day
                //volume code
                int vol = Integer.parseInt(data.get(temp));
                outputVolume += vol;
                //change if (numEntries > 1) {   //don't use total volume until there has been at least 2 entries
                    presentFlags.put("Volume", outputVolume);
                //change}
            }
            else if (temp.equals("Consistency")) {
                int value = Integer.parseInt(data.get(temp));
                presentFlags.put("Consistency", value); //may need to change depending on format of stored data
            }
            else if (temp.equals("PhysicalCharacteristics")) {
                //Physical characteristics should be stored as CSV format
                String value = data.get("PhysicalCharacteristics");
                String[] splitString = value.split(",");
                int charIdx = 0;

                for (int ii = 0; ii < splitString.length; ii++) {
                    String tmp = splitString[ii];

                    if (tmp.equals("thirsty") || tmp.equals("headache") || tmp.equals("lightheaded")) {
                        charIdx += 1;
                    }
                    else if (tmp.equals("stomach cramps") || tmp.equals("muscle cramps") || tmp.equals("fatigue")) {
                        charIdx += 2;
                    }
                    else if (tmp.equals("dry mouth") || tmp.equals("confusion") || tmp.equals("tiredness")) {
                        charIdx += 3;
                    }
                }
                presentFlags.put("PhysicalCharacteristics", charIdx);
            }
        }
        return presentFlags;
    }

    public boolean Calculate_New_State(Map<String, Integer> currFlags) {
        //Check what attributes have been flagged and determine the state
        double stateIdx = 3.0 + (account_State.getStateVal()*0.5);    //Base for new state
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
                    stateIdx += 0.0;
                }
                else if (scale == 3) {
                    stateIdx -= 1.0;
                }
            }
            else if (temp.equals("UrineFrequency")) {
                int scale = currFlags.get(temp);
                if (scale < 3) {
                    stateIdx += 2.0;
                }
            }
            else if (temp.equals("Consistency")) {
                int scale = currFlags.get(temp);
                if (scale == 1) {
                    stateIdx += 1.0;
                }
                else if (scale == 2) {
                    stateIdx += 0.0;
                }
                else if (scale == 3) {
                    stateIdx -= 1.0;
                }
            }
            else if (temp.equals("Volume")) {
                int scale = currFlags.get(temp);
                if (scale < userDailyOutput) {
                    stateIdx -= 2.0;
                }
                else if (scale > userDailyOutput && scale < userDailyOutput + 300) {
                    stateIdx += 1.0;
                }
                else if (scale > userDailyOutput + 299) {
                    stateIdx += 2.0;
                }
            }
            else if (temp.equals("PhysicalCharacteristics")) {
                int numTrue = currFlags.get(temp);
                if (numTrue == 0) {
                    stateIdx -= 1.0;
                }
                else if (numTrue > 0 && numTrue < 4) {  //1,2,3
                    stateIdx += 0.5;
                }
                else if (numTrue > 3 && numTrue < 7){   //4,5,6
                    stateIdx += 1.0;
                }
                else if (numTrue > 6 && numTrue < 10) { //7,8,9
                    stateIdx += 1.5;
                }
                else if (numTrue > 9 && numTrue < 13) {//10,11,12
                    stateIdx += 2.0;
                }
                else if (numTrue > 12 && numTrue < 16) {//13,14,15
                    stateIdx += 2.5;
                }
                else if (numTrue > 15 && numTrue < 19) {//16,17,18
                    stateIdx += 3.0;
                }
            }
        }

        stateRef = (int)Math.ceil(stateIdx);
        if (stateRef < 1) {
            stateRef = 1;
        }
        else if (stateRef > 10) {
            stateRef = 10;
        }

        success = Change_State(stateRef);
        return success;
    }

    public boolean Change_State(int stateIdx) {
        boolean success;
        //Change to the required state
        if (stateIdx > 0 && stateIdx < 5)
        {
            //must be green state
            account_State = new GreenState(stateIdx);
            success = true;
        }
        else if (stateIdx > 4 && stateIdx < 8)
        {
            //must be yellow state
            account_State = new YellowState(stateIdx);
            success = true;
        }
        else if (stateIdx > 7 && stateIdx < 11)
        {
            //must be red state
            account_State = new RedState(stateIdx);
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

    public double getStateVal() {
        return account_State.getStateVal();
    }

    public String getState() {
        return account_State.getState();
    }
}
