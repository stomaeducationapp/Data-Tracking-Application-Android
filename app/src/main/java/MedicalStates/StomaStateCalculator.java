package MedicalStates;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Factory.Factory;
import XML.Medical_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

import static XML.XML_Reader.Tags_To_Read.Bags;
import static XML.XML_Reader.Tags_To_Read.Hydration;
import static XML.XML_Reader.Tags_To_Read.Last_Entry;
import static XML.XML_Reader.Tags_To_Read.Urine;

/**
 * <h1>StomaStateCalculator</h1>
 * This class holds all of the logic for calculating the state of the users hydration status.
 * It calls a reader class to obtain the users most recent data input, and uses this along with their
 * current hydration state to calculate and set a new state.
 *
 * @author Ethan
 * @version 1.0
 */
public class StomaStateCalculator {

    private StomaState account_State;
    private Factory factory;
    //data fields
    private int userDailyOutput;        //user entered average stoma output volume
    private int urineCount;      //total urine frequency counter
    private int outputVolume;    //total output record
    private File med;

    /**
     * Constructor for the state calculator class. Initialises the state to Green, with a value of 3.
     */
    public StomaStateCalculator() {
        account_State = new GreenState(3);
        factory = Factory.Get_Factory();
        urineCount = 0;
        outputVolume = 0;
        userDailyOutput = 1200; //DECIDED UPON CONSTANT?
        med = null;
    }

