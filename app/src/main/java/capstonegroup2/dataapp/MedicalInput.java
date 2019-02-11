package capstonegroup2.dataapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Factory.Factory;
import Medical_Data_Input.Bag;
import Medical_Data_Input.BagAdapter;
import Medical_Data_Input.BagFragment;
import Medical_Data_Input.StomaForm;
import Observers.State_Observer;

public class MedicalInput extends Activity implements BagAdapter.ItemDeleteInterface, BagFragment.BagAddedListener
{

    private Factory factory;
    private StomaForm stomaForm;
    private State_Observer state_observer;

    private RecyclerView recyclerView;
    private ScrollView scrollLayout;
    private ConstraintLayout fragLayout;
    private ArrayList<Bag> bagList;
    private BagAdapter bAdapter;

    private String uColourVal;
    private String wellBVal;

    //TODO CHECK ALL BUTTON ALLOCATIONS AND MAKE SURE SUBMIT COLLECTS ALL INFO
    //TODO ADD BUNDLE EXTRACTION OF MEDICAL FILE FOR USE IN SAVING DATA

    /**
     * @param savedInstanceState is the state of the app when opened, allowing to set the layout view
     *                           *On Create declares all buttons used in the view
     *                           *Once initialized they can be used for input and saved into their classes
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_input);

        recyclerView = findViewById(R.id.bagList);
        scrollLayout = findViewById(R.id.medScroll);
        fragLayout = findViewById(R.id.bagFraglayout);

        bagList = new ArrayList<Bag>();
        stomaForm = new StomaForm(); //TODO CHANGE TO FACTORY CALL

        uColourVal = null; //Allow us to check they were selected when submission comes
        wellBVal = null;

        //Set up recycler view
        bAdapter = new BagAdapter(bagList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(bAdapter);

    }

    /* FUNCTION INFORMATION
     * NAME - addBag
     * INPUTS - view
     * OUTPUTS - none
     * PURPOSE - This is the function that calls up the fragment to add a new bag the user has emptied
     */
    public void addBag(View view)
    {

        BagFragment fragment = BagFragment.newInstance(bagList, bAdapter /*TODO ADD VALIDATOR*/); //Create a fragment to display the challenge
        scrollLayout.setVisibility(View.INVISIBLE);
        fragLayout.setVisibility(View.VISIBLE); //Make sure layouts are the right way

        //Could move to Form_Change if desired but since this is a container within an activity - not seen to be needed
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.bagFraglayout, fragment);
        fragmentTransaction.addToBackStack("bag_fragment"); //Display the fragment
        fragmentTransaction.commit();

    }

    /* FUNCTION INFORMATION
     * NAME - checkBoxClicked
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function that records which check boxes are clicked to store that data for submission
     */
    public String[] checkBoxClicked()
    {
        //Grab all the checkboxes and store the results
        CheckBox thirsty = findViewById(R.id.dehyOptSeven);
        CheckBox headache = findViewById(R.id.dehyOptNine);
        CheckBox stomach_cramps = findViewById(R.id.dehyOptFour);
        CheckBox light_headed = findViewById(R.id.dehyOptEight);
        CheckBox muscle_cramps = findViewById(R.id.dehyOptFive);
        CheckBox fatigue = findViewById(R.id.dehyOptSix);
        CheckBox dry_mouth = findViewById(R.id.dehyOptOne);
        CheckBox confusion = findViewById(R.id.dehyOptTwo);
        CheckBox tiredness = findViewById(R.id.dehyOptThree);

        //SYMPTOMS RESULTS NEED TO BE ADDED INTO AN ARRAY "RESULT"
        ArrayList<String> result = new ArrayList<String>();
        if(thirsty.isChecked()){
            result.add("Thirsty");
        }
        if(headache.isChecked()){
            result.add("Headache");
        }
        if(light_headed.isChecked()){
            result.add("Light Headed");
        }
        if(stomach_cramps.isChecked()){
            result.add("Stomach Cramps");
        }
        if(muscle_cramps.isChecked()){
            result.add("Muscle Cramps");
        }
        if(fatigue.isChecked()){
            result.add("Fatigue");
        }
        if(dry_mouth.isChecked()){
            result.add("Dry Mouth");
        }
        if(confusion.isChecked()){
            result.add("Confusion");
        }
        if(tiredness.isChecked()){
            result.add("Tiredness");
        }

        return result.toArray(new String[result.size()]);

    }

    /* FUNCTION INFORMATION
     * NAME - radioButtClicked
     * INPUTS - view (RadioButton that was clicked)
     * OUTPUTS - none
     * PURPOSE - This is the function that records which radiobutton is clicked to store that data for submission
     */
    public void radioButtClicked (View view){
        boolean checked = ((RadioButton) view).isChecked(); //Find out if the button was checked

        // Check which radio button was clicked
        switch (view.getId())
        {
            case R.id.urineOptOne:
                if (checked)
                {
                    uColourVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.urineOptTwo:
                if (checked)
                {
                    uColourVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.urineOptThree:
                if (checked)
                {
                    uColourVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.wellBOptOne:
                if (checked)
                {
                    wellBVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.wellBOptTwo:
                if (checked)
                {
                    wellBVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.wellBOptThree:
                if (checked)
                {
                    wellBVal = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            default:
                break;

        }
    }

    public void submitForm(View view)
    {
        boolean created = false;
        EditText urineInput = (EditText) findViewById(R.id.urine_input);

        //Get all the values that the user may have set
        String[] dehySympt = checkBoxClicked();
        String urineAmount = (urineInput.getText()).toString();

        //Do a rolling check for empty for values set

        if(bagList.size() == 0) //MAY WANT TO REMOVE IF NOT NECESSARY TO EMPTY A BAG PER INPUT
        {
            TextView bagText = findViewById(R.id.bagTitle);

            bagText.requestFocus();
            bagText.setError("Please provide a bag that has been emptied");
        }
        else
        {

            if(urineAmount.equals(""))
            {
                urineInput.requestFocus();
                urineInput.setError("Please provide an average urine output");
            }
            else
            {

                if(uColourVal == null)
                {
                    TextView uColText = findViewById(R.id.urineColourText);

                    uColText.requestFocus();
                    uColText.setError("Please provide an average urine colour");
                }
                else
                {
                    if(wellBVal == null)
                    {
                        TextView wellBText = findViewById(R.id.wellBText);

                        wellBText.requestFocus(); //TODO POSSIBLE FIX TO REMOVE ERROR TEXT
                        wellBText.setError("Please provide an average welllbeing value");
                    }
                    else
                    {
                            created = true;
                    }
                }
            }
        }

        if(created == true)
        {
            //TODO ADD VALIDATION HERE BEFORE ADDING ANYTHING TO STOMAFORM

            //TODO DELETE WHEN UNIT TESTED
            // Creating alert Dialog with one Button
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicalInput.this);

            // Setting Dialog Title
            alertDialog.setTitle("Submission results");

            // Setting Dialog Message
            alertDialog.setMessage("Result are:" + urineAmount + uColourVal + wellBVal + getCurrentDate() + dehySympt.length + bagList.size());

            // Setting the finished button
            alertDialog.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

            /*stomaForm.setUrine(Integer.parseInt(urineAmount), uColourVal);
            stomaForm.setDehydration(dehySympt);
            stomaForm.setTime(getCurrentDate());
            stomaForm.setWellbeing(wellBVal);
            for(int ii = 0; ii < bagList.size(); ii++)
            {
                stomaForm.addBag(bagList.get(ii));
            }*/

            //Call stomastatecalculator and write to file
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
        fragLayout.setVisibility(View.INVISIBLE);
        scrollLayout.setVisibility(View.VISIBLE);
    }

    //This nested class is used for fragments. It is inside since it is used inside this activity
    //This class is called when needing to inflate the page for viewing
    //This is tied to a button which when selected opens the fragment


     /* FUNCTION INFORMATION
     * NAME - deleteRecyclerItem
     * INPUTS - pos (position of the element to be deleted)
     * OUTPUTS - none
     * PURPOSE - This is the function that facilitates removal of a member from the list
     * NOTE - This is an override of the FineAdapter Interface to allow connection between activity and list
     */
    @Override
    public void deleteRecyclerItem(final int pos)
    {
        // Creating alert Dialog with two buttons
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MedicalInput.this);

        // Setting Dialog Title
        alertDialog.setTitle("Delete entry");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("I'm sure",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bAdapter.removeAt(pos);
                    }
                }); //Call the adapter to delete at that position
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

     /* FUNCTION INFORMATION
     * NAME - getCurrentDate
     * INPUTS - none
     * OUTPUTS - date (A string representing today's date
     * PURPOSE - This is the function that returns a string detailing today's date for use in file recording
     */

    private String getCurrentDate()
    {
        String date =  "";
        Calendar rightNow = Calendar.getInstance();

        date = date + rightNow.get(Calendar.DAY_OF_MONTH) + "_" + (rightNow.get(Calendar.MONTH) + 1) + "_" + rightNow.get(Calendar.YEAR);
        //Month is 0-indexed so we add one to display correctly to the user

        return date;
    }


}
