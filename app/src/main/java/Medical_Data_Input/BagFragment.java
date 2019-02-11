package Medical_Data_Input;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;

import capstonegroup2.dataapp.R;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 08/02/2019
 * LAST MODIFIED BY - Jeremy Dunnet 08/02/2019
 */

/* CLASS/FILE DESCRIPTION
 * This is a fragment used to allow a user to add new bags in a contained menu to avoid confusion
 */

/* VERSION HISTORY
 * 08/02/2019 - Created class
 */

/* REFERENCES
 * Idea sourced from https://examples.javacodegeeks.com/android/core/app/fragment/android-fragments-example/ and team member Patrick Crockford's implementation of fragments (Account_Information_Main.java and Account_Information_Name_Fragment.java)
 * And many more from https://developer.android.com/
 */

public class BagFragment extends Fragment {

    private ArrayList<Bag> bags;
    private BagAdapter bagAdapter;
    private BagAddedListener bListener;

    private EditText bagInput;
    private TextView consisText;
    private TextView timeText;
    private RadioButton bagRbOne;
    private RadioButton bagRbTwo;
    private RadioButton bagRbThree;
    private TimePicker hourPick;
    private DatePicker datePick;

    private String consisVal;

    public static final String ARG_BAGS = "BAG_LIST";
    public static final String ARG_ADPT = "BAG_ADAPTER";

    public BagFragment()
    {
        //Constructor must be blank
    }

    /* FUNCTION INFORMATION
     * NAME - newInstance
     * INPUTS - c (Challenge to collect data from), gameMode (to determine rewards)
     * OUTPUTS - Challenge_Fragment (instance of this class)
     * PURPOSE - This is the function that generates a new instance and stores all data we need so that it can be loaded on fragment load
     */
    public static BagFragment newInstance(ArrayList<Bag> bList, BagAdapter bAdapter) {
        BagFragment fragment = new BagFragment();

        Bundle args = new Bundle();
        //Now chuck in all the required information the fragment will need
        args.putSerializable(ARG_BAGS, bList);
        args.putSerializable(ARG_ADPT, bAdapter);
        //TODO ADD A VALIDATOR HERE

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bag_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Grab all needed values
        bags = (ArrayList<Bag>) getArguments().getSerializable(ARG_BAGS);
        bagAdapter = (BagAdapter) getArguments().getSerializable(ARG_ADPT);

        bagInput = view.findViewById(R.id.bagAmount);
        consisText = view.findViewById(R.id.consisText);
        bagRbOne = view.findViewById(R.id.consisOptOne);
        bagRbTwo = view.findViewById(R.id.consisOptTwo);
        bagRbThree = view.findViewById(R.id.consisOptThree);
        timeText = view.findViewById(R.id.timeText);
        hourPick = view.findViewById(R.id.bagTime);
        datePick = view.findViewById(R.id.bagDate);

        bagRbOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked(); //Find out if the button was checked

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.consisOptOne:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptTwo:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptThree:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    default:
                        break;

                }
            }
        });
        bagRbTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked(); //Find out if the button was checked

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.consisOptOne:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptTwo:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptThree:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    default:
                        break;

                }
            }
        });
        bagRbThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((RadioButton) v).isChecked(); //Find out if the button was checked

                // Check which radio button was clicked
                switch (v.getId()) {
                    case R.id.consisOptOne:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptTwo:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    case R.id.consisOptThree:
                        if (checked) {
                            consisVal = (String) (((RadioButton) v).getText()); //Set the value to the text of the button
                        }
                        break;
                    default:
                        break;

                }
            }
        });

        //Set onClick listeners
        final Button cancelButt = view.findViewById(R.id.bagFragCancelButt); //We make this final so the next listener can use it
        cancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity current = getActivity();
                FragmentManager fm = current.getFragmentManager();
                bListener.hideFragment(); //Make sure the layout doesn't obscure activity data
                fm.popBackStackImmediate(); //Close fragment

            }
        });

        Button addButt = view.findViewById(R.id.submitBag);
        addButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Grab all info and add new bag

                String bagAmount = (bagInput.getText()).toString();
                int min = hourPick.getCurrentMinute();
                int hour = hourPick.getCurrentHour();
                int day = datePick.getDayOfMonth();
                int month = datePick.getMonth();
                int year = datePick.getYear();

                //TODO DO VALIDATION CHECKS HERE

                //Do a rolling check for all values set
                if(bagAmount.equals(""))
                {
                    bagInput.requestFocus();
                    bagInput.setError("Please provide a bag amount emptied");
                }
                else
                {
                    if(consisVal == null)
                    {
                        consisText.requestFocus();
                        consisText.setError("Please provide a consistency");
                    }
                    else
                    {

                        //THESE LAST TWO IFS ARE PROBABLY NOT NEEDED - BUT HERE JUST IN CASE
                        if((min < 0) || (hour < 0))
                        {
                            timeText.requestFocus();
                            timeText.setError("Please provide a correct date");
                        }
                        else
                        {
                            if((day < 0) || (month < 0) || (year < 2018))
                            {
                                timeText.requestFocus();
                                timeText.setError("Please provide a correct date");
                            }
                            else
                            {
                                String date = "" + hour + "-" + min + "-" + day + "-" + month + "-" + year;

                                Bag b = new Bag(Integer.parseInt(bagAmount), consisVal, date);

                                bags.add(b);
                                bagAdapter.notifyItemInserted(bags.size());

                                cancelButt.callOnClick(); //Close window - allows user to see list updated with new bag before adding another one (if they need to)
                            }
                        }

                    }
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BagAddedListener) {
            bListener = (BagAddedListener) context; //Attach listener
        } else {
            throw new RuntimeException(context.toString() + " must implement BagAdddedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bListener = null;
    }

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 08/02/2019
     * LAST MODIFIED BY - Jeremy Dunnet 08/02/2019
     */

    /* CLASS/FILE DESCRIPTION
     * This is an interface to allow this fragment to pass data back to it's parent activity
     */

    /* VERSION HISTORY
     * 08/02/2019 - Created class
     */

    /* REFERENCES
     * Idea sourced from https://examples.javacodegeeks.com/android/core/app/fragment/android-fragments-example/ and team member Patrick Crockford's implementation of fragments (Account_Information_Main.java and Account_Information_Name_Fragment.java)
     * And many more from https://developer.android.com/
     */
    public interface BagAddedListener
    {
        //Makes sure the fragment area is hidden before closing (so it doesn't block activity content)
        void hideFragment();
    }
}
