package capstonegroup2.dataapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ControlledActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import capstonegroup2.dataapp.Challenges.ChallengeGUI;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 17/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 17/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the integration test for Stream Five - which is the connection of all GUIs related to challenge functionality (ChallengesGUI and Challenge_Fragment)
 * and the mechanisms that allow them to be connected (Observers and XML)
 */

/* VERSION HISTORY
 * 17/10/2018 - Created file and completed testing
 */

/* REFERENCES
 * Espresso basics learned from https://developer.android.com/training/testing/espresso/setup and https://developer.android.com/training/testing/espresso/basics
 * How to test for Activity switches learned from https://stackoverflow.com/questions/25998659/espresso-how-can-i-check-if-an-activity-is-launched-after-performing-a-certain
 * How to handle Intent recording learned from https://stackoverflow.com/questions/37869418/espresso-intent-test-failing
 * How to check for Fragment loading learned from https://stackoverflow.com/questions/36380046/espresso-2-2-1-check-if-fragment-layout-is-displayed
 * How to check if a Button isn't clickable learned from https://stackoverflow.com/questions/36536523/determine-if-a-button-isnt-clickable-with-espresso
 * How to perform a click on an item within a RecyclerView learned from https://gist.github.com/quentin7b/9c5669fd940865cf2e89
 * And many more from https://developer.android.com/
 *
 * NOTE
 * This test was done using the files in test/java/Integration/StreamFour which was then imported directly onto the emulator device (use the file paths in the respective classes)
 */
@RunWith(AndroidJUnit4.class)
public class StreamFiveTest {

    @Rule
    public ControlledActivityTestRule<TestActivity> actRule = new ControlledActivityTestRule<>(TestActivity.class);

    /* FUNCTION INFORMATION
     * NAME - cSwitch
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that performs the switch to the ChallengesGUI activity
     */
    private void cSwitch() {
        actRule.relaunchActivity(); //Reset app back to login page so test follows correct logic (not in a different activity)

        //Switch to activity
        onView(withId(R.id.testButt)).perform(click());

    }

    /* FUNCTION INFORMATION
     * NAME - cSwitchTest
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the switching works as intended (means activities are connected)
     */
    @Test
    public void cSwitchTest()
    {
        cSwitch(); //Perform the switch

        //Check we got to the right one
        intended(hasComponent(ChallengeGUI.class.getName()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - cLoad
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks the activity loads 5 challenges
     */
    @Test
    public void cLoad() {
        cSwitch(); //Make sure we are on the Challenges screen

        onView(withId(R.id.challenges)).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - cViewDes
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks you can view a challenges description, the fragment loads and can be cancelled
     */
    @Test
    public void cViewDes() {
        cSwitch(); //Make sure we are on the Challenges screen

        //Grab the first challenge card and click it's view description button
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalView)));
        onView(withId(R.id.ChallengeFragment)).check(matches(isDisplayed()));

        //Now click the cancel button and see if it disappears
        onView(withId(R.id.chalFragCancel)).perform(click());
        onView(withId(R.id.challenges)).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - cFragComplete
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a challenge fragment completion works as intended
     */
    @Test
    public void cFragComplete() {
        cSwitch(); //Make sure we are on the Challenges screen

        //Grab the first challenge card and click it's view description button
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalView)));
        onView(withId(R.id.ChallengeFragment)).check(matches(isDisplayed()));

        //Now click complete
        onView(withId(R.id.chalFragComplete)).perform(click());
        onView(withId(R.id.challenges)).check(matches(isDisplayed()));

        //Reload fragment and check completion loads here
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalView)));
        onView(withId(R.id.ChallengeFragment)).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - cCardComplete
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a completed challenge done from the card view works as intended
     */
    @Test
    public void cCardComplete() {
        cSwitch(); //Make sure we are on the Challenges screen

        //Grab the first challenge card and click it's view description button
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalComplete)));

        //Reload fragment and check completion loads here
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalView)));
        onView(withId(R.id.ChallengeFragment)).check(matches(isDisplayed()));

        actRule.finish();
    }

    /* FUNCTION INFORMATION
     * NAME - acSucces
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks you cannot recomplete a challenge
     */
    @Test
    public void cRecomplete() {
        cSwitch(); //Make sure we are on the Challenges screen

        //Grab the first challenge card and click it's view description button
        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalComplete)));

        //Now test each recomplete
        onView(allOf(withId(R.id.chalComplete), withText("Complete"), ChildPosition.childAtPosition(ChildPosition.childAtPosition(withClassName(is("android.support.v7.widget.CardView")), 0), 5), not(isClickable())));

        onView(withId(R.id.challenges)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.chalView)));
        onView(withId(R.id.chalFragComplete)).check(matches(not(isClickable())));

        actRule.finish();
    }

}
