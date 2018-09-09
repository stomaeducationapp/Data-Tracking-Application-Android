package MedicalStates;

import android.content.Context;

public class RedState implements StomaState {
    double stateVal;

    public RedState(int inStateVal) {
        if (inStateVal > 7 && inStateVal < 11) {
            stateVal = (double)inStateVal;
        }
        else {
            throw new IllegalArgumentException("Red state must be between 8 and 10");
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
