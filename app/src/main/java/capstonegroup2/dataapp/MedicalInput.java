package capstonegroup2.dataapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import Factory.Factory;
import Medical_Data_Input.StomaForm;
import Observers.State_Observer;

public class MedicalInput extends Activity{


    private Factory factory;
    private StomaForm stomaForm;
    private State_Observer state_observer;

    //Buttons which indicate the colour of Urine input
    private Button light_butt;
    private Button medium_butt;
    private Button dark_butt;
    private int urine_value;

    //Button for creating a new bag input
    private Button new_bag;

    //Check Boxes for the user's symptoms of dehydration
    private CheckBox thirsty, headache, light_headed, stomach_cramps, muscle_cramps, fatigue, dry_mouth, confusion, tiredness;

    //Contains all the check boxes
    private LinearLayout check_group;

    //Wellbeing
    private RadioButton good_butt, okay_butt, bad_butt;

    //NEW BAG BUTTON WHICH NAVIGATES TO THE BAG FRAGMENT
    private Button bagButton;

    //SUBMIT BUTTONS
    private Button main_submit;
    private Button bag_submit;

    //NUMBER OF TIMES URINATED ON AVERAGE
    private EditText urine_number;

    private RadioButton watery_butt;
    private RadioButton thick_butt;
    private RadioButton toothpaste_butt;

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

        //Urine Input
        light_butt = findViewById(R.id.Light);
        medium_butt = findViewById(R.id.Medium);
        dark_butt = findViewById(R.id.Dark);

        //Button pressed when entering a new bag
        bagButton = findViewById(R.id.bag_fragment);

        //Contains all symptoms
        check_group = findViewById(R.id.check_group);

        //Symptoms of dehydration
        thirsty = findViewById(R.id.Thirsty);
        headache = findViewById(R.id.Headache);
        light_headed = findViewById(R.id.Light_headed);
        stomach_cramps = findViewById(R.id.stomach_cramps);
        muscle_cramps = findViewById(R.id.Muscle_cramps);
        fatigue = findViewById(R.id.Fatigue);
        dry_mouth = findViewById(R.id.dry_mouth);
        confusion = findViewById(R.id.Confusion);
        tiredness = findViewById(R.id.Tiredness);

        //Well being weightings
        good_butt = findViewById(R.id.Good);
        okay_butt= findViewById(R.id.Okay);
        bad_butt = findViewById(R.id.Bad);

        //SUBMIT BUTTON FOR MAIN MEDICAL INPUT PAGE
        main_submit= findViewById(R.id.submit_main);
        bag_submit = findViewById(R.id.submit_bag);

        //URINE INPUT BY USER
        urine_number = findViewById(R.id.urine_input);

        watery_butt = findViewById(R.id.watery);
        thick_butt = findViewById(R.id.thick_liquid);
        toothpaste_butt = findViewById(R.id.toothpaste);


