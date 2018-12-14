package android.support.test.rule;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 14/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 14/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is a custom rule to allow resetting of activities in espresso instrumentation testing
 */

/* VERSION HISTORY
 * 14/12/2018 - Created file from below resource
 */

/* REFERENCES
 * All code adapted/borrowed from https://stackoverflow.com/questions/35306423/destroy-and-restart-activity-with-testing-support-library
 */

public class ControlledActivityTestRule<T extends Activity> extends ActivityTestRule<T> {
    public ControlledActivityTestRule(Class<T> activityClass) {
        super(activityClass, false);
    }

    public ControlledActivityTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode, true);
    }

    public ControlledActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    public void finish() {
        finishActivity();
        Intents.release();
    }

    public void relaunchActivity() {
        finishActivity();
        launchActivity();
    }

    public void launchActivity() {
        Intents.init();
        launchActivity(getActivityIntent());
    }
}
