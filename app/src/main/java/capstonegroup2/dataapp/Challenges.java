package capstonegroup2.dataapp;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.graphics.Color.GREEN;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 04/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 05/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles generating, displaying and completing challenges that encourage the user to participate in various healthy tasks to either earn gamification rewards
 * or satisfaction.
 */

/* VERSION HISTORY
 * 04/11/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * How to use fragments learned from https://developer.android.com/guide/components/fragments
 * CardView+RecyclerView tutorial from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * Getting an array from string resources learned from https://stackoverflow.com/questions/10532907/android-retrieve-string-array-from-resources
 * Adding space to a layout learned from https://stackoverflow.com/questions/6352140/adding-blank-spaces-to-layout
 * Getting resource ID's using strings learned from https://stackoverflow.com/questions/7493287/android-how-do-i-get-string-from-resources-using-its-name
 * Generating random numbers learned from https://stackoverflow.com/questions/21049747/how-can-i-generate-a-random-number-in-a-certain-range
 * How to change a views background color learned from https://stackoverflow.com/questions/23517879/set-background-color-programmatically
 * And many more from https://developer.android.com/
 */

public class Challenges extends AppCompatActivity {

    //Classfields
    private int gameMode; //Simple int to tailor how some challenges are displayed/handled
    private RecyclerView list;
    private ChallengeAdapter adapter;
    private List<Challenge> challengeList;
    //Constants
    public final int numStartChallenges = 5; //Number of challenges to generate on creation
    public final int MAX = 15; //Number of challenges that currently exist on file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);

        list = findViewById(R.id.challenges);

        challengeList = new ArrayList<>();
        adapter = new ChallengeAdapter(this, challengeList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);
        list.addItemDecoration(new VerticalSpaceItemDecoration(10));
        list.setAdapter(adapter);

        //TODO GET GAMIFICATION MODE FROM FILE AND SET IT TO GAMEMODE

        getNewChallenges(numStartChallenges, 1);

    }

    /* FUNCTION INFORMATION
     * NAME - getNewChallenges
     * INPUTS - numChallenges (amount of new challenges to retrieve)
     * OUTPUTS - none
     * PURPOSE - This is the function that generates and displays the new challenges to display on activity creation
     * NOTE - This is built as a separate method to enable future builds to replace old challenges with new ones (simply pass in number of new challenges you need)
     */
    private void getNewChallenges(int numChallenges, int mode)
    {
        int[] numbers = {0, 0, 0, 0, 0};
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

            String packageName = getPackageName();
            int resId = getResources().getIdentifier(("challenge" + challengeNumber), "array", packageName);

            String[] challengeInfo = getResources().getStringArray(resId);
            Challenge c = new Challenge(mode, challengeInfo[0], challengeInfo[1], challengeInfo[2], challengeInfo[3], challengeInfo[4]);
            challengeList.add(c);

        }

        adapter.notifyDataSetChanged();

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
     * PURPOSE - This is the function that marks a challenge (that calls this function as a onclick listener) as complete and rewards user depending on game mode
     */
    public void markAsComplete(View view)
    {
        //Set challenge to green colour
        View card = (View)((view.getParent()).getParent());
        card.setBackgroundColor(GREEN);

        //Get reward (if in mode 1) and add it to gratification file
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

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 05/11/2018
     * LAST MODIFIED BY - Jeremy Dunnet 05/11/2018
     */

    /* CLASS/FILE DESCRIPTION
     * This is a private class we will use to contain all relevant information to a challenge so we can access certain info easily
     */

    /* VERSION HISTORY
     * 05/11/2018 - Created file and added first draft code
     */

    /* REFERENCES
     * Idea sourced from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
     * And many more from https://developer.android.com/
     */
    private class Challenge
    {

        //Classfields
        private int mode;
        private String challengeTitle;
        private String challengeType;
        private String rewardValue;
        private String description;
        private String imageName;

        public Challenge(int gameMode, String title, String type, String value, String des, String image)
        {
            mode = gameMode;
            challengeTitle = title;
            challengeType = type;
            rewardValue = value;
            description = des;
            imageName = image;

        }

        public int getMode() {
            return mode;
        }

        public String getChallengeTitle() {
            return challengeTitle;
        }

        public String getChallengeType() {
            return challengeType;
        }

        public String getRewardValue() {
            return rewardValue;
        }

        public String getDes() {
            return description;
        }
        public String getImageName() {
            return imageName;
        }
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
                desButt = (TextView) findViewById(R.id.chalView);
                completeButt = (TextView) findViewById(R.id.chalComplete);
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
            holder.title.setText(challenge.getChallengeTitle());

            if(challenge.getMode() == 1)
            {
                holder.rewardVal.setText(challenge.getRewardValue());
            }
            else
            {
                holder.rewardTitle.setVisibility(View.INVISIBLE);
                holder.rewardVal.setVisibility(View.INVISIBLE);
            }

            /*String packageName = getPackageName();
            int resId = getResources().getIdentifier(challenge.getImageName(), "drawable", packageName);
            holder.thumbnail.setImageResource(resId);*/
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
                outRect.bottom = verticalSpaceHeight;
            }
        }
    }

}
