package Observers;

import android.app.Activity;
import android.content.Intent;


/**
 *
 * Created by Patrick on 19-Aug-18.
 * Last Edited by Patrick on 21-Aug-18 10pm
 * <p>
 * Changes:
 * 19th Aug
 * Created Class 'Save_Form_Change', and created the switch statement for Form_Control enum
 * Added Save_Current_Activity() method
 * <p>
 * 20th Aug
 * Renamed to Form_Change_Concrete due change in how activities will be saved, these are handled by the activities themselves
 * Deleted Save_Current_Activity() method
 * Added Debug Code for Unit Testing concrete Class, and will test Form_Change Interface at the same time
 * Added JavaDoc
 * <p>
 * 21st Aug
 * Changed Change_Form() parameter from context to Intent intent, as this can be created within the calling activity and is more logical
 * Refactored code to allow for this change to parameter
 * Added Null Checks for method parameters security
 * 29th Aug
 * Update JavaDoc code to comply with format
 */


// TODO: 25-Aug-18 When factory class is created uncomment and also comment activity creation and returns when integrating
class Form_Change_Concrete implements Form_Change {
    //private Factory factory;

    public Form_Change_Concrete(/*Factory factory*/) {
        //this.factory = factory;
    }

    /**
     * @param form_To_Change_To Enum specifying which form to change to from the current form.
     * @param intent
     * @return
     * @throws RuntimeException
     */

    @Override
    public boolean Change_Form(Form_Control form_To_Change_To, Intent intent) throws NullPointerException, Invalid_Enum_Exception {
        //Check Form_Control Enum hasn't somehow been set to Null
        boolean valid = false;
        if (form_To_Change_To != null && intent != null) {
            Activity activity = null;
            //// TODO: 20-Aug-18 Need to change names as the classes/packages are created so it fits in and switches correctly
            //Logic for which Activity to create and launch
            switch (form_To_Change_To) {
                case Account_Creation:
                    //activity = factory.Build_Account_Creation_Activity();
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
                    //activity = factory.Build_Password_Recovery_Activity();
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
                default:
                    //Shouldn't get here as it is based on enum, Void can get here which is a big error
                    throw new Invalid_Enum_Exception("Invalid Enum given to Form_Change_Concrete");
            }
            try {
                //activity.startActivity(intent);
            } catch (NullPointerException e) {
            }
        } else // Null value provided meaning the program is in a faulted state
        //Need to check which one has the null error value
        {
            if (form_To_Change_To == null) {
                throw new NullPointerException("Null Enum Value given");
            } else {
                throw new NullPointerException("Null intent Value given");
            }
        }
        return valid;
    }
}
