package capstonegroup2.dataapp.Challenges;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import capstonegroup2.dataapp.R;

import static android.graphics.Color.GREEN;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 06/11/2018
 * LAST MODIFIED BY - Jeremy Dunnet 06/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is a GUI class use to represent the full description of a challenge as a fragment on top of the Challenges GUI
 */

/* VERSION HISTORY
 * 06/11/2018 - Created class
 */

/* REFERENCES
 * Idea sourced from https://examples.javacodegeeks.com/android/core/app/fragment/android-fragments-example/ and team member Patrick Crockford's implementation of fragments (Account_Information_Main.java and Account_Information_Name_Fragment.java)
 * How to close a fragment properly learned from https://stackoverflow.com/questions/20812922/how-to-close-the-current-fragment-by-using-button-like-the-back-button
 * How to pass data between fragment and activity learned from https://stackoverflow.com/questions/9343241/passing-data-between-a-fragment-and-its-container-activity
 * And many more from https://developer.android.com/
 */

public class Challenge_Fragment extends Fragment
{
    //Classfields
    private String chalTitle;
    private String chalType;
    private String chalDes;
    private String chalReward;
    private int gameMode;
    private String chalComp;
    private ChallengeCompleteListener cListener;
    //Constants
    public final static String ARG_TITLE = "ChalTitle";
    public final static String ARG_TYPE = "ChalType";
    public final static String ARG_DES = "ChalDes";
    public final static String ARG_REWARD = "ChalReward";
    public final static String ARG_MODE = "ChalMode";
    public final static String ARG_COMP = "ChalComp";

    public Challenge_Fragment()
    {
        //Constructor must be blank
    }

    /* FUNCTION INFORMATION
     * NAME - newInstance
     * INPUTS - c (Challenge to collect data from), gameMode (to determine rewards)
     * OUTPUTS - Challenge_Fragment (instance of this class)
     * PURPOSE - This is the function that generates a new instance and stores all data we need so that it can be loaded on fragment load
     */
    public static Challenge_Fragment newInstance(Challenge c, int gameMode) {
        Challenge_Fragment fragment = new Challenge_Fragment();

        Bundle args = new Bundle();
        //Now chuck in all the required information the fragment will need
        args.putString(ARG_TITLE, c.getChallengeTitle());
        args.putString(ARG_TYPE, c.getChallengeType());
        args.putString(ARG_DES, c.getDes());
        args.putString(ARG_REWARD, c.getRewardValue());
        args.putString(ARG_MODE, ("" + gameMode));
        if(c.isComplete())
        {
            args.putString(ARG_COMP, ("TRUE"));
        }
        else
        {
            args.putString(ARG_COMP, ("FALSE"));
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.challenge_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Grab all needed values
        chalTitle = getArguments().getString(ARG_TITLE);
        chalType = getArguments().getString(ARG_TYPE);
        chalDes = getArguments().getString(ARG_DES);
        chalReward = getArguments().getString(ARG_REWARD);
        gameMode = Integer.valueOf( (getArguments().getString(ARG_MODE)) );
        chalComp = getArguments().getString(ARG_COMP);

        //Locate layout objects we need and set them to the new values
        TextView title = view.findViewById(R.id.chalFragTitle);
        title.setText(chalTitle);

        TextView type = view.findViewById(R.id.chalFragType);
        type.setText(chalType);

        TextView des = view.findViewById(R.id.chalFragDes);
        des.setText(chalDes);

        TextView reward = view.findViewById(R.id.chalFragRew);
        reward.setText(chalReward);

        //Set onClick listeners
        final TextView cancelButt = view.findViewById(R.id.chalFragCancel); //We make this final so the next listener can use it
        cancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity current = getActivity();
                FragmentManager fm = current.getFragmentManager();
                cListener.hideFragment(); //Make sure the layout doesn't obscure activity data
                fm.popBackStackImmediate(); //Close fragment

            }
        });

        if(chalComp.equals("FALSE")) //If challenge has not been completed - allow it to be completed
        {
            TextView comButt = view.findViewById(R.id.chalFragComplete);
            comButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View challenge = (View)(v.getParent());
                    challenge.setBackgroundColor(GREEN); //Mark the challenge as complete

                    cListener.completedChallenge(chalTitle); //Tell the activity that the challenge is complete
                    //Since in the above method the reward is allocated we do not need to worry about handling rewards here

                    cancelButt.callOnClick(); //Close window - assume that since the challenge is complete user doesn't need to look at it anymore
                    //FEEL FREE TO CHANGE
                }
            });
        }
        else
        {
            view.setBackgroundColor(GREEN); //Mark the challenge as complete
            TextView comp = view.findViewById(R.id.chalFragComplete);
            comp.setTextColor(getResources().getColor(R.color.grey)); //Change colour to indicate has been disabled
            comp.setClickable(false);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChallengeCompleteListener) {
            cListener = (ChallengeCompleteListener) context; //Attach listener
        } else {
            throw new RuntimeException(context.toString() + " must implement ChallengeCompleteListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cListener = null;
    }

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 06/11/2018
     * LAST MODIFIED BY - Jeremy Dunnet 06/11/2018
     */

    /* CLASS/FILE DESCRIPTION
     * This is an interface to allow this fragment to pass data back to it's parent activity
     */

    /* VERSION HISTORY
     * 06/11/2018 - Created class
     */

    /* REFERENCES
     * Idea sourced from https://examples.javacodegeeks.com/android/core/app/fragment/android-fragments-example/ and team member Patrick Crockford's implementation of fragments (Account_Information_Main.java and Account_Information_Name_Fragment.java)
     * And many more from https://developer.android.com/
     */
    public interface ChallengeCompleteListener
    {
        //Used to tell activity a challenge has been completed (so they can mark it as complete as well
        void completedChallenge(String title);
        //Makes sure the fragment area is hidden before closing (so it doesn't block activity content)
        void hideFragment();
    }

}

