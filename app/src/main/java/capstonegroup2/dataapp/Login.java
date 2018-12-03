package capstonegroup2.dataapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 3/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 3/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that is the first screen that a user will see - acting as both the splash and login screen, with links to creating an account and recover a password.
 */

/* VERSION HISTORY
 * 3/12/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * Hashing use learned from Security Research summary (https://drive.google.com/open?id=15PTGXy1QBaQXOnlrpta7S6xtsyXgT2g1) and https://developer.android.com/reference/java/security/MessageDigest
 * How to make text clickable learned from https://stackoverflow.com/questions/6226699/how-to-make-text-view-clickable-in-android
 * Setting up blinking text learned from https://stackoverflow.com/questions/3450839/blinking-text-in-android-view/11991435
 * Stopping an animation learned from https://stackoverflow.com/questions/4112599/how-to-stop-an-animation-cancel-does-not-work
 * And many more from https://developer.android.com/
 */

public class Login extends AppCompatActivity
{

    //Classfields
    /* //TODO UNCOMMENT WHEN INTEGRATED
    private Factory f; //The factory class we use to build every object in the app
    private Form_Change fc; //The Form_Change observer we will use for any activity changes in this activity
    private File loginFile = "PATH"; //Path to static login file where all created accounts have details stored
     */

    private ConstraintLayout splashLayout;
    private ConstraintLayout loginLayout;

    private TextView splashText;
    private TextView unameText;
    private TextView passText;

    private Button settingsButt;
    private Button registerButt;
    private Button recoverButt;
    private Button submitButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* //TODO UNCOMMENT WHEN INTEGRATED
        f = Factory.Get_Factory();
        fc = f.makeFormChange();
         */

        //Grab all the needed objects from the layout
        splashLayout = findViewById(R.id.splashLayout);
        loginLayout = findViewById(R.id.loginLayout);
        splashText = findViewById(R.id.loginSplash);
        unameText = findViewById(R.id.loginUnameBox);
        passText = findViewById(R.id.loginPassBox);
        settingsButt = findViewById(R.id.loginSettingsButt);
        registerButt = findViewById(R.id.loginRegisterButt);
        recoverButt = findViewById(R.id.loginRecoverButt);
        submitButt = findViewById(R.id.loginButt);

        //Hide the loginLayout until the user decides to login
        loginLayout.setVisibility(View.INVISIBLE);

