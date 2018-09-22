package MedicalStates;

import android.content.Context;

public interface StomaState {
    boolean Account_State_Information(Context sys_Ref);
    double getStateVal();
    String getState();
}
