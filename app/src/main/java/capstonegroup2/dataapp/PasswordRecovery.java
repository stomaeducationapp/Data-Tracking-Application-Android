package capstonegroup2.dataapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Factory.Factory;
import Validation.Validation;
import XML.Account_Reader;
import XML.Login_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 20/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 22/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles a user's request to recover their password, but identifying the username they want to recover the password for and testing that they are the owner
 * of that account by making them answer the security question they set at account creation. The user is then logged on and directed to password reset.
 */

/* VERSION HISTORY
 * 20/10/2018 - Created file and added comment design path for future coding
 * 21/10/2018 - Finished base functionality for testing
 * 22/10/2018 - Added attempts to user name checking and performed testing
 */

/* REFERENCES
 * How to layer two layouts over each other learned from https://stackoverflow.com/questions/19424443/how-to-put-2-layouts-on-top-of-each-others
 * Basic setup and layout adapted from my previous activity in this project AccountCreation
 * Creating a list with default values learned from https://stackoverflow.com/questions/21696784/how-to-declare-an-arraylist-with-values
 * Hashing use learned from Security Research summary (https://drive.google.com/open?id=15PTGXy1QBaQXOnlrpta7S6xtsyXgT2g1) and https://developer.android.com/reference/java/security/MessageDigest
 * And many more from https://developer.android.com/
 */

public class PasswordRecovery extends Activity {

    //Classfields
    //private Validation validator; //Validation object for checking user names and answers
    private Factory f; //Factory for creating all objects
    private String correctUser; //Username of user who passes account check - need it to re access file for security question checks
    private Login_Reader lr; //XML_Readers that are tied to the user above
    private File loginFile; //File for checking if accounts exist
    private Account_Reader ar;
    private File accountFile; //File for reading in security questions
    private int tries; //Amount of goes a user gets in a row to answer the question correctly

    //Objects in the first layout (User identification)
    private ConstraintLayout userLayout;
    private TextView userTitle;
    private EditText userText;
    private Button userButt;

    //Objects in the second layout (Security Question answer)
    private ConstraintLayout questionLayout;
    private TextView questionTitle;
    private TextView questionText;
    private EditText questionAnswer;
    private Button questionButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        f = Factory.Get_Factory();
        loginFile = new File("F:\\Uni\\Project\\Android\\Data-Tracking-Application-Android\\app\\src\\test\\java\\Integration\\StreamFour\\login_information.xml"); //TODO REPLACE WITH ACTUAL PATH WHEN FULLY INTEGRATED

        //Grab references to all objects on the screen
        userLayout = findViewById(R.id.pr_user_layout);
        userTitle = findViewById(R.id.user_text);
        userText = findViewById(R.id.user_entry);
        userButt = findViewById(R.id.user_button);

        questionLayout = findViewById(R.id.pr_question_layout);
        questionTitle = findViewById(R.id.question_title);
        questionText = findViewById(R.id.question_text);
        questionAnswer = findViewById(R.id.question_entry);
        questionButt = findViewById(R.id.question_button);

        //Now we want the layout to only display the user layout (for now)
        questionLayout.setVisibility(View.INVISIBLE);
        userLayout.setVisibility(View.VISIBLE);

        tries = 0; //Rest every time we create

    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    public void findUser(View view)
    {

        Validation.Validate_Result validResult; //Boolean check for if the entry by the user was valid
        Validation validator = f.Make_Validation();
        correctUser = (userText.getText()).toString(); //Store it as the valid classfield so we can use it later

        validResult = validator.validateUsername(correctUser); //Call validation to check if the user input is valid
        //Do error checking for possible error enums
        if(validResult != Validation.Validate_Result.Pass) //If not a valid username
        {

            String err = validator.getValidatorError(validResult); //Get the specific error code
            userText.requestFocus();
            userText.setError(err); //Display to the user

        }
        else
        {

                lr = (Login_Reader) f.Make_Reader(Factory.XML_Reader_Choice.Login);
                Map<String, String> userInfo;
                List<XML_Reader.Tags_To_Read> list = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Account_Name));

                try{
                    userInfo = lr.Read_File(loginFile, list, correctUser);
                }
                catch(XML_Reader_Exception e)
                {
                    throw new RuntimeException ("FIX THIS" + e.getMessage());
                }

