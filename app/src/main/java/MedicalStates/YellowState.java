package MedicalStates;

import android.content.Context;

public class YellowState implements StomaState {
    private double stateVal;

    /**
     * Constructor for the yellow state class. Validates the imported value before executing.
     * @param inStateVal The value representing the state to switch to. Used to double check the
     *                   correct state was selected before setting it.
     */
    public YellowState(int inStateVal) {
        if (inStateVal > 4 && inStateVal < 8) {
            stateVal = (double)inStateVal;
        }
        else{
            throw new IllegalArgumentException("Yellow state must be between 5 and 7");
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
     * @return String representation of Yellow state
     */
    @Override
    public String getState() {
        return "Yellow";
    }

    /**
     * This method is to handle any specific android system calls that must be done in the yellow state.
     * It has been left empty as the exact purpose is not yet known
     * @param sys_Ref reference to the android caller
     * @return boolean representing success/failure
     */
    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return true;
    }
}
