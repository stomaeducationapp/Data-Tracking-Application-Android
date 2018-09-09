package MedicalStates;

import android.content.Context;

public class YellowState implements StomaState {
    double stateVal;

    public YellowState(int inStateVal) {
        if (inStateVal > 4 && inStateVal < 8) {
            stateVal = (double)inStateVal;
        }
        else{
            throw new IllegalArgumentException("Yellow state must be between 5 and 7");
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
