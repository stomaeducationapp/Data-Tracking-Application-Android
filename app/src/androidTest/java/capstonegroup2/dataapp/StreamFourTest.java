package capstonegroup2.dataapp;

import android.support.test.rule.ControlledActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import capstonegroup2.dataapp.accountCreation.AccountCreation;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 12/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 14/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the integration test for Stream Four - which is the connection of all entry GUIs (Login, Account Creation and Password Recovery) and the mechanisms that allow
 * them to be connected (Observers and XML)
 */

/* VERSION HISTORY
 * 12/10/2018 - Created file
 * 13/12/2018 - Figured out how to make espresso work
 * 14/12/2018 - Completed testing
 */

/* REFERENCES
 * Espresso basics learned from https://developer.android.com/training/testing/espresso/setup and https://developer.android.com/training/testing/espresso/basics
 * How to test for alert dialog boxes in espresso learned from https://stackoverflow.com/questions/21045509/check-if-a-dialog-is-displayed-with-espresso
 * How to test for activity switches learned from https://stackoverflow.com/questions/25998659/espresso-how-can-i-check-if-an-activity-is-launched-after-performing-a-certain
 * How to test EditText errors learned from https://stackoverflow.com/questions/39206599/testing-edittext-errors-with-espresso-on-android
 * How to resolve a perform click exception learned from https://stackoverflow.com/questions/29786413/performexception-error-performing-single-click
 * How to find if a string is within a dialog learned from https://stackoverflow.com/questions/43934662/espresso-textview-contains-string
 * How to clear text in a EditText learned from https://stackoverflow.com/questions/23780857/updating-an-edittext-with-espresso
 * How to scroll in espresso learned from https://stackoverflow.com/questions/30387255/espresso-how-to-scroll-to-the-bottom-of-scrollview
 * How to handle intent recording learned from https://stackoverflow.com/questions/37869418/espresso-intent-test-failing
 * And many more from https://developer.android.com/
 *
 * NOTE
 * This test was done using the files in test/java/Integration/StreamFour which was then imported directly onto the emulated device (use the file paths in the respective classes)
 */
@RunWith(AndroidJUnit4.class)
public class StreamFourTest {

    @Rule
    public ControlledActivityTestRule<Login> actRule = new ControlledActivityTestRule<>(Login.class);

