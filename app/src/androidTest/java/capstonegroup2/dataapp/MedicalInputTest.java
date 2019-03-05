package capstonegroup2.dataapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ControlledActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 12/02/2019
 * LAST MODIFIED BY - Jeremy Dunnet 12/02/2019
 */

/* CLASS/FILE DESCRIPTION
 * This is the unit test for Medical Data Input - testing all created functionality works in isolation (BagFragment included since it is a directly connected class)
 */

/* VERSION HISTORY
 * 12/02/2019 - Created file and completed testing
 */

/* REFERENCES
 * Espresso basics learned from https://developer.android.com/training/testing/espresso/setup and https://developer.android.com/training/testing/espresso/basics
 * How to test for Activity switches learned from https://stackoverflow.com/questions/25998659/espresso-how-can-i-check-if-an-activity-is-launched-after-performing-a-certain
 * How to handle Intent recording learned from https://stackoverflow.com/questions/37869418/espresso-intent-test-failing
 * How to check for Fragment loading learned from https://stackoverflow.com/questions/36380046/espresso-2-2-1-check-if-fragment-layout-is-displayed
 * How to perform a click on an item within a RecyclerView learned from https://gist.github.com/quentin7b/9c5669fd940865cf2e89
 * How to check an item deleted from a listView learned from https://stackoverflow.com/questions/39222374/test-deletion-in-listview-with-espresso
 * And many more from https://developer.android.com/
 *
 */

@RunWith(AndroidJUnit4.class)
public class MedicalInputTest {

    @Rule
    public ControlledActivityTestRule<TestActivity> actRule = new ControlledActivityTestRule<>(TestActivity.class);

    /* FUNCTION INFORMATION
     * NAME - mSwitch
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that performs the switch to the MedicalInput activity
     */
    private void mSwitch() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)

        //Switch to activity
        onView(withId(R.id.testButt)).perform(click());

    }

    /* FUNCTION INFORMATION
     * NAME - mSwitchTest
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the switching works as intended (means activities are connected)
     */
    @Test
    public void mSwitchTest()
    {
        mSwitch(); //Perform the switch

        //Check we got to the right one
        intended(hasComponent(MedicalInput.class.getName()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - mLoad
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the activity loads the form
     */
    @Test
    public void mLoad() {
        mSwitch(); //Make sure we are on the MedicalInput screen

        onView(withId(R.id.bagList)).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - mSuccess
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a successful medical input works
     */
    @Test
    public void mSuccess() {
        mSwitch(); //Make sure we are on the MedicalInput screen

        //Enter all the required text
        onView(withId(R.id.urine_input)).perform(scrollTo(), typeText("23"));
        onView(withId(R.id.urine_input)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it

        onView(withId(R.id.bag_fragment)).perform(scrollTo(), click());
        onView(withId(R.id.bagAmount)).perform(scrollTo(), typeText("400"));
        onView(withId(R.id.bagAmount)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.consisOptOne)).perform(scrollTo(), click());
        onView(withId(R.id.submitBag)).perform(scrollTo(), click());

        onView(withId(R.id.urineOptThree)).perform(scrollTo(), click());
        onView(withId(R.id.wellBOptThree)).perform(scrollTo(), click());

        //Submit and check alert
        onView(withId(R.id.submit_main)).perform(scrollTo(), click());
        onView(withText(containsString("Submission results"))).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - mErrorX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that checks if a validation works in this activity
     */
    @Test
    public void mErrorOne() {
        mSwitch(); //Make sure we are on the MedicalInput screen

        onView(withId(R.id.bag_fragment)).perform(scrollTo(), click());
        onView(withId(R.id.bagAmount)).perform(scrollTo(), typeText("400"));
        onView(withId(R.id.bagAmount)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.consisOptOne)).perform(scrollTo(), click());
        onView(withId(R.id.submitBag)).perform(scrollTo(), click());

        onView(withId(R.id.urineOptThree)).perform(scrollTo(), click());
        onView(withId(R.id.wellBOptThree)).perform(scrollTo(), click());

        //Submit and check error
        onView(withId(R.id.submit_main)).perform(scrollTo(), click());
        onView(withId(R.id.urine_input)).check(matches(hasErrorText("Please provide an average urine output")));

        actRule.finish();

    }

    /* FUNCTION INFORMATION
     * NAME - mErrorX
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that checks if a validation works in this activity
     */
    @Test
    public void mErrorTwo() {
        mSwitch(); //Make sure we are on the MedicalInput screen

        onView(withId(R.id.bag_fragment)).perform(scrollTo(), click());
        onView(withId(R.id.consisOptOne)).perform(scrollTo(), click());

        //Submit and check error
        onView(withId(R.id.submitBag)).perform(scrollTo(), click());
        onView(withId(R.id.bagAmount)).check(matches(hasErrorText("Please provide a bag amount emptied")));

        actRule.finish();

    }

    /* FUNCTION INFORMATION
     * NAME - cFragComplete
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a bag list entry can be deleted
     */
    @Test
    public void mDeleteBag() {
        mSwitch(); //Make sure we are on the Challenges screen

        onView(withId(R.id.bag_fragment)).perform(scrollTo(), click());
        onView(withId(R.id.bagAmount)).perform(scrollTo(), typeText("400"));
        onView(withId(R.id.bagAmount)).perform(closeSoftKeyboard()); //Close keyboard to stop espresso not being able to find the next clickable view because keyboard is blocking it
        onView(withId(R.id.consisOptOne)).perform(scrollTo(), click());
        onView(withId(R.id.submitBag)).perform(scrollTo(), click());

        //Grab the first challenge card and click it's delete entry button button
        onView(withId(R.id.bagList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.delete_entry_button)));
        onView(withText(containsString("I'm sure"))).perform(click());
        onView(withId(R.id.bagList)).check(matches(not(hasDescendant(withId(R.id.delete_entry_button))))); //Check the entry was deleted

        actRule.finish();
    }

}

