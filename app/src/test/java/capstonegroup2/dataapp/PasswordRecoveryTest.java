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
 * CREATOR - Jeremy Dunnet 22/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 22/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the unit test for the Password Recovery UI
 */

/* VERSION HISTORY
 * 22/10/2018 - Created file and completed testing
 */

/* REFERENCES
 * Robolectric basic tutorial learned from http://robolectric.org/ and https://android.jlelse.eu/how-to-write-android-unit-tests-using-robolectric-27341d530613
 * How to test for alert dialog boxes in robolectric learned from https://stackoverflow.com/questions/47655855/how-to-make-robolectric-test-for-alertdialog
 * How to get titles from alert boxes learned from https://stackoverflow.com/questions/29161994/how-to-get-the-alertdialog-title/29162440#29162440
 * Difference for test focus methods learned from https://stackoverflow.com/questions/33022310/what-is-the-difference-between-hasfocus-and-isfocused-in-android
 * Re-instancing an activity learned from https://stackoverflow.com/questions/2486934/programmatically-relaunch-recreate-an-activity
 * And many more from https://developer.android.com/
 */

@RunWith(RobolectricTestRunner.class)
public class PasswordRecoveryTest {

    private Activity testAct; //The activity we will be testing

    /* FUNCTION INFORMATION
     * NAME - setUp
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that creates the initial activity object we will run our tests on (we will re uses it a few times
     */
    @Before
    public void setUp() {
        testAct = Robolectric.setupActivity(PasswordRecovery.class);
    }

    /* FUNCTION INFORMATION
     * NAME - blank
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that rebuilds the activity (to zero the attempts feature for different test cases)
     */
    private void blank() {
        testAct.recreate(); //Resets the activity
    }


    /* FUNCTION INFORMATION
     * NAME - setupNotNull
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests if the activity we built in setup is not null (tests for successful activity onCreate functionality)
     */
    @Test
    public void setupNotNull() {
        assertNotNull(testAct);
    }

    /* FUNCTION INFORMATION
     * NAME - findUserValid
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the user form part of the activity when it receives a valid user
     */
    @Test
    public void findUserValid() {
        blank();
        EditText userField = testAct.findViewById(R.id.user_entry);
        userField.setText("Bob"); //Grab user field an set to a valid value
        (testAct.findViewById(R.id.user_button)).callOnClick(); //Submit the form

        assertEquals("User form hidden", View.INVISIBLE, (testAct.findViewById(R.id.pr_user_layout).getVisibility())); //Check that the form has disappeared and the next one has appeared
        assertEquals("Question form visible", View.VISIBLE, (testAct.findViewById(R.id.pr_question_layout).getVisibility())); //Indicates functionality worked
        assertEquals("Question has loaded", "What is the name of the first person you kissed?", ((TextView) (testAct.findViewById(R.id.pr_question_text))).getText()); //Check the right question has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUserInvalid
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the user form part of the activity when it receives an invalid user
     */
    @Test
    public void findUserInvalid() {
        EditText userField = testAct.findViewById(R.id.user_entry);
        userField.setText("Jeremy"); //Grab user field an set to an invalid value
        (testAct.findViewById(R.id.user_button)).callOnClick(); //Submit the form

        assertEquals("User form hidden", View.VISIBLE, (testAct.findViewById(R.id.pr_user_layout).getVisibility())); //Check that the form remains and next one is still hidden
        assertEquals("Question form visible", View.INVISIBLE, (testAct.findViewById(R.id.pr_question_layout).getVisibility())); //Indicates functionality worked
        assertEquals("Error displayed", true, (testAct.findViewById(R.id.user_entry)).isFocused());
        assertEquals("Error content", "Username does not exist.", ((TextView) (testAct.findViewById(R.id.user_entry))).getError()); //Check the error has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUserMaxTries
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the user form displays the error when maximum attempts reached
     */
    @Test
    public void findUserMaxTries() {
        blank(); //Make sure tries is set to zero
        findUserInvalid(); //Have 3 incorrect guesses
        findUserInvalid();
        findUserInvalid();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Find the id of the alert title
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //Grab the TextView containing the title

        assertEquals("Alert title is correct", "Max Attempts Reached", alertTitle.getText());
    }

    /* FUNCTION INFORMATION
     * NAME - findAnswerValid
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the user form part of the activity when it receives a valid answer
     */
    @Test
    public void answerQuestionValid() {
        findUserValid(); //Set up the question screen
        EditText qField = testAct.findViewById(R.id.question_entry);
        qField.setText("Alice"); //Grab answer field an set to a valid value
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Submit the form

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Since the correct answer yields an alert detailing all info sent
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //We check for the alert title (not the next screen)

        assertEquals("Alert title is correct", "Form Submission Results", alertTitle.getText());

    }

    /* FUNCTION INFORMATION
     * NAME - findAnswerValid
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the user form part of the activity when it receives an invalid answer
     */
    @Test
    public void answerQuestionInvalid() {
        findUserValid();
        EditText qField = testAct.findViewById(R.id.question_entry);
        qField.setText("Jeremy"); //Grab answer field an set to an invalid value
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Submit the form

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.question_entry)).isFocused());
        assertEquals("Error content", "That answer is not correct", ((TextView) (testAct.findViewById(R.id.question_entry))).getError()); //Check the error has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - findUserMaxTries
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the question form displays the error when maximum attempts reached
     */
    @Test
    public void answerQuestionMaxTries() {
        findUserValid();
        EditText qField = testAct.findViewById(R.id.question_entry);
        qField.setText("Jeremy");
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Have 3 incorrect guesses
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Since We call findUserValid in the answerQuestionInvalid test - we can't call it to reuse that code
        (testAct.findViewById(R.id.question_button)).callOnClick(); //Instead we do it manually

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" );
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id);

        assertEquals("Alert title is correct", "Max Attempts Reached", alertTitle.getText());
    }
}