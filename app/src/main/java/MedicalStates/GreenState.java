package MedicalStates;

import android.content.Context;

/**
 * <h1>GreenState</h1>
 * This class encompasses the functionality specific to the green state.
 *
 * @author Ethan Bell
 * @version 1.0
 *
 */
public class GreenState implements StomaState {
    private double stateVal;

    /**
     * Constructor for the green state class. Validates the imported value before executing.
     * @param inStateVal The value representing the state to switch to. Used to double check the
     *                   correct state was selected before setting it.
     */
    public GreenState(int inStateVal) {
        if (inStateVal > 0 && inStateVal < 5) {
            stateVal = (double)inStateVal;
        }
        else{
            throw new IllegalArgumentException("Green state must be between 1 and 4");
        }
    }

    /**
     * Returns the real number representation of the current state
     * @return the value assigned to the current state
     */
    @Override
    public double getStateVal() {
        return stateVal;
    }

    /**
     * Returns the name of the current state
     * @return String representation of Green state
     */
    @Override
    public String getState() {
        return "Green";
    }

    /**
     * This method is to handle any specific android system calls that must be done in the green state.
     * It has been left empty as the exact purpose is not yet known
     * @param sys_Ref reference to the android caller
     * @return boolean representing success/failure
     */
    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return true;
    }
}
