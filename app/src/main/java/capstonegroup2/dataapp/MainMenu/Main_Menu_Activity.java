package capstonegroup2.dataapp.MainMenu;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import capstonegroup2.dataapp.MainMenu.Fragments.Green_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Red_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Yellow_State_Fragment;
import capstonegroup2.dataapp.R;

// TODO: 05-Nov-18 will need to save this activity state when going to another activity, except login screen
//https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state?rq=1
/**
 * KNOW BUGS OF THIS SECTION
 * IF you logged in during the switch over to another 24hour time, -> 9:00am, it will not disable the information and no auto start the daily review generation.
 *      will need to have a sleeping thread or something like that for it. 5-Nov-18
 * During Red state the exporting and creating of daily review doesn't occur. this could be done in the background as a future functionality if decided
 */
public class Main_Menu_Activity extends Activity implements Green_State_Fragment.Green_Fragment_Data_Listener,Yellow_State_Fragment.Yellow_Fragment_Data_Listener,Red_State_Fragment.Red_Fragment_Data_Listener {
    // TODO: 05-Nov-18 will need to catch the null point if information is wrong and then return to login screen
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
                ft.replace(R.id.State_Container, new Yellow_State_Fragment());
                ft.commit();
                break;
            case RED:
                ft.replace(R.id.State_Container, new Red_State_Fragment());
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
    public void onChangedData(Green_State_Fragment.Fields field, String value) {

    }

    @Override
    public void onChangedData(Red_State_Fragment.Fields field, String value) {

    }

    @Override
    public void onChangedData(Yellow_State_Fragment.Fields field, String value) {

    }
}
