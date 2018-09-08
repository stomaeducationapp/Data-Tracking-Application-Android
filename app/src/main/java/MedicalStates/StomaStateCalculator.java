package MedicalStates;

import android.content.Context;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Factory.Factory;

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

            State newState = Calculate_New_State(state_Context.getState(), flags);
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

    private State Calculate_New_State(String currState, List<Flag> currFlags) {
        //Check what attributes have been flagged and determine the state
        State newState;
        switch (currState) {
            case "Green": newState = State.GREEN;
                break;
            case "Yellow": newState = State.YELLOW;
                break;
            case "Red": newState = State.RED;
                break;
            default: newState = null;
                break;
        }


        return newState;
    }

    private boolean Change_State(State newState) {
        boolean success = false;
        //Change to the required state
        if (newState == State.GREEN)
        {
            state_Context = new GreenState();
            success = true;
        }
        else if (newState == State.YELLOW)
        {
            state_Context = new YellowState();
            success = true;
        }
        else if (newState == State.RED)
        {
            state_Context = new RedState();
            success = true;
        }
        return success;
    }
}
