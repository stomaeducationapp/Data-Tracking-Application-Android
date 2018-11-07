package capstonegroup2.dataapp;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import capstonegroup2.dataapp.Challenges.Challenge;
import capstonegroup2.dataapp.Challenges.ChallengeGUI;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 06/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 06/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the unit test for the Challenges GUI and related classes
 */

/* VERSION HISTORY
 * 22/10/2018 - Created file and completed testing
 */

/* REFERENCES
 * Robolectric basic tutorial learned from http://robolectric.org/ and https://android.jlelse.eu/how-to-write-android-unit-tests-using-robolectric-27341d530613
 * Difference for test focus methods learned from https://stackoverflow.com/questions/33022310/what-is-the-difference-between-hasfocus-and-isfocused-in-android
 * Re-instancing an activity learned from https://stackoverflow.com/questions/2486934/programmatically-relaunch-recreate-an-activity
 * Getting background color of a view learned from https://stackoverflow.com/questions/14779259/get-background-color-of-a-layout
 * And many more from https://developer.android.com/
 */
@RunWith(RobolectricTestRunner.class)
public class ChallengesTest
{

    private Activity testAct; //The activity we will be testing

    /* FUNCTION INFORMATION
     * NAME - setUp
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that creates the initial activity object we will run our tests on (we will re uses it a few times
     */
    @Before
    public void setUp() {
        testAct = Robolectric.setupActivity(ChallengeGUI.class);
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
     * NAME - challengeValid
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests a challenge object is setup and returns the right fields
     */
    @Test
    public void challengeValid() {
        Challenge c = new Challenge(0, "TITLE", "TYPE", "VALUE", "DESCRIPTION", "IMAGE");

        assertNotNull("Challenge was created", c);

        assertEquals("Game mode correct", 0, c.getMode());
        assertEquals("Title correct", "TITLE", c.getChallengeTitle());
        assertEquals("Type correct", "TYPE", c.getChallengeType());
        assertEquals("Value correct", "VALUE", c.getRewardValue());
        assertEquals("Description correct", "DESCRIPTION", c.getDes());
        assertEquals("Image correct", "IMAGE", c.getImageName());
    }

    /* FUNCTION INFORMATION
     * NAME - challengeLoad
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests that five challenges load with fields entered
     */
    @Test
    public void challengeLoad() {
        blank();

        RecyclerView challenges = testAct.findViewById(R.id.challenges);

        assertNotNull("Challenge list appears", challenges);

        for(int ii = 0; ii < challenges.getChildCount(); ii++)
        {
            View v = challenges.getChildAt(ii);
            TextView title = v.findViewById(R.id.chalTitle);

            assertNotNull("Title exists", (title.getText()).toString() ); //If one loaded the rest will
        }

    }

    /* FUNCTION INFORMATION
     * NAME - challengeComplete
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests a challenge can be completed and changes
     */
    @Test
    public void challengeComplete() {
        blank();

        TextView butt = testAct.findViewById(R.id.chalComplete); //Just grab the first button the method finds
        butt.callOnClick();

        View card = (View) (butt.getParent().getParent());
        Drawable  background =  card.getBackground();
        int colour = Color.BLUE;
        if (background instanceof ColorDrawable)
        {
            colour = ((ColorDrawable) background).getColor(); //Get the background color
        }

        assertEquals("Challenge marked as complete", Color.GREEN, colour);
    }

    /* FUNCTION INFORMATION
     * NAME - fragmentLoad
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that tests a challenge fragment is loaded
     */
    @Test
    public void fragmentLoad() {
        blank();

        TextView butt = testAct.findViewById(R.id.chalView); //Just grab the first button the method finds
        butt.callOnClick();
        View v =  (View) butt.getParent().getParent();

        ConstraintLayout c = testAct.findViewById(R.id.challenge_des);

        assertEquals("Fragment layout visible", View.VISIBLE, c.getVisibility());

        Fragment f = ((testAct.getFragmentManager()).getFragments()).get(1);

        assertNotNull("Challenge fragment loaded", f );

    }

}