        //Set up a blinking text animation - for some flair
        Animation anim = new AlphaAnimation(0.0f, 1.0f); //Alpha is a useful Animation object for fading in and out (what we aim to do)
                                                         //The floats describe how the animation will progress (0.0(invisible) to 1.0(opaque))
        anim.setDuration(500); //Change how long the animation runs for (in ms)
        anim.setStartOffset(500); //How long after creation to start (in ms)
        anim.setRepeatMode(Animation.REVERSE); //What to do when animation finishes (could do RESTART to do a loop animation)
        anim.setRepeatCount(Animation.INFINITE); //How many times to repeat animation
        splashText.startAnimation(anim);

    }

    /* FUNCTION INFORMATION
     * NAME - splashClick
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that hides the splash layout on user click to display the login layout to enable a user to actually login
     */
    public void splashClick(View view)
    {

        //Stop animation
        splashText.clearAnimation(); //To save memory if animation continued to run in background where no one would see it
                                     //We never return to the splash screen once clicked so safe to clear it instead of pause

        //Hide the splash screen and redisplay the login screen layout
        splashLayout.setVisibility(View.INVISIBLE);
        loginLayout.setVisibility(View.VISIBLE);

    }

    /* FUNCTION INFORMATION
     * NAME - settingsClick
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that switches the activity to the Settings page to edit some blanket settings for the app
     */
    public void settingsClick(View view)
    {

        //Create an alert dialog since this feature doesn't exist yet
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

        // Setting Dialog Title
        alertDialog.setTitle("Unimplemented feature");

        // Setting Dialog Message
        alertDialog.setMessage("Feature coming soon!");

        // Setting the finished button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); //Simply close screen
                    }
                });

        // Showing Alert Message
        alertDialog.show();

        /* //TODO UNCOMMENT IF SETTINGS EVER DESIGNED
        //Create the intent we want to change to
        Intent switchAct = new Intent(this, Settings.class);

        //Call our Form_Change Observer to do the switching for us
        fc.Change_From(Form_Change_Observer.Activity_Control.Settings, switchAct);
        */

    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    public void loginClick(View View)
    {
        //Grab the two strings the user input
        String uInput = (unameText.getText()).toString();
        if(uInput.equals(""))
        {
            unameText.requestFocus();
            unameText.setError("Please provide a username");
        }
        else
        {
            String pInput = (passText.getText()).toString();
            if(pInput.equals(""))
            {
                passText.requestFocus();
                passText.setError("Please provide a password");
            }
            else
            {

                /* //TODO REDO WHEN VALIDATION DONE
                Enum<Validate_Result> validResult; //Boolean check for if the entry by the user was valid

                validResult = validator.Validate_Username(uInput); //Call validation to check if the user input is valid
                //Do error checking for possible error enums
                if(validResult != Validate_Result.PASS) //If not a valid username
                {

                   String err = validator.getError(validResult); //Get the specific error code
                    unameText.setError(err); //Display to the user

                }
                else
                {

                if(validator.verify_username(uInput) != Validate_result.PASS) //If the account name doesn't exist
                {
                    //OR JUST SET OWN "Username does not exist" error
                    String err = validator.getError(validResult); //Get the specific error code
                    unameText.setError(err); //Display to the user
                }
                else
                {

                    validResult = validator.Validate_Password(pInput); //Call validation to check if the user input is valid
                    if(validResult != Validate_Result.PASS) //If not a valid username
                    {

                        String err = validator.getError(validResult); //Get the specific error code
                        passText.setError(err); //Display to the user

                    }
                    else
                    {
                    */

                        //Hash password (we only store hashed passwords to protect them)
                        final String algorithm = "SHA-256"; //Values to create the hash used later on
                        MessageDigest md;

                        try {
                            md = MessageDigest.getInstance(algorithm);
                        }
                        catch (NoSuchAlgorithmException e)
                        {
                            throw new IllegalStateException("No algorithm exists"); //TODO POSSIBLE REWORK TO CUSTOM EXCEPTION CLASS
                        }
                        byte[] answerBytes = pInput.getBytes();
                        byte[] hash = md.digest(answerBytes);
                        String hashedPass = new String(hash);

                        /* //TODO UNCOMMENT WHEN MAIN MENU DONE
                        lr = f.Make_Login_Reader();
                        Map<String, String> userInfo;
                        List<XML_Reader.Tags_To_Read> list = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Password));

                        userInfo = lr.Read_File(loginFile, list, uInput);

                        String storedPass = userInfo.get("Password");

                        if(storedPass.equals(hashedPass))
                        {

                            //Create the intent we want to change to
                            Intent switchAct = new Intent(this, MainMenu.class);
                            //Need to add username in as a bundle parameter to pass to main menu

                            //Call our Form_Change Observer to do the switching for us
                            fc.Change_From(Form_Change_Observer.Activity_Control.Main_Menu, switchAct);

                        }

                    }
                    */

                //TODO REMOVE WHEN INTEGRATED

                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

                // Setting Dialog Title
                alertDialog.setTitle("Login Results");

                // Setting Dialog Message
                alertDialog.setMessage("You logged in with:\nUsername: " + uInput + "\nPassword: " + pInput + "\nHashed Password: " + hashedPass);

                // Setting the finished button
                alertDialog.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); //Simply close screen
                            }
                        });

                // Showing Alert Message
                alertDialog.show();
            }
        }

    }

    /* FUNCTION INFORMATION
     * NAME - registerClick
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that switches the activity to AccountCreation if they need to make a new account
     */
    public void registerClick(View View)
    {

        //TODO REMOVE WHEN INTEGRATED
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

        // Setting Dialog Title
        alertDialog.setTitle("Missing Transition - AC");

        // Setting Dialog Message
        alertDialog.setMessage("Purely here for test purposes");

        // Setting the finished button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); //Simply close screen
                    }
                });

        // Showing Alert Message
        alertDialog.show();

        /* //TODO UNCOMMENT WHEN INTEGRATED
        //Create the intent we want to change to
        Intent switchAct = new Intent(this, AccountCreation.class);

        //Call our Form_Change Observer to do the switching for us
        fc.Change_From(Form_Change_Observer.Activity_Control.Account_Creation, switchAct);
        */
    }

    /* FUNCTION INFORMATION
     * NAME - recoverClick
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that switches the activity to the PasswordRecovery if user has forgotten their password
     */
    public void recoverClick(View view)
    {

        //TODO REMOVE WHEN INTEGRATED
        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

        // Setting Dialog Title
        alertDialog.setTitle("Missing Transition - PR");

        // Setting Dialog Message
        alertDialog.setMessage("Purely here for test purposes");

        // Setting the finished button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); //Simply close screen
                    }
                });

        // Showing Alert Message
        alertDialog.show();

        /* //TODO UNCOMMENT WHEN INTEGRATED
        //Create the intent we want to change to
        Intent switchAct = new Intent(this, PasswordRecovery.class);

        //Call our Form_Change Observer to do the switching for us
        fc.Change_From(Form_Change_Observer.Activity_Control.Password_Recovery, switchAct);
        */

    }

}
