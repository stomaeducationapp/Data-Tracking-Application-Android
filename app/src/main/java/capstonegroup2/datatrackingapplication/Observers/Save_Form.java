package capstonegroup2.datatrackingapplication.Observers;
import android.os.Bundle;
import android.app.Activity;

/**
 * Created by Patrick on 20-Aug-18.
 */

public class Save_Form implements Form_Change {
    private Factory factory;
    /**
     * public void Change_Form(Form_Control)
     * <p>
     * Public method call for changing between forms within the program
     * <p>
     * Returns
     * void
     *
     * @param form_To_Change_To Enum specifying which form to change to from the current form.
     */
    @Override
    public void Change_Form(Form_Control form_To_Change_To,Activity current_UI) {
        //Logic for whether to "save" the form or if it isn't required return
        switch(form_To_Change_To){
            case Login:
                break;
            case Account_Creation:
                break;
            case Medical_Data_Input:
                break;
            case Account_Main_Menu:
                break;
            case Password_Recovery:
                break;
            case Review:
                break;
            case Account_Information:
                break;
            case Encrypt_and_Export:
                break;
            case Gamification:
                break;
            case Medical_States:
                break;
            default:
                break;

        }
    }

    private void Save_Form(Activity current_UI){
        current_UI.onSaveInstanceState(Bundle outState);
    }
}
