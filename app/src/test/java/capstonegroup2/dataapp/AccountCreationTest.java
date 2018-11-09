package capstonegroup2.dataapp;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowAlertDialog;

import capstonegroup2.dataapp.accountCreation.AccountCreation;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 25/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 25/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the unit test for the Account Creation UI
 */

/* VERSION HISTORY
 * 25/10/2018 - Created file and completed testing
 */

/* REFERENCES
 * Removing focus from an object learned from https://stackoverflow.com/questions/5056734/android-force-edittext-to-remove-focus
 * Generating a touch event programmatically learned from https://stackoverflow.com/questions/28498079/how-automatically-perform-touch-in-android-programmatically/28498553
 * And many more from https://developer.android.com/
 */

@RunWith(RobolectricTestRunner.class)
public class AccountCreationTest
{

    private Activity testAct; //The activity we will be testing
    private Button createButt; //This is the only button that is reused almost every test to we will locate it on creation/recreation to reuse code

    /* FUNCTION INFORMATION
     * NAME - setUp
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that creates the initial activity object we will run our tests on (we will re uses it a few times)
     */
    @Before
    public void setUp()
    {
        testAct = Robolectric.setupActivity(AccountCreation.class);
        createButt = testAct.findViewById(R.id.acCreateButt);
    }

    /* FUNCTION INFORMATION
     * NAME - blank
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that rebuilds the activity (to zero the attempts feature for different test cases)
     */
    private void blank()
    {
        testAct.recreate(); //Resets the activity
        createButt = testAct.findViewById(R.id.acCreateButt); //Refind to prevent any weirdness
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
     * NAME - emptyUser
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity errors when no username is supplied
     */
    @Test
    public void emptyUser()
    {
        //Since empty user is the same as an empty form
        createButt.callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.acUNInput)).isFocused());
        assertEquals("Error content", "Please provide a username", ((TextView) (testAct.findViewById(R.id.acUNInput))).getError()); //Check the error has loaded
    }

    /* FUNCTION INFORMATION
     * NAME - emptyPass
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity errors when no password is supplied
     */
    @Test
    public void emptyPass()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        createButt.callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.acPassInput)).isFocused());
        assertEquals("Error content", "Please provide a password", ((TextView) (testAct.findViewById(R.id.acPassInput))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - passFocusIn
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity displays a password guide when password text box clicked
     */
    @Test
    public void passFocusIn()
    {

        (testAct.findViewById(R.id.acPassInput)).dispatchTouchEvent(MotionEvent.obtain(0,0,MotionEvent.ACTION_DOWN, 100,100,0.5f,5,0,1,1,0,0)); //Touch the password box

        assertEquals("Pass guide hidden", View.VISIBLE, (testAct.findViewById(R.id.acPassGuide).getVisibility())); //Check that the guide has appeared

    }

    /* FUNCTION INFORMATION
     * NAME - passWeak
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity errors when a weak password is supplied and that the strength indicator is the correct response for a weak password
     */
    @Test
    public void passWeak()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("hello");

        assertEquals("Response is correct", "Strength: WEAK", ((TextView)(testAct.findViewById(R.id.acPassStrength))).getText());

        createButt.callOnClick(); //Now since this has a particular error state we want to check that as well

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.acPassInput)).isFocused());
        assertEquals("Error content", "Please provide a password with a strength of at least \"good\"", ((TextView) (testAct.findViewById(R.id.acPassInput))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - passGood
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the that the strength indicator is the correct response for good passwords
     */
    @Test
    public void passGood()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("passswordHell6");

        assertEquals("Response is correct", "Strength: GOOD", ((TextView)(testAct.findViewById(R.id.acPassStrength))).getText());

    }

    /* FUNCTION INFORMATION
     * NAME - passExcellent
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the that the strength indicator is the correct response for excellent passwords
     */
    @Test
    public void passExcellent()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("passswordHello697");

        assertEquals("Response is correct", "Strength: EXCELLENT", ((TextView)(testAct.findViewById(R.id.acPassStrength))).getText());

    }

    /* FUNCTION INFORMATION
     * NAME - emptyAnswer
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity errors when no answer is supplied
     */
    @Test
    public void emptyAnswer()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("passwordHello6977");
        createButt.callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.acSQAnswer)).isFocused());
        assertEquals("Error content", "Please provide an answer to security question", ((TextView) (testAct.findViewById(R.id.acSQAnswer))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - emptyExport
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity errors when no export button is supplied
     */
    @Test
    public void emptyExport()
    {

        blank();
        //Now we just enter some text into username
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("passswordHello697");
        EditText ansField = testAct.findViewById(R.id.acSQAnswer);
        ansField.setText("Alice");
        createButt.callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.acExportTitle)).isFocused());
        assertEquals("Error content", "Please select an export type", ((TextView) (testAct.findViewById(R.id.acExportTitle))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - formValid
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity responds when the form is filled in correctly
     */
    @Test
    public void formValid()
    {

        blank();
        //Now the required (empty by default) fields
        EditText userField = testAct.findViewById(R.id.acUNInput);
        userField.setText("Bob");
        EditText passField = testAct.findViewById(R.id.acPassInput);
        passField.setText("passwordHello697");
        EditText ansField = testAct.findViewById(R.id.acSQAnswer);
        ansField.setText("Alice");
        RadioButton export = testAct.findViewById(R.id.acExportSettThree); //We will choose the none option
        export.setChecked(true);
        export.callOnClick();

        createButt.callOnClick();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Find the id of the alert title
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //Grab the TextView containing the title

        assertEquals("Alert title is correct", "Form Submission Results", alertTitle.getText());

    }

}