    /* FUNCTION INFORMATION
     * NAME - acSwitch
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that performs the switch to the AccountCreation activity
     */
    private void acSwitch() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)

        //Switch to activity
        onView(withId(R.id.loginSplash)).perform(click());
        onView(withId(R.id.loginRegisterButt)).perform(click());

    }

    /* FUNCTION INFORMATION
     * NAME - acSwitchTest
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the switching works as intended (means activities are connected)
     */
    @Test
    public void acSwitchTest()
    {
        acSwitch(); //Perform the switch

        //Check we got to the right one
        intended(hasComponent(AccountCreation.class.getName()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - acSucces
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a successful account creation works
     */
    @Test
    public void acSuccess() {
        acSwitch(); //Make sure we are on the AccountCreation screen

        //Enter all the required text
        onView(withId(R.id.acUNInput)).perform(typeText("Alice"));
        onView(withId(R.id.acUNInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acPassInput)).perform(typeText("1amDAbest!"));
        onView(withId(R.id.acPassInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acSQAnswer)).perform(typeText("Alice"));
        onView(withId(R.id.acSQAnswer)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it

        onView(withId(R.id.acGameSett)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Mode 2"))).perform(click());
        onView(withId(R.id.acSQs)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Who was your first grade teacher?"))).perform(click());
        onView(withId(R.id.acExportSettTwo)).perform(click());

        //Submit and check alert
        onView(withId(R.id.acCreateButt)).perform(scrollTo(), click());
        onView(withText(containsString("File write success"))).check(matches(isDisplayed()));
        //File check should be done in device explorer before emulator close to check actual data written remains correct

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - acError
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks if a validation works in this activity
     */
    @Test
    public void acError() {
        acSwitch(); //Make sure we are on the AccountCreation screen

        //Enter all the required text (so no missing entry errors appear)
        onView(withId(R.id.acUNInput)).perform(typeText("@lice"));
        onView(withId(R.id.acUNInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acPassInput)).perform(typeText("1amDAbest!"));
        onView(withId(R.id.acPassInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acSQAnswer)).perform(typeText("Mrs Krabapple"));
        onView(withId(R.id.acSQAnswer)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it

        onView(withId(R.id.acGameSett)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Mode 2"))).perform(click());
        onView(withId(R.id.acSQs)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Who was your first grade teacher?"))).perform(click());
        onView(withId(R.id.acExportSettTwo)).perform(click());

        //Submit and check error
        onView(withId(R.id.acCreateButt)).perform(scrollTo(), click());
        onView(withId(R.id.acUNInput)).check(matches(hasErrorText("No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.")));

        actRule.finish();

    }

    /* FUNCTION INFORMATION
     * NAME - acFail
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks when a username that is already present is used a failure occurs
     */
    @Test
    public void acFail() {
        acSwitch(); //Make sure we are on the AccountCreation screen

        //Enter all the required text (so no missing entry errors appear)
        onView(withId(R.id.acUNInput)).perform(typeText("Bob"));
        onView(withId(R.id.acUNInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acPassInput)).perform(typeText("1amDAbest!"));
        onView(withId(R.id.acPassInput)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.acSQAnswer)).perform(typeText("Mrs Krabapple"));
        onView(withId(R.id.acSQAnswer)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it

        onView(withId(R.id.acGameSett)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Mode 2"))).perform(click());
        onView(withId(R.id.acSQs)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Who was your first grade teacher?"))).perform(click());
        onView(withId(R.id.acExportSettTwo)).perform(click());

        //Submit and check error
        onView(withId(R.id.acCreateButt)).perform(scrollTo(), click());
        onView(withId(R.id.acUNInput)).check(matches(hasErrorText("Username already taken.")));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - prSwitch
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that performs the activity switch to the PasswordRecovery class
     */
    private void prSwitch() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)

        //Switch to activity
        onView(withId(R.id.loginSplash)).perform(click());
        onView(withId(R.id.loginRecoverButt)).perform(click());

    }

    /* FUNCTION INFORMATION
     * NAME - prSwitchTest
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the switching works as intended (means activities are connected)
     */
    @Test
    public void prSwitchTest()
    {
        prSwitch(); //Perform the switch

        //Check we got to the right one
        intended(hasComponent(PasswordRecovery.class.getName()));
        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - prSuccess
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a password recovery can be done (that file reading works)
     */
    @Test
    public void prSuccess() {
        prSwitch(); //Make sure we are on the PasswordRecovery screen

        //Enter all the required text
        onView(withId(R.id.pr_user_entry)).perform(typeText("Bob"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());

        onView(withId(R.id.pr_question_entry)).perform(typeText("Alice"));
        onView(withId(R.id.pr_question_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_question_button)).perform(click());

        //Check alert
        onView(withText("Form Submission Results")).check(matches(isDisplayed()));
        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - prFail
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks if file reading works to detect wrong answers/non-existent usernames
     */
    @Test
    public void prFail() {
        prSwitch(); //Make sure we are on the PasswordRecovery screen

        //Enter all the required text
        onView(withId(R.id.pr_user_entry)).perform(typeText("Greg"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());
        onView(withId(R.id.pr_user_entry)).check(matches(hasErrorText("No user found with that name.")));

        //Enter the correct text (to test next field)
        onView(withId(R.id.pr_user_entry)).perform(clearText());
        onView(withId(R.id.pr_user_entry)).perform(typeText("Bob"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());

        //Get error on next field
        onView(withId(R.id.pr_question_entry)).perform(typeText("Hello"));
        onView(withId(R.id.pr_question_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_question_button)).perform(click());
        onView(withId(R.id.pr_question_entry)).check(matches(hasErrorText("That answer is not correct")));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - prError
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks if a validation works in this activity
     */
    @Test
    public void prError() {
        prSwitch(); //Make sure we are on the PasswordRecovery screen

        //Enter all the required text
        onView(withId(R.id.pr_user_entry)).perform(typeText("@lice"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());

        //Check alert
        onView(withId(R.id.pr_user_entry)).check(matches(hasErrorText("No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.")));
        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - prMaxTries
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that the max tries feature now closes activities
     */
    @Test
    public void prMaxTries() {
        prSwitch(); //Make sure we are on the PasswordRecovery screen

        //Enter all the required text
        onView(withId(R.id.pr_user_entry)).perform(typeText("Greg"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());
        onView(withId(R.id.pr_user_button)).perform(click());
        onView(withId(R.id.pr_user_button)).perform(click());

        //Check alert
        onView(withText("Max Attempts Reached")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click());
        intended(hasComponent(Login.class.getName())); //Check the dialog cancelled the activity

        prSwitch(); //Make sure we are on the PasswordRecovery screen

        //Enter all the required text
        onView(withText(R.id.pr_user_entry)).perform(clearText());
        onView(withId(R.id.pr_user_entry)).perform(typeText("Bob"));
        onView(withId(R.id.pr_user_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_user_button)).perform(click());
        onView(withId(R.id.pr_question_entry)).perform(typeText("Greg"));
        onView(withId(R.id.pr_question_entry)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.pr_question_button)).perform(click());
        onView(withId(R.id.pr_question_button)).perform(click());
        onView(withId(R.id.pr_question_button)).perform(click());

        //Check alert
        onView(withText("Max Attempts Reached")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click());
        intended(hasComponent(Login.class.getName())); //Check the dialog cancelled the activity

        actRule.finish();

    }

    /* FUNCTION INFORMATION
     * NAME - loginSuccess
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that a successful login works (with file reading)
     */
    @Test
    public void loginSuccess() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)
        onView(withId(R.id.loginSplash)).perform(click()); //Get to login screen

        //Enter all the required text
        onView(withId(R.id.loginUnameBox)).perform(typeText("Bob"));
        onView(withId(R.id.loginUnameBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginPassBox)).perform(typeText("Alice15d&best"));
        onView(withId(R.id.loginPassBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginButt)).perform(click());

        //Check alert
        onView(withText(containsString("Success on login: Yes"))).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - loginFail
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that bad logins work as intended
     */
    @Test
    public void loginFail() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)
        onView(withId(R.id.loginSplash)).perform(click()); //Get to login screen

        //Enter all the required text
        onView(withId(R.id.loginUnameBox)).perform(typeText("Bob"));
        onView(withId(R.id.loginUnameBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginPassBox)).perform(typeText("Hello"));
        onView(withId(R.id.loginPassBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginButt)).perform(click());

        //Check alert
        onView(withText(containsString("Success on login: No"))).check(matches(isDisplayed()));
        onView(withId(android.R.id.button2)).perform(click()); //Close dialog

        //Enter all the required text
        onView(withId(R.id.loginUnameBox)).perform(clearText());
        onView(withId(R.id.loginUnameBox)).perform(typeText("Greg"));
        onView(withId(R.id.loginUnameBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginButt)).perform(click());

        //Check alert
        onView(withId(R.id.loginUnameBox)).check(matches(hasErrorText("Username or password incorrect")));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - loginError
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks if a validation works in this activity
     */
    @Test
    public void loginError() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)
        onView(withId(R.id.loginSplash)).perform(click()); //Get to login screen

        //Enter all the required text
        onView(withId(R.id.loginUnameBox)).perform(typeText("@lice"));
        onView(withId(R.id.loginUnameBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginPassBox)).perform(typeText("1amDAbest!"));
        onView(withId(R.id.loginPassBox)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.loginButt)).perform(click());

        //Check alert
        onView(withId(R.id.loginUnameBox)).check(matches(hasErrorText("No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.")));

        actRule.finish();
    }

}
