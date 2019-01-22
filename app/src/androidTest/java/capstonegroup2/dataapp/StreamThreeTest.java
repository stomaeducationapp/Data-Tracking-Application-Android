package capstonegroup2.dataapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ControlledActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import capstonegroup2.dataapp.Challenges.ChallengeGUI;

import static android.support.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 22/01/2019
 * LAST MODIFIED BY - Jeremy Dunnet 22/01/2019
 */

/* CLASS/FILE DESCRIPTION
 * This is the integration test for Stream Three - which is the connection of the Daily Review classes and GUI with the mechanisms that
 * allow them to be connected (Observers and XML) to the main app thread
 */

/* VERSION HISTORY
 * 22/01/2019 - Created file and completed testing
 */

/* REFERENCES
 * Espresso basics learned from https://developer.android.com/training/testing/espresso/setup and https://developer.android.com/training/testing/espresso/basics
 * And many more from https://developer.android.com/
 *
 * NOTE
 * This test was done using the files in test/java/Integration/StreamThree which was then imported directly onto the emulator device (use the file paths in the respective classes)
 * This test seems very simple in its test cases but that is because the reviews mechanisms are almost all behind the scenes (test in ReviewExtraTest) and this just confirms the UI displays what the background handles
 */
@RunWith(AndroidJUnit4.class)
public class StreamThreeTest {

    @Rule
    public ControlledActivityTestRule<TestActivity> actRule = new ControlledActivityTestRule<>(TestActivity.class);

    /* FUNCTION INFORMATION
     * NAME - cSwitchTest
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the setup method to switch from the test activity (since a true connected activity is not finished)
     */
    @Before
    public void drSetup()
    {
        //Switch to activity
        onView(withId(R.id.testButt)).perform(click());

    }

    /* FUNCTION INFORMATION
     * NAME - graphTestCaseOne
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a state based graph is displayed in both today/yesterday slots
     */
    @Test
    public void graphTestCaseOne()
    {

        onView(withId(R.id.graphSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("State Graph"))).perform(click());

        onView(withId(R.id.todayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

        onView(withId(R.id.yesterdayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

    }

    /* FUNCTION INFORMATION
     * NAME - graphTestCaseTwo
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks another state based graph is displayed in both today/yesterday slots
     */
    @Test
    public void graphTestCaseTwo()
    {

        onView(withId(R.id.graphSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("State Pie Chart"))).perform(click());

        onView(withId(R.id.todayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

        onView(withId(R.id.yesterdayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

    }

    /* FUNCTION INFORMATION
     * NAME - graphTestCaseThree
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a volume based graph is displayed in both today/yesterday slots
     */
    @Test
    public void graphTestCaseThree()
    {

        onView(withId(R.id.graphSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Volume Graph"))).perform(click());

        onView(withId(R.id.todayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

        onView(withId(R.id.yesterdayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

    }

    /* FUNCTION INFORMATION
     * NAME - graphTestCaseFour
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a output based graph is displayed in both today/yesterday slots
     */
    @Test
    public void graphTestCaseFour()
    {

        onView(withId(R.id.graphSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Output Graph"))).perform(click());

        onView(withId(R.id.todayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

        onView(withId(R.id.yesterdayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

    }

    /* FUNCTION INFORMATION
     * NAME - graphTestCaseFive
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the test that checks a wellbeing based graph is displayed in both today/yesterday slots
     */
    @Test
    public void graphTestCaseFive()
    {

        onView(withId(R.id.graphSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Wellbeing Pie Chart"))).perform(click());

        onView(withId(R.id.todayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

        onView(withId(R.id.yesterdayButton)).perform(click());
        onView(withId(R.id.chartView)).check(matches(isDisplayed()));

    }

}
