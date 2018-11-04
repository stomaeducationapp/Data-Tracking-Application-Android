package capstonegroup2.dataapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 04/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 04/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles generating, displaying and completing challenges that encourage the user to participate in various healthy tasks to either earn gamification rewards
 * or satisfaction.
 */

/* VERSION HISTORY
 * 04/11/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * How to yse fragments learned from https://developer.android.com/guide/components/fragments
 * And many more from https://developer.android.com/
 */

public class Challenges extends AppCompatActivity {

    //Classfields
    private int gameMode; //Simple int to tailor how some challenges are displayed/handled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        ActionBar bar = getActionBar();
        bar.setTitle(R.string.c_title);

        //Retrieve gamificiation mode and set it
        //Call getNewChallenges

    }

    /* FUNCTION INFORMATION
     * NAME - getNewChallenges
     * INPUTS - numChallenges (amount of new challenges to retrieve)
     * OUTPUTS - none
     * PURPOSE - This is the function that generates and displays the new challenges to display on activity creation
     * NOTE - This is built as a separate method to enable future builds to replace old challenges with new ones (simply pass in number of new challenges you need)
     */
    private void getNewChallenges(int numChallenges)
    {

        //Generate five random numbers (all unique)
        //Retrieve the challenges associated with those IDS (reward value, description, title and type)
        //Store all challenge info so we can call it up again to use in creating description fragments
        //for loop (to numChallenges) that creates a new recyclerView node that represents a challenge add adds it to recyclerVew on screen
            //Don't add rewards if not in mode 1

    }

    /* FUNCTION INFORMATION
     * NAME - markAsComplete
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that marks a challenge (that calls this function as a onclick listener) as complete and rewards user depending on game mode
     */
    public void markAsComplete(View view)
    {
        //Set challenge to green colour
        //Get reward (if in mode 1) and add it to gamification file
        //TODO LEAVE THIS HERE FOR POSSIBLE REMOVAL OF CHALLENGE AFTER COMPLETION
    }

    /* FUNCTION INFORMATION
     * NAME - viewDescription
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that calls up the fragment each challenge own to display the full description of a challenge (to better understand what it entails)
     */
    public void viewDescription(View view)
    {
        //On click - get ID of challenge
        //Create fragment with title, type, description, reward and complete/cancel buttons
        //Change focus to that fragment
    }
}