        /* TODO REWORK SO RECYLERVIEW WORKS IN THIS ACTIVITY

            //Set up recycler view
        fAdapter = new FineAdapter(fineList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fAdapter);

         */


    }

    //ALL CHECKED VALUES ARE ADDED TO THE VALUES ARRAY AND SENT TO BE SAVED
    //IF A VALUE IS NOT CHECKED IT WILL NOT BE ADDED
    public void checkBoxes(View view)
    {
        Button submit = findViewById(R.id.submit_main);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //SYMPTOMS RESULTS NEED TO BE ADDED INTO AN ARRAY "RESULT"
                String result = "";
                if(thirsty.isChecked()){
                    result += "Thirsty";
                }
                if(headache.isChecked()){
                    result += "Headache";
                }
                if(light_headed.isChecked()){
                    result += "Light Headed";
                }
                if(stomach_cramps.isChecked()){
                    result += "Stomach Cramps";
                }
                if(muscle_cramps.isChecked()){
                    result += "Muscle Cramps";
                }
                if(fatigue.isChecked()){
                    result += "Fatigue";
                }
                if(dry_mouth.isChecked()){
                    result += "Dry Mouth";
                }
                if(confusion.isChecked()){
                    result += "Confusion";
                }
                if(tiredness.isChecked()){
                    result += "Tiredness";
                }

                //Call fillForm for adding colour of urine
                //Select one of the buttons to fill urine colour
                //Light button can be set to 1, 2, 3
                /*light_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        stomaForm.addUrine(urine_value = 1);
                    }
                });

                //User selects the medium button
                medium_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        stomaForm.addUrine(urine_value = 2);
                    }
                });

                //User selects the dark button
                dark_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        stomaForm.addUrine(urine_value = 3);
                    }
                });

                //This loads the bag page where the user can enter their bag details
                //the loadBagFragment method is called when clicking on the bag button
                //In the loadBagFragment method, the fragment xml is called and overwrites the current page
                bagButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        loadBagFragment(new BagFragment());
                    }
                });

                //TIMES URINATED ON AVERAGE ENTERED BY THE USER
                String times_urinated = urine_number.getText().toString();
                stomaForm.addTimesUrinated(times_urinated);*/

                //WELLBEING RADIO BUTTONS
                good_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        String good_value = good_butt.getText().toString();
                        stomaForm.setWellbeing(good_value);
                    }
                });

                //WELLBEING RADIO BUTTONS
                okay_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        String okay_value = okay_butt.getText().toString();
                        stomaForm.setWellbeing(okay_value);
                    }
                });

                //WELLBEING RADIO BUTTONS
                bad_butt.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v){
                        String bad_value = bad_butt.getText().toString();
                        stomaForm.setWellbeing(bad_value);
                    }
                });

            }
        });


    }

    //This nested class is used for fragments. It is inside since it is used inside this activity
    //This class is called when needing to inflate the page for viewing
    //This is tied to a button which when selected opens the fragment
    public static class BagFragment extends Fragment {

        //VOLUME INSIDE OF BAG
        private EditText bag_amount;
        private StomaForm stomaForm;
        //BAG FRAGMENT: CONSISTENCY
        private Button submit_bag;




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.bag_fragment, container, false);

            //TODO SET THE INITIAL TIME AND DATE OF THE TWO SPINNERS TO CURRENT TIME
            //USE https://www.tutorialspoint.com/android/android_timepicker_control.htm and https://developer.android.com/guide/topics/ui/controls/pickers

        }

    }

    /* TODO REWITE FOR HOLDING A BAG ENTRY TO DISPLAY BACK TO USER
    private class FineAdapter extends RecyclerView.Adapter<FineAdapter.MyViewHolder> {


        private List<Fine> finesList;
        private ItemDeleteInterface itemDeleteInterface;

        public FineAdapter(List<Fine> finesList, ItemDeleteInterface mainActivity)
        {

            this.finesList = finesList;
            this.itemDeleteInterface = mainActivity; //Attach interface implementation
        }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView name, amount;
            public Button fineButt;
            public ImageButton deleteButt;
            public RelativeLayout fineLayout;

            public MyViewHolder(View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.fineName); //Find all the layout objects in the fine row
                amount = (TextView) view.findViewById(R.id.amount);
                fineButt = (Button) view.findViewById(R.id.fine_button);
                deleteButt = (ImageButton) view.findViewById(R.id.delete_entry_button);
                fineLayout = (RelativeLayout) view.findViewById(R.id.fineRow);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fine_list_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final int pos = position;
            Fine fine = finesList.get(pos); //Get the fine object and assign all the values to the layout objects
            holder.name.setText(fine.getName());
            if(fine.getAmount().equals("DOUBLE MAX"))
            {
                holder.amount.setText(fine.getAmount());

                holder.fineLayout.setClickable(false);

                holder.fineButt.setOnClickListener(null);
                holder.fineButt.setClickable(false); //Disable fine button as hit the cap
            }
            else
            {
                holder.amount.setText(fine.getAmount());
                holder.fineButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fine finee = finesList.get(pos); //Click listener for adding a fine increment
                        finee.fine();
                        finesList.set(pos, finee);
                        notifyItemChanged(pos);
                    }
                });
            }
            holder.deleteButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemDeleteInterface.deleteRecyclerItem(pos); //Set up listener for deleting the item
                }
            });

        }

        @Override
        public int getItemCount() {
            return finesList.size();
        }

        /* FUNCTION INFORMATION
         * NAME - removeAt
         * INPUTS - position (position of the element to be deleted)
         * OUTPUTS - none
         * PURPOSE - This is the function that facilitates removal of a member from the list
         *
        public void removeAt(int position) {
            finesList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, finesList.size()); //Need to tell adapter to reorganise list order (in case element had elements after it)
        }

        /* AUTHOR INFORMATION
         * CREATOR - Jeremy Dunnet ??/??/2018 (Project went on hiatus early 2018 so no concrete date on when I first designed each class)
         * LAST MODIFIED BY - Jeremy Dunnet 30/01/2019 (when comment sweep was done)
         */

        /* CLASS/FILE DESCRIPTION
         * This is the recyclerview interface for the ability to remove an item from the adapter and list
         */

        /* VERSION HISTORY
         * 30/01/2019 - Created comment log block and swept through and updated comments/references
         */

        /* REFERENCES
         * RecyclerView Item Removal/Addition Interface learned from https://stackoverflow.com/questions/26076965/android-recyclerview-addition-removal-of-items
         * Developer tutorials from https://developer.android.com/
         *
        public interface ItemDeleteInterface
        {
            void deleteRecyclerItem(int pos);
        }

    }*/


    /* TODO REWRITE DELETERECYLERITEM TO MAKE SURE WORKS WITH REMOVING A BAG
       TODO REWRITE GETCURRENTDATE FOR USE IN RECORDING ENTRY TIME
       /* FUNCTION INFORMATION
     * NAME - deleteRecyclerItem
     * INPUTS - pos (position of the element to be deleted)
     * OUTPUTS - none
     * PURPOSE - This is the function that facilitates removal of a member from the list
     * NOTE - This is an override of the FineAdapter Interface to allow connection between activity and list
     *
    @Override
    public void deleteRecyclerItem(final int pos)
    {
        // Creating alert Dialog with two buttons
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Delete entry");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("I'm sure",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fAdapter.removeAt(pos);
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

    private String getCurrentDate()
    {
        String date =  "";
        Calendar rightNow = Calendar.getInstance();

        date = date + rightNow.get(Calendar.DAY_OF_MONTH) + "_" + (rightNow.get(Calendar.MONTH) + 1) + "_" + rightNow.get(Calendar.YEAR);
        //Month is 0-indexed so we add one to display correctly to the user

        return date;
    }
     */

        private void loadBagFragment(Fragment fragment) {
        // create a FragmentManager
            FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
            fragmentTransaction.replace(R.id.bag_fragment, fragment);
            fragmentTransaction.commit(); // save the changes

            Button submit_bag = findViewById(R.id.submit_bag);
            submit_bag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //SYMPTOMS RESULTS NEED TO BE ADDED INTO AN ARRAY "RESULT"
                    String consistency = "";
                    if (watery_butt.isChecked()) {
                        consistency = "Watery";
                    }
                    if (thick_butt.isChecked()) {
                        consistency = "Thick";
                    }
                    if (toothpaste_butt.isChecked()) {
                        consistency = "Toothpaste";
                    }
                }
            });
        }

}
