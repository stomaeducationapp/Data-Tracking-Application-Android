package capstonegroup2.dataapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 20/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 20/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles a user's request to recover their password, but identifying the username they want to recover the password for and testing that they are the owner
 * of that account by making them answer the security question they set at account creation. The user is then logged on and directed to password reset.
 */

/* VERSION HISTORY
 * 20/10/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * How to layer two layouts over each other learned from https://stackoverflow.com/questions/19424443/how-to-put-2-layouts-on-top-of-each-others
 * Basic setup and layout adapted from my previous activity in this project AccountCreation
 * And many more from https://developer.android.com/
 */

public class PasswordRecovery extends Activity {
    /* FUNCTION INFORMATION
     * NAME - exportFile
     * INPUTS - userFile (file to be exported)
     * OUTPUTS - none
     * PURPOSE - This is the function that
     */

    //Classfields
    private Validation validator;
    private String correctUser; //Username of user who passes account check - need it to re access file for security question checks

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

    public PasswordRecovery() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

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

    }

    public void findUser(View view)
    {

        //TODO UPDATE WHEN VALIDATION IS FINISHED
        Enum<Validate_Result> validResult = false; //Boolean check for if the entry by the user was valid
        String user = (userText.getText()).toString();

        validResult = validator.Validate_Username(user); //Call validation to check if the user input is valid
        //Do error checking for possible error enums

        //Else pass to check account exists

        //If exists then call account reader to pass back security question id
        //Store user in field so we can call back later

        //Call security question file and find id related question

        //TODO FIND OUT ABOUT IF NEED AN ASYNC LOADING SCREEN

        //edit question view text to display security question

        //Hide user view and un hide question view


    }

    public void answerQuestion(View view)
    {

        //Get answer

        //Hash answer

        //Read hashed answer from users file
        //Check if equal

        //If correct then log user in

    }

}
