package capstonegroup2.dataapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 12/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 13/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the integration test for Stream Four - which is the connection of all entry GUIs (Login, Account Creation and Password Recovery) and the mechanisms that allow
 * them to be connected (Observers and XML)
 */

/* VERSION HISTORY
 * 12/10/2018 - Created file
 * 13/12/2018 - Figured out how to make espresso work
 */

/* REFERENCES
 * Robolectric basic tutorial learned from http://robolectric.org/ and https://android.jlelse.eu/how-to-write-android-unit-tests-using-robolectric-27341d530613
 * How to test for alert dialog boxes in robolectric learned from https://stackoverflow.com/questions/47655855/how-to-make-robolectric-test-for-alertdialog
 * How to get titles from alert boxes learned from https://stackoverflow.com/questions/29161994/how-to-get-the-alertdialog-title/29162440#29162440
 * Difference for test focus methods learned from https://stackoverflow.com/questions/33022310/what-is-the-difference-between-hasfocus-and-isfocused-in-android
 * Re-instancing an activity learned from https://stackoverflow.com/questions/2486934/programmatically-relaunch-recreate-an-activity
 * And many more from https://developer.android.com/
 */
@RunWith(AndroidJUnit4.class)
public class StreamFourTest {

    @Rule
    public ActivityTestRule<Login> mActivityRule =
            new ActivityTestRule<>(Login.class);

    @Test
    public void checkSplashScreen() {
        onView(withText("Tap to start")).check(matches(isDisplayed()));
    }

}
