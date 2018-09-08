package MedicalStates;

import android.content.Context;

public class YellowState implements StomaState {

    public YellowState() {

    }

    public String getState(){
        return "Yellow";
    }

    @Override
    public boolean Account_State_Information(Context sys_Ref) {
        return false;
    }
}
