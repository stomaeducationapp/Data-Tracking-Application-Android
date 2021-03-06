package capstonegroup2.dataapp.Challenges;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Factory.Factory;
import Observers.Time_Observer;
import XML.Account_Reader;
import XML.Account_Writer;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;
import capstonegroup2.dataapp.R;

import static android.graphics.Color.GREEN;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 04/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 06/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles generating, displaying and completing challenges that encourage the user to participate in various healthy tasks to either earn gamification rewards
 * or satisfaction.
 */

/* VERSION HISTORY
 * 04/11/2018 - Created file and added comment design path for future coding
 * 05/11/2018 - Implemented base functionality (minus fragment)
 */

/* REFERENCES
 * How to use fragments learned from https://developer.android.com/guide/components/fragments
 * CardView+RecyclerView tutorial from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * Getting an array from string resources learned from https://stackoverflow.com/questions/10532907/android-retrieve-string-array-from-resources
 * Adding space to a layout learned from https://stackoverflow.com/questions/6352140/adding-blank-spaces-to-layout
 * Getting resource ID's using strings learned from https://stackoverflow.com/questions/7493287/android-how-do-i-get-string-from-resources-using-its-name
 * Generating random numbers learned from https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range
 * How to change a views background color learned from https://stackoverflow.com/questions/23517879/set-background-color-programmatically
 * How to obtain parent view from a view learned from https://stackoverflow.com/questions/17879743/get-parents-view-from-a-layout
 * Setting background colour of element from layout file learned from https://stackoverflow.com/questions/7378636/setting-background-colour-of-android-layout-element
 * Performing string comparison of a TextView learned from https://stackoverflow.com/questions/13073307/compare-text-in-edittext-with-defined-string
 * Passing data between fragment and activity learned from https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
 * Creating a fragment learned from https://examples.javacodegeeks.com/android/core/app/fragment/android-fragments-example/ and team member Patrick Crockford's implementation of fragments (Account_Information_Main.java and Account_Information_Name_Fragment.java)
 * Changing Text colour programmatically learned from https://stackoverflow.com/questions/8472349/how-to-set-text-color-to-a-text-view-programmatically
 * How to disable onClick learned from https://stackoverflow.com/questions/5195321/remove-an-onclick-listener
 * And many more from https://developer.android.com/
 */

public class ChallengeGUI extends AppCompatActivity implements Challenge_Fragment.ChallengeCompleteListener {

    //Classfields
    private int gameMode; //Simple int to tailor how some challenges are displayed/handled
    private RecyclerView list;
    private ChallengeAdapter adapter;
    private List<Challenge> challengeList;
    private ConstraintLayout fragmentArea;
    private Factory f;
    private File accountFile;
    private int userPoints; //Point value the user currently has earned
    //Constants
    public final int numStartChallenges = 5; //Number of challenges to generate on creation
    public final int MAX = 15; //Number of challenges that currently exist on file

    //TODO WORK ON THIS!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        //Find the layout areas we need
        list = findViewById(R.id.challenges);
        fragmentArea = findViewById(R.id.challenge_des);

        //Hide the fragment area initially
        fragmentArea.setVisibility(View.INVISIBLE);

        //Create out list of challenges
        challengeList = new ArrayList<>();
        adapter = new ChallengeAdapter(this, challengeList);

        //Set up the recyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        list.addItemDecoration(new VerticalSpaceItemDecoration(10)); //Add spacing between items for looks
        list.setAdapter(adapter);

        //Get our information from the file we received
        Bundle bundle = this.getIntent().getExtras();
        HashMap<Time_Observer.Files, File> files = (HashMap<Time_Observer.Files, File>) bundle.getSerializable("fileMap");
        accountFile = files.get(Time_Observer.Files.Account);

        f =  Factory.Get_Factory();

        Account_Reader ar = (Account_Reader) f.Make_Reader(Factory.XML_Reader_Choice.Account);
        List<XML_Reader.Tags_To_Read> readList = new ArrayList<>(Arrays.asList(XML_Reader.Tags_To_Read.Gamification, XML_Reader.Tags_To_Read.Points));
        Map<String, String> userInfo = new HashMap<String, String>();

        try{
            userInfo = ar.Read_File(accountFile, readList, "Bob"); //TODO HARDCODED UNTIL INTEGRATION OF MAIN MENU - WHERE USERNAME PASSING WILL BE SOLVED
        }
        catch(XML_Reader_Exception e)
        {
            throw new RuntimeException ("FIX THIS" + e.getMessage());
        }

        String mode = userInfo.get("Gamification"); //Get mode from what was read in

        if(mode.equals("Mode 1"))
        {
            gameMode = 1;
            userPoints = Integer.valueOf(userInfo.get("Points")); //Record the points of the user since we will need it later
        }
        else
        {
            gameMode = 2; //Two is only other mode that could make it here (since mode 3 disables challenge button to load this activity)
        }

