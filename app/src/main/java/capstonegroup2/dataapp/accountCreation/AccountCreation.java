package capstonegroup2.dataapp.accountCreation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Factory.Factory;
import Validation.Validation;
import XML.Account_Writer;
import XML.Login_Reader;
import XML.Login_Writer;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;
import capstonegroup2.dataapp.R;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 07/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 6/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the Activity that allows a user to submit a form to obtain an account on the app (used to login and perform all actions of the app). The Activity gives feedback if any fields
 * are invalid - allowing the user to change them instead of restarting the form.
 */

/* VERSION HISTORY
 * 07/10/2018 - Created Activity design layout and added rudimentary code to display back for demo
 * 08/10/2018 - Finished up functionality related to activity (data collection and initial checks) and added draft calls to validation
 * 21/10/2018 - Fixed functionality to include security questions and added some validation code
 * 25/10/2018 - Fixed bugs that arose from testing
 * 6/12/2018 - Uncommented integration code and updated to work with XML/Validation
 */

/* REFERENCES
 * Basics of creating activity as a form (useful containers and attributes) adapted/learned from https://code.tutsplus.com/tutorials/android-essentials-creating-simple-user-forms--mobile-1758
 * Radio button implementation learned from https://developer.android.com/guide/topics/ui/controls/radiobutton
 * How to change spacing between items in a container learned from https://stackoverflow.com/questions/28730905/android-scrollview-spacing-between-items-in-linearlayout
 * Adding plain text hints as placeholders on input boxes learned from https://stackoverflow.com/questions/8221072/android-add-placeholder-text-to-edittext
 * Getting text values from views learned from https://www.mkyong.com/android/android-radio-buttons-example/
 * Getting string from strings.xml in code learned from https://stackoverflow.com/questions/7213924/access-string-xml-resource-file-from-java-android-code
 * Setting the spinner prompt learned from https://stackoverflow.com/questions/6385959/how-to-set-a-title-for-spinner-which-is-not-selectable
 * Alert dialog creation copied/adapted/learned from a previous Android implementation of my own (Jeremy Dunnet) - using the basics from developer.android.com
 * Moving EditText cursor learned from https://stackoverflow.com/questions/6217378/place-cursor-at-the-end-of-text-in-edittext
 * Showing error popups in an EditText learned from https://stackoverflow.com/questions/18225365/show-error-on-the-tip-of-the-edit-text-android
 * Idea on how to change text from TextWatcher learned from https://stackoverflow.com/questions/9498155/how-to-update-the-same-edittext-using-textwatcher/9498218
 * Event listeners for key presses learned from https://stackoverflow.com/questions/4282214/onkeylistener-not-working-on-virtual-keyboard and https://developer.android.com/reference/android/text/TextWatcher.html#onTextChanged(java.lang.CharSequence,%20int,%20int,%20int)
 * Chopping strings using substring learned from https://javarevisited.blogspot.com/2016/03/how-to-remove-first-and-last-character-from-String-in-java-example.html
 * Fixing a String.matches result learned from https://stackoverflow.com/questions/20165485/why-is-string-matches-returning-false-in-java
 * Creating a password checker elements adapted from https://github.com/yesterselga/password-strength-checker-android
 * Colouring a progress bar learned from https://stackoverflow.com/questions/2020882/how-to-change-progress-bars-progress-color-in-android
 * Placing progress bar next to an element learned from https://stackoverflow.com/questions/34915468/display-progress-bar-beside-the-text-in-listview
 * Removing error symbol from EditText learned from https://stackoverflow.com/questions/10206799/remove-error-from-edittext
 * Allowing errors on non-EditText elements learned from https://stackoverflow.com/questions/13508270/android-seterrorerror-not-working-in-textview
 * Making an element invisible learned from https://stackoverflow.com/questions/4480489/can-you-hide-an-element-in-a-layout-such-as-a-spinner-depending-on-an-activity
 * Activating ontouch learned from https://stackoverflow.com/questions/38865922/android-hide-toolbar-when-edittext-is-focused
 * Hiding element after focus change learned from https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
 * Setting a onitemselected listener for a spinner learned from https://stackoverflow.com/questions/20151414/how-can-i-use-onitemselected-in-android
 * And many more from https://developer.android.com
 */

