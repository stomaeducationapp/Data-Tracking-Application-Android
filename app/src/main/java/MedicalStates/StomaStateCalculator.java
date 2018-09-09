package MedicalStates;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Factory.Factory;

//
//REFER TO STOMA STATE LOGIC DOCUMENT
//

public class StomaStateCalculator {

    private enum State {GREEN,YELLOW,RED}
    private enum Flag {NUMBAGS,VOLUME}  //add more flags

    private StomaState state_Context;
    private Factory factory;
    private Context sys_Ref;
    //data fields
    private Map<String, String> data;   //might be refactored to become a local var in Calculate_State method


    public StomaStateCalculator() {
        state_Context = new GreenState(); //Probably refactor into factory at some point
        factory = Factory.Get_Factory();
    }

    public boolean Calculate_State() {
        try {
            //Read in the account data
            if (!Get_Account_Data()) {
                //throw exception for data read fail
            }
            List<Flag> flags = Get_Major_Flags();

            State newState = Calculate_New_State(flags);
        }
        catch (Exception e) {}  //change to specific exception when becomes known
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

    private List<Flag> Get_Major_Flags() {
        //Parse account data for presence of flags
        List<Flag> presentFlags = new ArrayList<>();


    }

    private boolean Calculate_New_State(List<Flag> currFlags) {
        //Check what attributes have been flagged and determine the state
        int stateIdx = 3;
        boolean success;


        success = Change_State(stateIdx);
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
