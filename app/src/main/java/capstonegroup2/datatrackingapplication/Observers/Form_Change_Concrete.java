package capstonegroup2.datatrackingapplication.Observers;

/**
 * Created by Patrick on 20-Aug-18.
 */

public class Form_Change_Concrete implements Form_Change {
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
    public void Change_Form(Form_Control form_To_Change_To) {
        //Logic for which Activity to go to
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
                //Shouldn't get here as it is based on enum, Void can get here which is a big error
                throw new Run
                break;
        }
    }
}