public class AccountCreation extends Activity {

    //Classfields
    //EditTexts for the user input sections
    private EditText unameInput;
    private EditText passInput;
    private EditText sqInput;
    //Spinner we use for gamification settings
    private Spinner gameInput;
    //Spinner for the chosen security question
    private Spinner sqChoice;
    //Checkbox we use for notifications settings
    private CheckBox notInput;
    //Progress bar for password strength
    private ProgressBar strengthBar;
    //TextView fro password strength in english
    private TextView passStrength;
    //TextView for export - for error state
    private TextView exportText;
    //TextView for password guide
    private TextView passGuide;

    //Boolean for text change
    private Boolean unKeyCheck;
    private Boolean pKeyCheck;

    //Values for any options that are set via clicks
    private String exportValue;

    private Factory f;
    private Validation validator;
    private File loginFile;
    private File accountFile;

    //Constants - Change here to ease refactoring
    //Maximum length of username
    public static final int UNAME_MAX = 20;
    //Constants for validation of answers (since it is free input we need to detail what can be put in)
    private final int MINLENGTH = 1;
    private final int MAXLENGTH = 40; //Just a sample idea
    private final boolean[] ACHARPERM = {true, true, false, false}; //No numbers or special characters - just for simple tests now


    /* FUNCTION INFORMATION
     * NAME - onCreate
     * INPUTS - savedInstanceState
     * OUTPUTS - none
     * PURPOSE - This is the standard function to imitate creation of the activity this java code is tied to
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        //Find all the objects we need
        unameInput = findViewById(R.id.acUNInput);
        passInput = findViewById(R.id.acPassInput);
        gameInput = findViewById(R.id.acGameSett);
        notInput = findViewById(R.id.acNotSett);
        passStrength = findViewById(R.id.acPassStrength);
        strengthBar = findViewById(R.id.acStrengthBar);
        exportText = findViewById(R.id.acExportTitle);
        passGuide = findViewById(R.id.acPassGuide);
        sqInput = findViewById(R.id.acSQAnswer);
        sqChoice = findViewById(R.id.acSQs);

        passGuide.setVisibility(View.INVISIBLE); //Hide password strength indicator until start typing

        //Set initial values to false - with nothing in the boxes yet they haven't been checked
        unKeyCheck = false;
        pKeyCheck = false;

        f = Factory.Get_Factory();
        validator = f.Make_Validation();
        loginFile = new File("F:\\Uni\\Project\\Android\\Data-Tracking-Application-Android\\app\\src\\test\\java\\Integration\\StreamFour\\login_information.xml"); //TODO REPLACE WITH ACTUAL PATH WHEN FULLY INTEGRATED
        accountFile = new File("F:\\Uni\\Project\\Android\\Data-Tracking-Application-Android\\app\\src\\test\\java\\Integration\\StreamFour\\account_information.xml");

        /* TODO DELETE OR REENABLE DEPENDING ON WHAT CONTROLS WANTED FOR INSTANT FEEDBACK
        //Set an TextWatch listener for each of the two text inputs so that we can do some per character analysis
        //Username listener
        TextWatcher unInputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text  = s.toString(); //Get the string
                //If the text in the box is empty - don't bother with checking the characters inside
                if((text.length() - 1) >= 0)
                {
                    //Check for violating length limit
                    if(text.length() > UNAME_MAX)
                    {
                        //Remove the added character
                        unameInput.setText((text.substring(0, (text.length() - 1))));
                        //Display warning to the user
                        unameInput.setError("Usernames have a maximum length of 20 characters");
                        unameInput.setSelection((unameInput.getText().length())); //Place their cursor to the end of the line
                    }
                    else
                    {
                        //If there is an unaccepted character in the string
                        if (text.matches(".*[^a-zA-Z0-9_].*") && (unKeyCheck == false))
                        {
                            //Remove all matches to this regex
                            text = text.replaceAll("[^a-zA-Z0-9_]", "");
                            unameInput.setText(text); //Replace text to corrected string
                            //Display warning to the user
                            unameInput.setError("Usernames can only have A-Z, 0-9 and _\nSpecial Characters are not permitted");
                            unameInput.setSelection((unameInput.getText().length()));
                            unKeyCheck = true; //Say we have changed a key (since by editing the string we may call this method recursively)
                        }
                    }
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                unKeyCheck = false; //Since a key has changed - we may need to edit it
            }
        };*/
        TextWatcher pInputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text  = s.toString(); //Get the string
                //If the text in the box is empty - don't bother with checking the characters inside
                if((text.length() - 1) >= 0)
                {
                        /*
                        //If there is an unaccepted character in the string
                        if (text.matches(".*[^a-zA-Z0-9_].*") && (pKeyCheck == false))
                        {
                            //Remove all matches to this regex
                            text = text.replaceAll("[^a-zA-Z0-9_]", "");
                            passInput.setText(text); //Replace text to corrected string
                            //Display warning to the user
                            passInput.setError("Passwords can only have A-Z, 0-9 and _\nSpecial Characters are not permitted");
                            passInput.setSelection((passInput.getText().length()));
                            pKeyCheck = true; //Say we have changed a key (since by editing the string we may call this method recursively)
                        }
                        else //We only want one error at a time - The bad character takes precedence first since if we remove bad character,strength is same as previous entry
                        {
                            passwordStrength(text); //Check strength of password
                        }*/
                        passwordStrength(text); //This is needed for demo functionality
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pKeyCheck = false;
            }
        };
        //unameInput.addTextChangedListener(unInputTextWatcher); //Add the TextWatchers to the elements
        passInput.addTextChangedListener(pInputTextWatcher);
        //If TextWatcher is not picking up all hardware/software keyboard events - consider adding a onKeyListener to the view in addition (as that can pickup some but not all)

        passInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passGuide.setVisibility(View.VISIBLE);
                return false;
            }
        });
        passInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!passInput.hasFocus()) {
                    passGuide.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    /* FUNCTION INFORMATION
     * NAME - exportSet
     * INPUTS - view (tied to the radio group the method checks)
     * OUTPUTS - none
     * PURPOSE - This is the onClick event handler for the radio group that concerns the export settings of a user
     */
    public void exportSet (View view)
    {
        boolean checked = ((RadioButton) view).isChecked(); //Find out if the button was checked

        // Check which radio button was clicked
        switch (view.getId())
        {
            case R.id.acExportSettOne:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
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

    /* FUNCTION INFORMATION
     * NAME - createAccount
     * INPUTS - View (the button that acts as the submit button)
     * OUTPUTS - none
     * PURPOSE - This is the function to collate all information a user has provided and submitting it to the system to be converted into an account
     */
    public void createAccount (View view)
    {
        boolean created = false;

        final String algorithm = "SHA-256"; //Hashing algorithm we use
        MessageDigest md;
        try
        {
            md =  MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException("Hash failed " + e.getMessage()); //TODO CONSIDER THROWING CUSTOM EXCEPTION
        }

        //Get all the values that the user may have set
        boolean notBox = notInput.isChecked();
        String gameValue = (gameInput.getSelectedItem()).toString(); //We don't check this since it has a default value that is selected
        String sqValue = (sqChoice.getSelectedItem()).toString(); //Get the question the user selected
        String unameValue = (unameInput.getText()).toString(); //This two we need to check in case they weren't answered
        String passValue = (passInput.getText()).toString();
        String sqAnswer = (sqInput.getText()).toString();
        String notValue = "";

        //Translate notification check box to a string value - this can only be yes or no (if user doesn't answer, default it NO)
        if(notBox == true)
        {
            notValue = "Yes";
        }
        else
        {
            notValue = "No";
        }

        //Do a rolling check for empty username/password
        if(unameValue.equals(""))
        {
            unameInput.requestFocus();
            unameInput.setError("Please provide a username");
        }
        else
        {
            if(passValue.equals(""))
            {
                passInput.requestFocus();
                passInput.setError("Please provide a password");
            }
            else
            {
                if(passwordStrength(passValue) < 3 )
                {
                    passInput.requestFocus();
                    passInput.setError("Please provide a password with a strength of at least \"good\"");
                }
                else
                {
                    if(sqAnswer.equals("")) //COULD ALSO DO A LENGTH CHECK HERE MORE LIKELY ADD A TEXTWATCHER
                    {
                        sqInput.requestFocus();
                        sqInput.setError("Please provide an answer to security question");
                    }
                    else
                    {
                        if (exportValue == null) //Need to select an export setting - no default
                        {
                            exportText.requestFocus();
                            exportText.setError("Please select an export type");
                        }
                        else
                        {
                            created = true;
                        }
                    }
                }
            }
        }

        Validation.Validate_Result validResult; //Enum for result of validation
        validResult = validator.validateUsername(unameValue); //Call validation to check if the user input is valid
        //Do error checking for possible error enums
        if(validResult != Validation.Validate_Result.Pass) //If not a valid username
        {

            String err = validator.getValidatorError(validResult); //Get the specific error code
            unameInput.requestFocus();
            unameInput.setError(err); //Display to the user

        }
        else
        {

            validResult = validator.validatePassword(passValue); //Check the password is valid
            if (validResult != Validation.Validate_Result.Pass) {
                String err = validator.getValidatorError(validResult); //Get the specific error code
                passInput.requestFocus();
                passInput.setError(err); //Display to the user
            } else {
                validResult = validator.validateFreeInput(sqAnswer, MINLENGTH, MAXLENGTH, ACHARPERM[0], ACHARPERM[1], ACHARPERM[2], ACHARPERM[3]); //Check the answer is valid
                if (validResult != Validation.Validate_Result.Pass) {
                    String err = validator.getValidatorError(validResult); //Get the specific error code
                    sqInput.requestFocus();
                    sqInput.setError(err); //Display to the user
                } else {
                    boolean success = false;
                    String sqID = "";

                    //Write newly created account to file
                    Account_Writer ac = (Account_Writer) f.Make_Writer(Factory.XML_Writer_Choice.Account);
                    XML_Writer.Tags_To_Write job = XML_Writer.Tags_To_Write.Create;
                    Map<String, String> values = new HashMap<String, String>();

                    values.put("Name", unameValue);
                    byte[] hashedPass = md.digest( (passValue.getBytes()) );
                    values.put("Password", new String(hashedPass));
                    values.put("Security_Answer", sqAnswer);
                    values.put("Gamification", gameValue);
                    values.put("Notification", notValue);
                    values.put("Export_Settings", exportValue);

                    //Now we need to find the security question ID of the question selected
                    String[] questions = getResources().getStringArray(R.array.ac_security_question_entries);
                    String[] ids = getResources().getStringArray(R.array.ac_security_question_ids);
                    for(int ii= 0; ii < questions.length; ii++)
                    {
                        if(questions[ii].equals(sqValue)) //If the question matches
                        {
                            sqID = ids[ii];
                        }
                    }
                    values.put("Security_Question_ID", sqID);

                    try{
                        success = ac.Write_File(accountFile, values, job); //Create the account in the system
                    }
                    catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception e)
                    {
                        throw new RuntimeException("FIX THIS" + e.getMessage());
                    }

                    //If file succesfully written - add to login file so user can login later
                    if(success ==  true)
                    {
                        //LOGIN FILE
                        Login_Writer lw = (Login_Writer) f.Make_Writer(Factory.XML_Writer_Choice.Login);
                        job = XML_Writer.Tags_To_Write.New;
                        values = new HashMap<String, String>();

                        values.put("Account_Name", unameValue);
                        values.put("Password", new String(hashedPass));

                        try{
                            success = lw.Write_File(loginFile, values, job); //Create the account in the system
                        }
                        catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception e)
                        {
                            throw new RuntimeException("FIX THIS" + e.getMessage());
                        }

                        if(success == true)
                        {
                            //TODO LOGIN TO MAIN MENU
                            /* //TODO UNCOMMENT WHEN MAIN MENU DONE
                                //Call our Form_Change Observer to do the switching for us
                                fc.Change_From(Form_Change_Observer.Activity_Control.Main_Menu, this);
                            */
                        }
                        else
                        {
                            // Creating alert Dialog with one Button
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccountCreation.this);

                            // Setting Dialog Title
                            alertDialog.setTitle("File write failure");

                            // Setting Dialog Message
                            alertDialog.setMessage("Something went wrong trying to add you into the system - please reboot the app and try again.");

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
                    else
                    {
                        // Creating alert Dialog with one Button
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AccountCreation.this);

                        // Setting Dialog Title
                        alertDialog.setTitle("File write failure");

                        // Setting Dialog Message
                        alertDialog.setMessage("Something went wrong trying to add you into the system - please reboot the app and try again.");

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
            }

        }

    }

    /* FUNCTION INFORMATION
     * NAME - passwordStrength
     * INPUTS - password (the string we need to check for complexity)
     * OUTPUTS - none
     * PURPOSE - This is the function that uses a simple method of password strength testing so that the user creates a moderately strong password
     */
    private int passwordStrength(String password)
    {
        int strength = 0;

        if(password.length() < 10)
        {
            passInput.requestFocus(); //For when submit is pressed
            passInput.setError("Password must be at least 10 characters long"); //Tell user why is is not valid

            //Now display strength to user
            passStrength.setText(("Strength: WEAK"));
            strengthBar.setProgress(33);
            (strengthBar.getProgressDrawable()).setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN); //May set all progress bars to this colour - integration will need testing to confirm
        }
        else
        {

            passInput.setError(null); //Since we have now submitted a large enough value - remove red ! since it's not incorrect now

            if(password.matches(".*[a-z].*"))
            {
                strength++;
            }

            if(password.matches(".*[A-Z].*"))
            {
                strength++;
            }

            if(password.matches(".*[0-9].*"))
            {
                strength++;
            }

            if(password.length() > 14)
            {
                strength++;
            }

            switch (strength)
            {
                case 0:
                case 1:
                case 2:
                    //Now display strength to user
                    passStrength.setText(("Strength: WEAK"));
                    strengthBar.setProgress(33);
                    (strengthBar.getProgressDrawable()).setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN); //May set all progress bars to this colour - integration will need testing to confirm
                    break;
                case 3:
                    //Now display strength to user
                    passStrength.setText(("Strength: GOOD"));
                    strengthBar.setProgress(66);
                    (strengthBar.getProgressDrawable()).setColorFilter(Color.YELLOW, android.graphics.PorterDuff.Mode.SRC_IN); //May set all progress bars to this colour - integration will need testing to confirm
                    break;
                case 4:
                    //Now display strength to user
                    passStrength.setText(("Strength: EXCELLENT"));
                    strengthBar.setProgress(100);
                    (strengthBar.getProgressDrawable()).setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN); //May set all progress bars to this colour - integration will need testing to confirm
                    break;
                default:
                    //Now display strength to user
                    passStrength.setText(("Strength: WEAK"));
                    strengthBar.setProgress(33);
                    (strengthBar.getProgressDrawable()).setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN); //May set all progress bars to this colour - integration will need testing to confirm
            }
        }

        return strength;

    }

}
