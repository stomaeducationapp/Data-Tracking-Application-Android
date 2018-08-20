package capstonegroup2.datatrackingapplication.Observers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import capstonegroup2.datatrackingapplication.BuildConfig;

/**
 * Created by Patrick on 19-Aug-18.
 * Last Edited by Patrick on 20-Aug-18 10pm
 */

/**
 * Changes:
 * 19th
 * Created Class 'Save_Form_Change', and created the switch statement for Form_Control enum
 * Added Save_Current_Activity() method
 *
 * 20th
 * Renamed to Form_Change_Concrete due change in how activities will be saved, these are handled by the activities themselves
 * Deleted Save_Current_Activity() method
 * Added Debug Code for Unit Testing concrete Class, and will test Form_Change Interface at the same time
 * Added JavaDoc code
 */
public class Form_Change_Concrete implements Form_Change {
    private Factory factory;

    /**
     * @param form_To_Change_To Enum specifying which form to change to from the current form.
     * @param context
     * @return
     * @throws RuntimeException
     */
    @Override
    public boolean Change_Form(Form_Control form_To_Change_To, Context context) throws RuntimeException {
        //Logic for which Activity to go to
        boolean valid = false;
        Activity activity = null;
        Intent intent = null;
        //// TODO: 20-Aug-18 Need to change names as the classes/packages are created so it fits in and switches correctly 
        switch (form_To_Change_To) {
            case Account_Creation:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Account_Creation_Activity(this, context);
                    intent = new Intent(context, Account_Creation.class);
                }
                break;
            case Medical_Data_Input:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Medical_Data_Input_Activity(this, context);
                    intent = new Intent(context, Medical_Data_Input.class);
                }
                break;
            case Account_Main_Menu:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Account_Main_Menu_Activity(this, context);
                    intent = new Intent(context, Main_Menu.class);
                }
                break;
            case Password_Recovery:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Password_Recovery_Activity(this, context);
                    intent = new Intent(context, Password_Recovery.class);
                }
                break;
            case Review:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Medical_Review_Activity(this, context);
                    intent = new Intent(context, Medical_Review.class);
                }
                break;
            case Account_Information:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Account_Information_Activity(this, context);
                    intent = new Intent(context, Account_Information.class);
                }
                break;
            case Encrypt_and_Export:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Encrypt_and_Export_Activity(this, context);
                    intent = new Intent(context, Encrypt_and_Export.class);
                }
                break;
            case Gamification:
                if (BuildConfig.DEBUG) {
                    valid = true;
                } else {
                    activity = factory.Build_Gamification_Activity(this, context);
                    intent = new Intent(context, Gamification.class);
                }
                break;
            default:
                //Shouldn't get here as it is based on enum, Void can get here which is a big error
                throw new RuntimeException("Null Given/Unreachable");
        }
        if (!BuildConfig.DEBUG) {
            activity.startActivity(intent);
        }
        return valid;
    }
}