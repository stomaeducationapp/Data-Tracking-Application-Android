package capstonegroup2.dataapp.MainMenu;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import capstonegroup2.dataapp.R;

public class Main_Menu_Activity extends Activity implements Green_State_Fragment.Green_Fragment_Data_Listener {

    private Account_Data_Container account_data_container;
    private boolean state_Invalid;
    private static final String GREEN = "Green";
    private static final String YELLOW = "Yellow";
    private static final String RED = "Red";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state_Invalid = false;
        account_data_container = new Account_Data_Container();
        // Will need to populate the container from XML
        /*Xml reader get all account information*/
        Change_Account_State();
    }

    //this will be used to select what fragment is loaded in
    private void Change_Account_State() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        String account_State = account_data_container.getState();
        switch (account_State) {
            case GREEN:
                ft.replace(R.id.State_Container, new Green_State_Fragment());
                ft.commit();
                break;
            case YELLOW:
                ft.commit();
                break;
            case RED:
                ft.commit();
                break;
            default:
                //need to re-pull from account information on device as maybe corrupted/invalid.
                if (!state_Invalid) {
                    state_Invalid = true;
                    //String temp = XML reader get state

                } else {
                    //will reset it to green
                    //xml writer state = green;
                    account_data_container.setState(GREEN);
                }
                Change_Account_State();
                break;
        }
    }

    @Override
    public void onChangedData(String field, String value) {
//this will be complex, as the information given could even change the fragment that is was called from etc etc
    }
}
