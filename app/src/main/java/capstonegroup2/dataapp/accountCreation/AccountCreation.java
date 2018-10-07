package capstonegroup2.dataapp.accountCreation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import capstonegroup2.dataapp.R;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 07/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 07/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the Activity that allows a user to submit a form to obtain an account on the app (used to login and perform all actions of the app). The Activity gives feedback if any fields
 * are invalid - allowing the user to change them instead of restarting the form.
 */

/* VERSION HISTORY
 * 24/08/2018 - Created Activity design layout and added rudimentary code to display back for demo
 */

/* REFERENCES
 * Basics of creating activity as a form (useful containers and attributes) adapted/learned from https://code.tutsplus.com/tutorials/android-essentials-creating-simple-user-forms--mobile-1758
 * Radio button implementation learned from https://developer.android.com/guide/topics/ui/controls/radiobutton
 * How to change spacing between items in a container learned from https://stackoverflow.com/questions/28730905/android-scrollview-spacing-between-items-in-linearlayout
 * Adding plain text hints as placeholders on input boxes learned from https://stackoverflow.com/questions/8221072/android-add-placeholder-text-to-edittext
 * Getting text values from views learned from https://www.mkyong.com/android/android-radio-buttons-example/
 * Getting string from strings.xml in code learned from https://stackoverflow.com/questions/7213924/access-string-xml-resource-file-from-java-android-code
 * Setting the spinner prompt learned from https://stackoverflow.com/questions/6385959/how-to-set-a-title-for-spinner-which-is-not-selectable
 * Alert dialog creation copied/adapted/learned from a previous Android implementation of my own (Jeremy Dunnet) - using the basics from debeloper.android.com
 * And many more from https://developer.android.com
 */

public class AccountCreation extends Activity {

    //Classfields
    //All in the layout we need to access later on
    //EditTexts for the user input sections
    private EditText unameInput;
    private EditText passInput;
    //Spinner we use for gamification settings
    private Spinner gameInput;
    //Checkbox we use for notifications settings
    private CheckBox notInput;

    //Values for any options that are set via clicks
    private String exportValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        unameInput = findViewById(R.id.acUNInput);
        passInput = findViewById(R.id.acPassInput);
        gameInput = findViewById(R.id.acGameSett);
        notInput = findViewById(R.id.acNotSett);
        gameInput.setPrompt(getString(R.string.ac_gamification_option_title));

    }

    public void exportSet (View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId())
        {
            case R.id.acExportSettOne:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText());
                }
                break;
            case R.id.acExportSettTwo:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText());
                }
                break;
            case R.id.acExportSettThree:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText());
                }
                break;
            default:
                break;

        }
    }

    public void createAccount (View view)
    {
        //Get all the values that the user may have set
        boolean notBox = notInput.isChecked();
        String gameValue = (gameInput.getSelectedItem()).toString();
        String unameValue = (unameInput.getText()).toString();
        String passValue = (passInput.getText()).toString();
        String notValue = "";

        //Translate notification check box to a string value
        if(notBox == true)
        {
            notValue = "Yes";
        }
        else
        {
            notValue = "No";
        }

        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccountCreation.this);

        // Setting Dialog Title
        alertDialog.setTitle("Form Submission Results");

        // Setting Dialog Message
        alertDialog.setMessage("This is what you submitted\n" + "Username: " + unameValue + "\nPassword: " + passValue + "\nGamification Settings: " + gameValue + "\nData Export Settings: " + exportValue + "\nNotification Settings: " + notValue);

        // Setting the finished button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

}
