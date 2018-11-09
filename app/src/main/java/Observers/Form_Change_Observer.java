package Observers;

import android.content.Intent;

/**
 * <h1>Form_Change_Observer</h1>
 * The Form_Change_Observer Interface Java Class is used to Allow access to the package private concrete classes that inherit from it.
 * It also stores the Enum Activity_Control required to navigate to a new activity through the Concrete Observers.
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h>Changes</h1>
 * 19 Aug
 * Created Interface 'Form_Change_Observer', and created Enum and Change_Form Method, Patrick Crockford
 * Added Comment Block, Patrick Crockford
 * <p>
 * 20th Aug
 * Changed Comment Style to Java Doc, Patrick Crockford
 * Added Context context to the Change_Form() Method, Patrick Crockford
 * Removed Enum Values : Login, and Medical_State_Calculator as they wont be reached through observers, instead back up the call stack, Patrick Crockford
 * <p>
 * 21st Aug
 * Changed Change_Form() parameter from context to Intent intent, as this can be created within the calling activity and is more logical, Patrick Crockford
 * <p>
 * 27th Aug
 * Added Throws NullPoint and Invalid_Enum exception method signatures, Patrick Crockford
 * <p>
 * 29th Aug
 * Renamed to Form_Change_Observer, Patrick Crockford
 * Update JavaDoc code to comply with format, Patrick Crockford
 */
public interface Form_Change_Observer {

    /**
     * Enum to control the logic of the form changing. This is used to allow or deny access to either the Delete Form
     * or the Save Form concrete classes within the Observer Package so outside classes just send where they want to go
     * and doesn't require any knowledge of how to or what needs to happen.
     * To Facilitate this each concrete class will check what Activity_Control value has been given, in turn, and if valid it will execute the required form change
     * Otherwise it will return, and the next Form_Change_Observer Observer will execute the check, until the end of the list has been reached.
     * This List will have a length = number of Concrete Form_Change_Observer classes.
     */
    enum Activity_Control {
        Account_Creation, Medical_Data_Input, Account_Main_Menu, Password_Recovery, Review, Account_Information, Encrypt_and_Export, Gamification, Challenges
    }

    /**
     * @param activity_To_Change_To Enum specifying which form to change to from the current form.
     * @param intent
     * @return True if successfully created and used new Activity, else false
     * @throws NullPointerException   if intent, Activity_Control, and/or Activity Objects are Null
     * @throws Invalid_Enum_Exception if Activity_Control Enum value is a non-valid value. Primary cause will be addition of new Enum in the Form_Change_Observer interface but not yet added to switch statement
     */
    boolean Change_Form(Activity_Control activity_To_Change_To, Intent intent) throws NullPointerException, Invalid_Enum_Exception;
}
