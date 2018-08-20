package capstonegroup2.datatrackingapplication.Observers;

/**
 * Created by Patrick on 20-Aug-18.
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
    enum Form_Control {}

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

    // NOTE: This may need a second parameter with a reference back to the calling form, so that form can be deleted if required, with a default value of null if not.
    public void Change_Form(Form_Control form_To_Change_To);
}
