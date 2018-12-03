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
 * CREATOR - Jeremy Dunnet 3/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 3/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the unit test for the Login UI
 */

/* VERSION HISTORY
 * 3/10/2018 - Created file and completed testing
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
public class LoginTest {

    private Activity testAct; //The activity we will be testing

    /* FUNCTION INFORMATION
     * NAME - setUp
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that creates the initial activity object we will run our tests on (we will re use it a few times)
     */
    @Before
    public void setUp() {
        testAct = Robolectric.setupActivity(Login.class);
    }

    /* FUNCTION INFORMATION
     * NAME - blank
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that rebuilds the activity (to zero the attempts feature for different test cases)
     */
    private void blank() {
        testAct.recreate(); //Resets the activity
    }


    /* FUNCTION INFORMATION
     * NAME - setupNotNull
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests if the activity we built in setup is not null (tests for successful activity onCreate functionality)
     */
    @Test
    public void setupNotNull() {
        assertNotNull(testAct);
    }

    /* FUNCTION INFORMATION
     * NAME - layoutLoad
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the layouts of both screen (splash and login) display correctly on load
     */
    @Test
    public void layoutLoad() {

        blank();

        assertEquals("Login Screen hidden", View.INVISIBLE, (testAct.findViewById(R.id.loginLayout).getVisibility())); //Check that the form has disappeared and the next one has appeared
        assertEquals("Splash Screen visible", View.VISIBLE, (testAct.findViewById(R.id.splashLayout).getVisibility())); //Indicates functionality worked
        assertEquals("Splash text loaded", "Tap to start", ((TextView) (testAct.findViewById(R.id.loginSplash))).getText()); //Check the right text loaded

    }

    /* FUNCTION INFORMATION
     * NAME - layoutClick
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the screen swap when the splash screen clicked
     */
    @Test
    public void layoutClick() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick();

        assertEquals("Login Screen visible", View.VISIBLE, (testAct.findViewById(R.id.loginLayout).getVisibility())); //Check that the form has disappeared and the next one has appeared
        assertEquals("Splash Screen invisible", View.INVISIBLE, (testAct.findViewById(R.id.splashLayout).getVisibility())); //Indicates functionality worked

    }

    /* FUNCTION INFORMATION
     * NAME - noLoginEntry
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests what the login form does when nothing is entered in the fields and the user clicks login
     */
    @Test
    public void noLoginEntry() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        EditText userField = testAct.findViewById(R.id.loginUnameBox);
        EditText passField = testAct.findViewById(R.id.loginPassBox);

        (testAct.findViewById(R.id.loginButt)).callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.loginUnameBox)).isFocused());
        assertEquals("Error content", "Please provide a username", ((TextView) (testAct.findViewById(R.id.loginUnameBox))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - someLoginEntry
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests what the login form does when only a username is provided and the user clicks login
     */
    @Test
    public void someLoginEntry() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        EditText userField = testAct.findViewById(R.id.loginUnameBox);
        userField.setText("Bob"); //Grab user field an set to a valid value
        EditText passField = testAct.findViewById(R.id.loginPassBox);

        (testAct.findViewById(R.id.loginButt)).callOnClick();

        assertEquals("Error displayed", true, (testAct.findViewById(R.id.loginPassBox)).isFocused());
        assertEquals("Error content", "Please provide a password", ((TextView) (testAct.findViewById(R.id.loginPassBox))).getError()); //Check the error has loaded

    }

    /* FUNCTION INFORMATION
     * NAME - fullLoginEntry
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests what the login form does when a full entry is given
     */
    @Test
    public void fullLoginEntry() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        EditText userField = testAct.findViewById(R.id.loginUnameBox);
        userField.setText("Bob"); //Grab user field an set to a valid value
        EditText passField = testAct.findViewById(R.id.loginPassBox);
        passField.setText("Alice"); //Grab user field an set to a valid value

        (testAct.findViewById(R.id.loginButt)).callOnClick();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Since the correct answer yields an alert detailing all info sent
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //We check for the alert title (not the next screen)

        assertEquals("Alert title is correct", "Login Results", alertTitle.getText());

    }

    /* FUNCTION INFORMATION
     * NAME - clickRegister
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity attempting to switch to another Activity that is directly linked to this one
     */
    @Test
    public void clickRegister() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        (testAct.findViewById(R.id.loginRegisterButt)).callOnClick();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Since the correct answer yields an alert detailing all info sent
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //We check for the alert title (not the next screen)

        assertEquals("Alert title is correct", "Missing Transition - AC", alertTitle.getText());

    }

    /* FUNCTION INFORMATION
     * NAME - clickRecover
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity attempting to switch to another Activity that is directly linked to this one
     */
    @Test
    public void clickRecover() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        (testAct.findViewById(R.id.loginRecoverButt)).callOnClick();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Since the correct answer yields an alert detailing all info sent
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //We check for the alert title (not the next screen)

        assertEquals("Alert title is correct", "Missing Transition - PR", alertTitle.getText());

    }

    /* FUNCTION INFORMATION
     * NAME - clickSettings
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests the activity attempting to switch to another Activity that is directly linked to this one
     */
    @Test
    public void clickSettings() {

        blank();
        TextView splashField = testAct.findViewById(R.id.loginSplash);
        splashField.callOnClick(); //Switch screens to the login layout

        (testAct.findViewById(R.id.loginSettingsButt)).callOnClick();

        int id = testAct.getResources().getIdentifier( "alertTitle", "id", "android" ); //Since the correct answer yields an alert detailing all info sent
        TextView alertTitle = (ShadowAlertDialog.getLatestAlertDialog()).findViewById(id); //We check for the alert title (not the next screen)

        assertEquals("Alert title is correct", "Unimplemented feature", alertTitle.getText());

    }
}
