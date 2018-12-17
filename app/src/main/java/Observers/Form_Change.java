package Observers;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.util.HashMap;

import Factory.Factory;
import capstonegroup2.dataapp.Challenges.Challenge;
import capstonegroup2.dataapp.Challenges.ChallengeGUI;
import capstonegroup2.dataapp.PasswordRecovery;
import capstonegroup2.dataapp.accountCreation.AccountCreation;

/**
 * <h1>Form_Change</h1>
 * The Form_Change Java Class is used to change between Activities of the Data Application.
 * This Observer has been created to reduce the coupling that would be required between all the
 * packages that contain activities allowing for adding, deleting, and modifying of activities to
 * occur easily and with a reduce chance of errors occurring at a later date
 * Implements Form_Change_Observer interface
 * @author Patrick Crockford
 * @version 1.3
 * <h1>Last Edited</h1>
 * 17 Dec 2018
 * Jeremy Dunnet
 */

public class Form_Change implements Form_Change_Observer {
    private Factory factory;


    /**
     * Instantiates a new Form_Change.
     */
    public Form_Change(Factory factory) {
        this.factory = factory;
    }

    /**
     * @param activity_To_Change_To Enum specifying which form to change to from the current form.
     * @param context System Object with information about the current running acivity (to move system resources over to new one)
     * @return True if successfully created and used new Activity, else false
     * @throws NullPointerException if intent, Activity_Control, and/or Activity Objects are Null
     * @throws Invalid_Enum_Exception if Activity_Control Enum value is a non-valid value. Primary cause will be addition of new Enum in the Form_Change_Observer interface but not yet added to switch statement
     */
    @Override
    public boolean Change_Form(Activity_Control activity_To_Change_To, Context context) throws NullPointerException, Invalid_Enum_Exception {
        //Check Activity_Control Enum hasn't somehow been set to Null
        boolean valid = false;
        if (activity_To_Change_To != null && context != null) {
            Intent intent = null;
            // TODO: 17-Sep-18 Uncomment and modify when Activities have been created
            //Logic for which Activity to create and launch
            switch (activity_To_Change_To) {
                case Account_Creation:
                    intent =  new Intent(context, AccountCreation.class);
                    valid = true;
                    break;
                case Medical_Data_Input:
                    //activity = factory.Build_Medical_Data_Input_Activity();
                    valid = true;
                    break;
                case Account_Main_Menu:
                    //activity = factory.Build_Account_Main_Menu_Activity();
                    valid = true;
                    break;
                case Password_Recovery:
                    intent =  new Intent(context, PasswordRecovery.class);
                    valid = true;
                case Review:
                    //activity = factory.Build_Medical_Review_Activity();
                    valid = true;
                    break;
                case Account_Information:
                    //activity = factory.Build_Account_Information_Activity();
                    valid = true;
                    break;
                case Encrypt_and_Export:
                    //activity = factory.Build_Encrypt_and_Export_Activity();
                    valid = true;
                    break;
                case Gamification:
                    //activity = factory.Build_Gamification_Activity();
                    valid = true;
                    break;
                case Challenges:
                    intent =  new Intent(context, ChallengeGUI.class);
                    break;
                default:
                    //Shouldn't get here as it is based on enum, is a big error
                    throw new Invalid_Enum_Exception("Invalid Enum given to activity_To_Change_To");
            }
            //This Throws an NullPointerException if null, but shouldn't due to being created in a factory
                context.startActivity(intent);
        } else // Null value provided meaning the program is in a faulted state
        //Need to check which one has the null error value
        {
            if (activity_To_Change_To == null) {
                throw new NullPointerException("Null Enum Value given");
            } else {
                throw new NullPointerException("Null intent Value given");
            }
        }
        return valid;
    }

    /**
     * @param activity_To_Change_To Enum specifying which form to change to from the current form.
     * @param context System Object with information about the current running acivity (to move system resources over to new one)
     * @param fileMap Map that contains any important files that the next activity will use (save reinitializing any files multiple times)
     * @return True if successfully created and used new Activity, else false
     * @throws NullPointerException if intent, Activity_Control, and/or Activity Objects are Null
     * @throws Invalid_Enum_Exception if Activity_Control Enum value is a non-valid value. Primary cause will be addition of new Enum in the Form_Change_Observer interface but not yet added to switch statement
     */
    @Override
    public boolean Change_Form_File(Activity_Control activity_To_Change_To, Context context, HashMap<Time_Observer.Files, File> fileMap) throws NullPointerException, Invalid_Enum_Exception {
        //Check Activity_Control Enum hasn't somehow been set to Null
        boolean valid = false;
        if (activity_To_Change_To != null && context != null) {
            Intent intent = null;
            // TODO: 17-Sep-18 Uncomment and modify when Activities have been created
            //Logic for which Activity to create and launch
            switch (activity_To_Change_To) {
                case Account_Creation:
                    intent =  new Intent(context, AccountCreation.class);
                    intent.putExtra("fileMap", fileMap);
                    valid = true;
                    break;
                case Medical_Data_Input:
                    //activity = factory.Build_Medical_Data_Input_Activity();
                    valid = true;
                    break;
                case Account_Main_Menu:
                    //activity = factory.Build_Account_Main_Menu_Activity();
                    valid = true;
                    break;
                case Password_Recovery:
                    intent =  new Intent(context, PasswordRecovery.class);
                    intent.putExtra("fileMap", fileMap);
                    valid = true;
                case Review:
                    //activity = factory.Build_Medical_Review_Activity();
                    valid = true;
                    break;
                case Account_Information:
                    //activity = factory.Build_Account_Information_Activity();
                    valid = true;
                    break;
                case Encrypt_and_Export:
                    //activity = factory.Build_Encrypt_and_Export_Activity();
                    valid = true;
                    break;
                case Gamification:
                    //activity = factory.Build_Gamification_Activity();
                    valid = true;
                    break;
                case Challenges:
                    intent =  new Intent(context, ChallengeGUI.class);
                    intent.putExtra("fileMap", fileMap);
                    valid = true;
                    break;
                default:
                    //Shouldn't get here as it is based on enum, is a big error
                    throw new Invalid_Enum_Exception("Invalid Enum given to activity_To_Change_To");
            }
            //This Throws an NullPointerException if null, but shouldn't due to being created in a factory
            context.startActivity(intent);
        } else // Null value provided meaning the program is in a faulted state
        //Need to check which one has the null error value
        {
            if (activity_To_Change_To == null) {
                throw new NullPointerException("Null Enum Value given");
            } else {
                throw new NullPointerException("Null intent Value given");
            }
        }
        return valid;
    }

}