                if(userInfo.isEmpty()) //No entries returned - no user by that name
                {
                    userText.requestFocus();
                    userText.setError("No user found with that name.");
                    tries = tries + 1;
                }
                else
                {

                    ar = (Account_Reader) f.Make_Reader(Factory.XML_Reader_Choice.Account);
                    list = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Security_Question_ID));
                    //TODO GRAB ACCOUNT FILE PATH FROM LOGIN FILE
                    accountFile = new File("F:\\Uni\\Project\\Android\\Data-Tracking-Application-Android\\app\\src\\test\\java\\Integration\\StreamFour\\account_information.xml");

                    try{
                        userInfo = ar.Read_File(accountFile, list, correctUser);
                    }
                    catch(XML_Reader_Exception e)
                    {
                        throw new RuntimeException ("FIX THIS" + e.getMessage());
                    }

                    String qID = userInfo.get("Security_Question_ID"); //Store the ID of the question

                    /*lr = factory.Make_Login_Reader();
                    Map<String, String> qInfo;

                    qInfo = lr.Read_File(file, list, qID); //Since each qID is unique - can be used like an account name
                    String question = qInfo.get("Security Question"); //Fetch the question text

                    //TODO FIND OUT ABOUT IF NEED AN ASYNC LOADING SCREEN

                    questionText.setText(question); //Display question to the user
                    */

                    userLayout.setVisibility(View.INVISIBLE); //Hide user view
                    questionLayout.setVisibility(View.VISIBLE); //Display question view

                }

        }

        if(tries >= 3) //If user has exhausted all correct tries
        {
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PasswordRecovery.this);

            // Setting Dialog Title
            alertDialog.setTitle("Max Attempts Reached");

            // Setting Dialog Message
            alertDialog.setMessage("I'm sorry, but you have used up your available tries for now.");

            // Setting the finished button
            alertDialog.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            //GO BACK TO LOGIN SCREEN
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }


    }

    /* FUNCTION INFORMATION
     * NAME - answerQuestion
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the provided answer of the user and checks it against the stored value. If correct allows them to login.
     */
    public void answerQuestion(View view)
    {

        String answer = (questionAnswer.getText()).toString(); //Get user's answer
        final String algorithm = "SHA-256"; //Values to create the hash used later on
        MessageDigest md;

        //TODO REFACTOR WHEN INTEGRATING AND OTHER FUNCTIONALITY DONE
        /*List<XML_Reader.Tags_To_Read> list = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Security_Answer));
        String realAnswer = ar.Read_File(file, list, correctUser); //Get user's stored (hashed) answer to compare
        */

        //Since the realAnswer is stored securely as a hash - we need to hash the user's answer to check if they are the same
        try {
            md = MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new IllegalStateException("No algorithm exists"); //TODO POSSIBLE REWORK TO CUSTOM EXCEPTION CLASS
        }
        byte[] answerBytes = answer.getBytes();
        byte[] hash = md.digest(answerBytes);
        String hashedAnswer = new String(hash);

        //DEMO/TESTING PURPOSES ONLY TODO DELETE
        String realAnswer = "";
        switch (correctUser)
        {
            //Each case simulates a search for a valid user
            case "Bob":
                realAnswer = new String(md.digest(("Alice").getBytes()));
                break;
            case "Alice":
                realAnswer = new String(md.digest(("Miss Krabapple").getBytes()));
                break;
            case "Hannes":
                realAnswer = new String(md.digest(("Honda Accord").getBytes()));
                break;
            default:
                questionAnswer.setError("Question does not exist.");
                break;

        }

        if(hashedAnswer.equals(realAnswer)) //If answer was correct
        {
            //LOGIN TO MAIN MENU

            //DEMO/TESTING PURPOSES ONLY TODO DELETE
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PasswordRecovery.this);

            // Setting Dialog Title
            alertDialog.setTitle("Form Submission Results");

            // Setting Dialog Message
            alertDialog.setMessage("You were user: " + correctUser + "\nWith Question : " + (questionText.getText()).toString() + "\nWith Answer (unhashed): " + answer + "\nWith Answer (hashed): " + hashedAnswer);

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
        else
        {
            questionAnswer.setError("That answer is not correct");
            questionAnswer.requestFocus();
            tries = tries + 1;
        }

        if(tries >= 3) //If user has exhausted all correct tries
        {
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(PasswordRecovery.this);

            // Setting Dialog Title
            alertDialog.setTitle("Max Attempts Reached");

            // Setting Dialog Message
            alertDialog.setMessage("I'm sorry, but you have used up your available tries for now.");

            // Setting the finished button
            alertDialog.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            //GO BACK TO LOGIN SCREEN
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }

    }

}
