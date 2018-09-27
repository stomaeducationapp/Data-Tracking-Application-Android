package MedicalStates;

import android.content.Context;

public class RedState implements StomaState {
    private double stateVal;

    /**
     * Constructor for the red state class. Validates the imported value before executing.
     * @param inStateVal The value representing the state to switch to. Used to double check the
     *                   correct state was selected before setting it.
     */
    public RedState(int inStateVal) {
        if (inStateVal > 7 && inStateVal < 11) {
            stateVal = (double)inStateVal;
        }
        else {
            throw new IllegalArgumentException("Red state must be between 8 and 10");
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
     * @return String representation of Red state
     */
    @Override
    public String getState() {
        return "Red";
    }

    /**
     * This method is to handle any specific android system calls that must be done in the red state.
     * It has been left empty as the exact purpose is not yet known
     * @param sys_Ref reference to the android caller
     * @return boolean representing success/failure
     */
    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return true;
    }
}