    /**
     * Alternate constructor for the state calculator. This constructor can be used to customise the
     * initialisation point, for example if the original object needs to be replaced, this constructor
     * can be used to resume from the previous state.
     * @param prevState the state value to initialise the state calculator object to.
     * @param userOutput a customisable value for the users normal daily output to better tailor the
     *                   state calculations
     */
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
        userDailyOutput = userOutput;
        med = null;
    }

    /**
     * The method that will be called to calculate and update a users hydration state. This method
     * handles sequentially calling the other retrieval and calculation methods of the class, making
     * this method the target for the app when it wants to calculate a new state.
     * @return boolean representing success/failure
     */
    public boolean Calculate_State(File medical) {
        boolean success = true;
        try {
            //Read in the account data
            Map<String, String> data = new HashMap<>();
            Map<String, Integer> flags = new HashMap<>();
            med = medical;

            data = Get_Account_Data(medical);
            flags = Get_Flags_From_Data(data); //retrieve and simplify the flag info from the input data

            if (!Calculate_New_State(flags)) {  //calculate and set the hydration state with the newly calculated data
                throw new IllegalArgumentException("Problem calculating new state");
            }

        }
        catch (IllegalArgumentException e) {success = false;}  //change to specific exception when possible
        return success;
    }

    /**
     * This method gets an XML reader from the factory object and uses it to get the users most
     * recent data input in a map
     * @return the map storing the data in attribute - value pairs
     */
    public Map<String, String> Get_Account_Data(File medical) {
        Map<String, String> data = new HashMap<>();
        List<XML_Reader.Tags_To_Read> tags = new ArrayList<>();
        tags.add(Last_Entry); //Tell the Reader we are only looking for the last entry
        tags.add(Bags);
        tags.add(Urine);
        tags.add(Hydration);

        //read in medical data from file
        Medical_Reader dataIn = (Medical_Reader) factory.Make_Reader(Factory.XML_Reader_Choice.Medical);
        try {
            data = dataIn.Read_File(medical, tags, null);  //update method call when reader becomes available
        }
        catch (XML_Reader_Exception e)
        {
            throw new RuntimeException("Something has gone wrong trying to read" + e.getMessage()); //TODO FIX IF BETTER OPTION
        }

        data = sortMedData(data); //Reformat the string so we can easily access the information

        return data;
    }

    /**
     * This method takes the map with all of the user data and extracts only the key - value pairs
     * that contain information relevant to the state calculation. The map is processed and relevant
     * fields are translated into number format and added to a hydration flag map.
     * @param data The full data set from the user input
     * @return the map containing the flags
     */
    public Map<String, Integer> Get_Flags_From_Data(Map<String, String> data) {
        //Parse account data for presence of flags
        Map<String, Integer> presentFlags = new HashMap<>();
        String[] attributes;

        attributes = data.keySet().toArray(new String[0]);

        //parse full data and extract only relevant key-value pairs
        for (String temp : attributes) {
            //iterate all data elements and only copy relevant fields to the new Map
            if (temp.contains("UrineColour")) {
                int uIdx = 0;
                String tmp = data.get(temp);
                if (tmp.equals("Light"))
                {
                    uIdx += 1;
                }
                else if (tmp.equals("Medium"))
                {
                    uIdx += 2;
                }
                else if (tmp.equals("Dark"))
                {
                    uIdx += 3;
                }
                presentFlags.put("UrineColour", uIdx);
            }
            else if (temp.contains("UrineFrequency")) {
                //frequency code
                int freq = Integer.parseInt(data.get(temp));
                urineCount += freq;
                presentFlags.put("UrineFrequency", urineCount);
            }
            else if (temp.contains("Volume")) {
                //volume code
                int vol = Integer.parseInt(data.get(temp));
                outputVolume += vol;
                presentFlags.put("Volume", outputVolume);
            }
            else if (temp.contains("Consistency")) {
                int cIdx = 0;
                String tmp = data.get(temp);
                if (tmp.equals("Watery"))
                {
                    cIdx += 1;
                }
                else if (tmp.equals("Thick"))
                {
                    cIdx += 2;
                }
                else if (tmp.equals("Toothpaste-like"))
                {
                    cIdx += 3;
                }
                presentFlags.put("Consistency", cIdx); //TODO CONSIDER MAKING CIDX A CLASSFIELD TOF ALLOW VALUE TO ACCUMULATE OVER MULTIPLE BAGS
            }
            else if (temp.contains("Hydration")) {
                //Physical characteristics should be stored as CSV format
                String value = data.get(temp);
                String[] splitString = value.split(",");
                int dIdx = 0;

                for (String tmp : splitString) {
                    if (tmp.equals("Thirsty") || tmp.equals("Headache") || tmp.equals("Light Headed")) {
                        dIdx += 1;
                    } else if (tmp.equals("Stomach Cramps") || tmp.equals("Muscle Cramps") || tmp.equals("Fatigue")) {
                        dIdx += 2;
                    } else if (tmp.equals("Dry Mouth") || tmp.equals("Confusion") || tmp.equals("Tiredness")) {
                        dIdx += 3;
                    }
                }
                presentFlags.put("Dehydration", dIdx);
            }
        }
        return presentFlags;
    }

    /**
     * This method uses the flags found in the users data and uses them to calculate teh hydration state.
     * The users previous state is also taken into account and will have an effect on the state value.
     * Flags considered positive will decrement the state value.
     * Flags considered neutral will not change the state value.
     * Flags considered negative will increment the state value.
     * All flags are taken into account to determine the new state.
     * @param currFlags the map with attributes and their associated values
     * @return boolean representing success or failure
     */
    public boolean Calculate_New_State(Map<String, Integer> currFlags) {
        //Check what attributes have been flagged and determine the state
        double stateIdx = 2.0 + (account_State.getStateVal()*0.5);    //Base for new state - might be lowered
        int stateRef;
        boolean success;
        String[] attributes;

        attributes = currFlags.keySet().toArray(new String[0]);

        for (String temp : attributes) {
            switch (temp) {
                case "UrineColour": {   //change hydration depending on urine colour
                    int scale = currFlags.get(temp);
                    if (scale == 1) {
                        stateIdx += 1.0;
                    } else if (scale == 2) {
                        stateIdx += 0.0;
                    } else if (scale == 3) {
                        stateIdx -= 1.0;
                    }
                    break;
                }
                case "UrineFrequency": {
                    int scale = currFlags.get(temp);
                    if (scale < 3) {
                        stateIdx += 2.0;
                    }
                    break;
                }
                case "Consistency": { //TODO CONSIDER REWORKING TO USE AN AVERAGE OF SOME SORT
                    int scale = currFlags.get(temp);
                    if (scale == 1) {
                        stateIdx += 1.0;
                    } else if (scale == 2) {
                        stateIdx += 0.0;
                    } else if (scale == 3) {
                        stateIdx -= 1.0;
                    }
                    break;
                }
                case "Volume": {
                    int scale = currFlags.get(temp);
                    if (scale < userDailyOutput) {
                        stateIdx -= 2.0;
                    } else if (scale > userDailyOutput && scale < userDailyOutput + 300) {
                        stateIdx += 1.0;
                    } else if (scale > userDailyOutput + 299) {
                        stateIdx += 2.0;
                    }
                    break;
                }
                case "Dehydration":
                    int numTrue = currFlags.get(temp);
                    if (numTrue == 0) {
                        stateIdx -= 1.0;
                    } else if (numTrue > 0 && numTrue < 4) {  //1,2,3 selected
                        stateIdx += 0.5;
                    } else if (numTrue > 3 && numTrue < 7) {   //4,5,6 selected
                        stateIdx += 1.0;
                    } else if (numTrue > 6 && numTrue < 10) { //7,8,9 selected
                        stateIdx += 1.5;
                    } else if (numTrue > 9 && numTrue < 13) {//10,11,12 selected
                        stateIdx += 2.0;
                    } else if (numTrue > 12 && numTrue < 16) {//13,14,15 selected
                        stateIdx += 2.5;
                    } else if (numTrue > 15 && numTrue < 19) {//16,17,18 selected
                        stateIdx += 3.0;
                    }
                    break;
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

    /**
     * This method evaluates the state value calculated from the flags and determines which state the
     * account_State object should be set to.
     * @param stateIdx The value representing the users current hydration state
     * @return boolean representing success/failure
     */
    public boolean Change_State(int stateIdx) {
        boolean success;
        try {
            //Change to the required state
            if (stateIdx > 0 && stateIdx < 5) {
                //must be green state
                account_State = new GreenState(stateIdx);
                success = true;
            } else if (stateIdx > 4 && stateIdx < 8) {
                //must be yellow state
                account_State = new YellowState(stateIdx);
                success = true;
            } else if (stateIdx > 7 && stateIdx < 11) {
                //must be red state
                account_State = new RedState(stateIdx);
                success = true;
            } else {
                success = false;
            }
            account_State.Account_State_Information(factory, med);
        }
        catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception e)
        {success = false;}

        return success;
    }

    /**
     * Used to reset the daily total urine count and total output volume. This should be called at the
     * start of each day for the calculator to function properly.
     */
    public void reset() {
        urineCount = 0;
        outputVolume = 0;
    }

    /**
     * This method makes it possible to change the users customisable daily output amount. This is so
     * the calculator can be updated if the user ever changes their average daily output amount to better
     * suit their condition.
     * @param dailyOutput number representing the new daily output to be used.
     */
    public void setUserDailyOutput(int dailyOutput) {
        userDailyOutput = dailyOutput;
    }

    /**
     * This method can be used in case an external class needs to get the state value.
     * @return real number representing the state value
     */
    public double getStateVal() {
        return account_State.getStateVal();
    }

    /**
     * This method can be used in case an external class needs to get the current state
     * @return String representing the current state.
     */
    public String getState() {
        return account_State.getState();
    }

    /* FUNCTION INFORMATION
     * NAME - sortMedData
     * INPUTS - fileData (data that needs to be looked through)
     * OUTPUTS - sortedData (map that has only relevant data inside)
     * PURPOSE - This is the function that formats freshly read file data into a format the calculator can use
     */
    private Map<String, String> sortMedData (Map<String, String> fileData)
    {
        String[] attributes;

        attributes = fileData.keySet().toArray(new String[0]);

        //Parse full data and extract only relevant key-value pairs
        for (String temp : attributes) {
            //Iterate all data elements and pull out the needed data from the compound strings
            if (temp.contains("Urine")) {

                String urineVal = fileData.get(temp);
                String[] urineData = urineVal.split(",");
                fileData.put(("UrineFrequency"), urineData[0]); //Always built the same way so can pull it out without issue
                fileData.put(("UrineColour"), urineData[1]);
                fileData.remove(temp); //Remove the old value since we don't need it anymore

            }
            else if (temp.contains("Bags")) {

                String bagList = fileData.get(temp);
                String[] bags = bagList.split(";"); //Could be multiple bags so we iterate over each one separated by ;
                for(int ii = 0; ii < bags.length; ii++)
                {
                    String[] bagData = bags[ii].split(",");
                    fileData.put(("Volume" + "-" + ii), bagData[0]); //Always built the same way so can pull it out without issue
                    fileData.put(("Consistency" + "-" + ii), bagData[1]); //Include the ii value so each bag has unique tag
                }
                fileData.remove(temp); //Remove the old value since we don't need it anymore

            }
        }
        return fileData;
    }

}
