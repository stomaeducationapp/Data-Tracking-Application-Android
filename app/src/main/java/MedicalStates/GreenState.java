package MedicalStates;

import android.content.Context;

public class GreenState implements StomaState {

    public GreenState() {

    }

    public String getState(){
        return "Green";
    }

    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return false;
    }
}
