package Observers;

import android.content.Intent;

/**
 * Created by Patrick on 19-Aug-18.
 * Last Edited by Patrick on 20-Aug-18 10pm
 * Changes:
 * 19th Aug
 * Created Interface 'Form_Change', and created Enum and Change_Form Method
 * Added Comment Block
 *
 * 20th Aug
 * Changed Comment Style to Java Doc
 * Added Context context to the Change_Form() Method
 * Removed Enum Values : Login, and Medical_State_Calculator as they wont be reached through observers, instead back up the call stack.
 *
 * 21st Aug
 * Changed Change_Form() parameter from context to Intent intent, as this can be created within the calling activity and is more logical
 */

public interface Form_Change {
    /**
     * Enum to control the logic of the form changing. This is used to allow or deny access to either the Delete Form
     * or the Save Form concrete classes within the Observer Package so outside classes just send where they want to go
     * and doesn't require any knowledge of how to or what needs to happen.
     * To Facilitate this each concrete class will check what Form_Control value has been given, in turn, and if valid it will execute the required form change
     * Otherwise it will return, and the next Form_Change Observer will execute the check, until the end of the list has been reached.
     * This List will have a length = number of Concrete Form_Change classes.
     */
    enum Form_Control {Account_Creation, Medical_Data_Input, Account_Main_Menu, Password_Recovery, Review, Account_Information, Encrypt_and_Export, Gamification}

    /**
     * @param form_To_Change_To  Enum specifying which form to change to from the current form.
     * @param intent
     * @return
     */
    boolean Change_Form(Form_Control form_To_Change_To,  Intent intent);
}
