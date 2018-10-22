package capstonegroup2.dataapp;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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

@RunWith(RobolectricTestRunner.class)
public class PasswordRecoveryTest {

    private Activity testAct;

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Before
    public void setUp() {
        testAct = Robolectric.setupActivity(PasswordRecovery.class);
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    private void blank() {
        testAct.recreate(); //Resets the activity
    }


    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void setupNotNull() {
        assertNotNull(testAct);
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void findUserValid() {
        blank();
        EditText userField = testAct.findViewById(R.id.user_entry);
        userField.setText("Bob"); //Grab user field an set to a valid value
        (testAct.findViewById(R.id.user_button)).callOnClick(); //Submit the form

        assertEquals("User form hidden", View.INVISIBLE, (testAct.findViewById(R.id.pr_user_layout).getVisibility())); //Check that the form has disappeared and the next one has appeared
        assertEquals("Question form visible", View.VISIBLE, (testAct.findViewById(R.id.pr_question_layout).getVisibility())); //Indicates functionality worked
        assertEquals("Question has loaded", "What is the name of the first person you kissed?", ((TextView) (testAct.findViewById(R.id.question_text))).getText()); //Check the right question has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void findUserInvalid() {
        blank();
        EditText userField = testAct.findViewById(R.id.user_entry);
        userField.setText("Jeremy"); //Grab user field an set to a valid value
        (testAct.findViewById(R.id.user_button)).callOnClick(); //Submit the form

        assertEquals("User form hidden", View.VISIBLE, (testAct.findViewById(R.id.pr_user_layout).getVisibility())); //Check that the form remains and next one is still hidden
        assertEquals("Question form visible", View.INVISIBLE, (testAct.findViewById(R.id.pr_question_layout).getVisibility())); //Indicates functionality worked
        assertEquals("Error displayed", true, (testAct.findViewById(R.id.user_entry)).isFocused());
        assertEquals("Error content", "Username does not exist.", ((TextView) (testAct.findViewById(R.id.user_entry))).getError()); //Check the error has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void findUserMaxTries() {
        blank();
        findUserInvalid(); //Have 3 incorrect guesses
        findUserInvalid();
        findUserInvalid();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" );
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id);

        assertEquals("Alert title is correct", "Max Attempts Reached", alertTitle.getText());
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void answerQuestionValid() {
        blank();
        EditText qField = testAct.findViewById(R.id.question_entry);
        qField.setText("Alice"); //Grab user field an set to a valid value
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Submit the form

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" );
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id);

        assertEquals("Alert title is correct", "Form Submission Results", alertTitle.getText());

    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void answerQuestionInvalid() {
        blank();
        EditText qField = testAct.findViewById(R.id.question_entry);
        qField.setText("Jeremy"); //Grab user field an set to a valid value
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Submit the form

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.question_entry)).isFocused());
        assertEquals("Error content", "That answer is not correct", ((TextView) (testAct.findViewById(R.id.question_entry))).getError()); //Check the error has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUser
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that takes the username the user has entered in, and checks if it exists. If it does it locates their security question for them to answer
     */
    @Test
    public void answerQuestionMaxTries() {
        blank();
        answerQuestionInvalid(); //Have 3 incorrect guesses
        answerQuestionInvalid();
        answerQuestionInvalid();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" );
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id);

        assertEquals("Alert title is correct", "Max Attempts Reached", alertTitle.getText());
    }
}