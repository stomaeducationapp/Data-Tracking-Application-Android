package MedicalStates;

import android.content.Context;

public class GreenState implements StomaState {
    private double stateVal;

    public GreenState(int inStateVal) {
        if (inStateVal > 0 && inStateVal < 5) {
            stateVal = (double)inStateVal;
        }
        else{
            throw new IllegalArgumentException("Green state must be between 1 and 4");
        }
    }

    public double getStateVal() {
        return stateVal;
    }

    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return false;
    }
}
