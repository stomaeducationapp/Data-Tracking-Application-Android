package MedicalStates;

import android.content.Context;

public class RedState implements StomaState {

    public RedState() {

    }

    public String getState(){
        return "Red";
    }

    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return false;
    }
}