        getNewChallenges(numStartChallenges, gameMode);

    }

    /* FUNCTION INFORMATION
     * NAME - getNewChallenges
     * INPUTS - numChallenges (amount of new challenges to retrieve), mode (what gamification mode we are in)
     * OUTPUTS - none
     * PURPOSE - This is the function that generates and displays the new challenges to display on activity creation
     * NOTE - This is built as a separate method to enable future builds to replace old challenges with new ones (simply pass in number of new challenges you need)
     */
    private void getNewChallenges(int numChallenges, int mode)
    {
        int[] numbers = new int[numChallenges];
        for(int ii = 0; ii < numChallenges; ii++) //So the array can be as long as the challenges we need
        {
            numbers[ii] = 0;
        }

        Random gen = new Random();

        for(int ii = 0; ii < numChallenges; ii++)
        {
            int challengeNumber;

            challengeNumber = gen.nextInt(MAX) + 1; //Generate a number between 1 and MAX
            while(alreadyGenerated(numbers, challengeNumber) == true) //Until we get a new unique random number
            {
                challengeNumber = gen.nextInt(MAX + 1) + 1; //Generate a number between 1 and MAX (+1 to be inclusive)
            }
            numbers[ii] = challengeNumber;

            String packageName = getPackageName(); //Find the challenge stored in the resources
            int resId = getResources().getIdentifier(("challenge" + challengeNumber), "array", packageName);

            String[] challengeInfo = getResources().getStringArray(resId);
            String reward = "";

            if(mode == 1) //If we are in full gamification mode we need to record the point reward
            {
                reward = challengeInfo[2];
            }//Any other has no reward listed since they do not use that feature

            Challenge c = new Challenge(mode, challengeInfo[0], challengeInfo[1], reward, challengeInfo[3], challengeInfo[4]); //Create a challenge object and store in the list
            challengeList.add(c);

        }

        adapter.notifyDataSetChanged(); //Tell recycler view to update the screen

    }

    /* FUNCTION INFORMATION
     * NAME - alreadyGenerated
     * INPUTS - numbers (array of previously generated values), newNum (new number to test against)
     * OUTPUTS - boolean
     * PURPOSE - This is the function that tests a new number against all previously generated values and returns a boolean to indicate if need to generate a new one
     */
    private boolean alreadyGenerated(int[] numbers, int newNum)
    {

        boolean duplicate = false;

        for(int ii = 0; ii < numbers.length; ii++)
        {
            if(numbers[ii] == newNum)
            {
                duplicate = true;
            }
        }

        return duplicate;

    }

    /* FUNCTION INFORMATION
     * NAME - markAsComplete
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that marks a challenge as complete and rewards user depending on game mode
     */
    public void markAsComplete(View view)
    {
        //Set challenge to green colour
        View card = (View)((view.getParent()).getParent()); //Find the challenge card View
        card.setBackgroundColor(GREEN); //Colour it green
        String title = ((TextView)(card.findViewById(R.id.chalTitle))).getText().toString();

        for(int ii = 0; ii < challengeList.size(); ii++) {
            Challenge challenge = challengeList.get(ii);
            if ( title.equals(challenge.getChallengeTitle()) )
            {
                challenge.setComplete(true); //Find the challenge object and mark it as complete so future viewDes calls will continue to show it as completed
            }
        }

        if(gameMode == 1)
        {
            //Update the points of the user
            TextView reward = card.findViewById(R.id.chalRewValue);
            userPoints = userPoints + (Integer.valueOf( (reward.getText()).toString() ));

            //Write newly created account to file
            boolean success;
            Account_Writer ac = (Account_Writer) f.Make_Writer(Factory.XML_Writer_Choice.Account);
            XML_Writer.Tags_To_Write job = XML_Writer.Tags_To_Write.Modify;
            Map<String, String> values = new HashMap<String, String>();

            values.put("Points", (userPoints + ""));

            try{
                success = ac.Write_File(accountFile, values, job); //Create the account in the system
            }
            catch (XML_Writer_File_Layout_Exception | XML_Writer_Failure_Exception e)
            {
                throw new RuntimeException("FIX THIS" + e.getMessage());
            }
        }

        //Disable challenge complete button
        TextView comp = card.findViewById(R.id.chalComplete);
        comp.setTextColor(getResources().getColor(R.color.grey)); //Change colour to indicate has been disabled
        comp.setOnClickListener(null); //Remove ability to complete again
        comp.setClickable(false);

        //TODO LEAVE THIS HERE FOR POSSIBLE REMOVAL OF CHALLENGE AFTER COMPLETION
        //Currently a challenge that is completed is not cleared and replaced with a new one (give sense of daily completion)
        //If you wish to rotate this may require a rewrite of some methods of the class to accommodate (since this feature was low priority so only a demo/low functionality version was designed)
        //You will need to add a flag to getNewChallenges to alert the method that a challenge list was previously generated (and would need to check all listed challenges for duplicates)
        //and the system would need to record the position of the completed challenge so that the method can replace that position in the list with the new challenge
    }

    /* FUNCTION INFORMATION
     * NAME - viewDescription
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that calls up the fragment each challenge own to display the full description of a challenge (to better understand what it entails)
     */
    public void viewDescription(View view)
    {
        Challenge c = null;

        View challengeView = (View) view.getParent(); //Find the challenge card
        TextView vTitle = challengeView.findViewById(R.id.chalTitle); //Get the title
        for(int ii = 0; ii < challengeList.size(); ii++) {
            Challenge challenge = challengeList.get(ii);
            if ( ((vTitle.getText()).toString()).equals(challenge.getChallengeTitle()) )
            {
                c = challenge; //Find the challenge object in our list associated with the title
            }
        }

        Challenge_Fragment fragment = Challenge_Fragment.newInstance(c, gameMode); //Create a fragment to display the challenge
        fragmentArea.setVisibility(View.VISIBLE);

        //Could move to Form_Change if desired but since this is a container within an activity - not seen to be needed
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.challenge_des, fragment);
        fragmentTransaction.addToBackStack("challenge_fragment"); //Display the fragment
        fragmentTransaction.commit();

    }

    /* FUNCTION INFORMATION
     * NAME - completedChallenge
     * INPUTS - title (name of challenge completed)
     * OUTPUTS - none
     * PURPOSE - This is the function that allows us to mark the card on the activity screen as well so that challenges a user completes in a fragment stay marked
     */
    @Override
    public void completedChallenge(String title)
    {

        for(int jj = 0; jj < list.getChildCount(); jj++)
        {
            View v = list.getChildAt(jj);
            TextView vTitle = v.findViewById(R.id.chalTitle);

            if( ((vTitle.getText().toString())).equals(title) ) //Find the view that matches our title
            {
                TextView comButt = v.findViewById(R.id.chalComplete);
                comButt.callOnClick();
            }
        }

    }

    /* FUNCTION INFORMATION
     * NAME - hideFragment
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that hides the fragmentArea so the rest of the activity is still visible
     */
    @Override
    public void hideFragment()
    {
        fragmentArea.setVisibility(View.INVISIBLE);
    }

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 05/11/2018
     * LAST MODIFIED BY - Jeremy Dunnet 05/11/2018
     */

    /* CLASS/FILE DESCRIPTION
     * This is a private class we will use to contain all cards that are generated by the GUI so be displayed in the list
     */

    /* VERSION HISTORY
     * 05/11/2018 - Created file and added first draft code
     */

    /* REFERENCES
     * Idea sourced from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
     * Make a TextView a button learned from https://stackoverflow.com/questions/6105860/how-to-use-a-text-as-a-button-in-android
     * Changing an ImageView learned from https://stackoverflow.com/questions/16906528/change-image-of-imageview-programmatically-in-android
     * And many more from https://developer.android.com/
     */
    private class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.MyViewHolder>
    {

        private Context mContext;
        private List<Challenge> challengeList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title, rewardTitle, rewardVal, desButt, completeButt;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);
                title = (TextView) view.findViewById(R.id.chalTitle);
                rewardTitle = (TextView) view.findViewById(R.id.chalRewTitle);
                rewardVal = (TextView) view.findViewById(R.id.chalRewValue);
                desButt = (TextView) view.findViewById(R.id.chalView);
                completeButt = (TextView) view.findViewById(R.id.chalComplete);
                thumbnail = (ImageView) view.findViewById(R.id.chalPic);
            }
        }


        public ChallengeAdapter(Context mContext, List<Challenge> challengeList) {
            this.mContext = mContext;
            this.challengeList = challengeList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.challenge, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Challenge challenge = challengeList.get(position);
            holder.title.setText(challenge.getChallengeTitle()); //Set title text

            if(challenge.getMode() == 1) //If we need to display a reward - else hide it
            {
                holder.rewardVal.setText(challenge.getRewardValue());
            }
            else
            {
                holder.rewardTitle.setVisibility(View.INVISIBLE);
                holder.rewardVal.setVisibility(View.INVISIBLE);
            }

            String packageName = getPackageName(); //Get the image and set it to the card
            int resId = getResources().getIdentifier(challenge.getImageName(), "drawable", packageName);
            holder.thumbnail.setImageResource(resId);
        }

        @Override
        public int getItemCount() {
            return challengeList.size();
        }
    }

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 05/11/2018
     * LAST MODIFIED BY - Jeremy Dunnet 05/11/2018
     */

    /* CLASS/FILE DESCRIPTION
     * This is a private class we will use to add spacing to elements in the recyclerView list
     */

    /* VERSION HISTORY
     * 05/11/2018 - Created file and added first draft code
     */

    /* REFERENCES
     * The inspiration and code for this private class was adapted from https://stackoverflow.com/questions/24618829/how-to-add-dividers-and-spaces-between-items-in-recyclerview
     * And many more from https://developer.android.com/
     */
    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = verticalSpaceHeight; //Set the recycler views height to our constructed value
            }
        }
    }

}
