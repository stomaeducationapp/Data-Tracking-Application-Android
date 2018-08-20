package capstonegroup2.datatrackingapplication.Observers;

import android.app.Activity;

/**
 * Created by Patrick on 20-Aug-18.
 */

public class Delete_Form implements Form_Change {
    /**
     * @param form_To_Change_To Enum specifying which form to change to from the current form.
     */
    @Override
    public void Change_Form(Form_Control form_To_Change_To,Activity current_UI) {
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
}
