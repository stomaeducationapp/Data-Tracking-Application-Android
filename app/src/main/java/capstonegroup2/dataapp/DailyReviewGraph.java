package capstonegroup2.dataapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import MedicalReview.DailyReview;

/**
 * Class: DailyReviewGraph. This is the activity that displays the graphs and lets the user interact
 * Extends: AppCompatActivity. This provides functionality of android activities.
 * Implements: OnItemSelectListener. This is for the spinner event trigger.
 * @author Ethan
 */
public class DailyReviewGraph extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private DailyReview today;
    private DailyReview yesterday;
    private RadioGroup dayGroup;
    private String day;

    /**
     * onCreate handles what happens when this activity is first created. In this case, it retrieves
     * the DailyReview objects from the intent and initialises the listeners for the spinner and
     * radio buttons.
     * @param savedInstanceState the state if there is a saved instance of this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_review_graph);

        //default to display the today data
        day = "today";

        Intent i = getIntent();

        //Gets the data sets to be used in making the graphs
        today = i.getParcelableExtra("today");
        yesterday = i.getParcelableExtra("yesterday");

        spinner = findViewById(R.id.graphSpinner);

        spinner.setOnItemSelectedListener(this);

        //create set of spinner elements
        List<String> options = new ArrayList<>();
        options.add("State Graph");
        options.add("State Pie Chart");
        options.add("Volume Graph");
        options.add("Output Graph");
        options.add("Wellbeing Pie Chart");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        dayGroup = (RadioGroup)findViewById(R.id.dayGroup);
        dayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkID) {
                //set the day
                if (R.id.todayButton == checkID) {
                    day = "today";
                    displayHandler(spinner.getSelectedItem().toString());
                }
                else if (R.id.yesterdayButton == checkID) {
                    day = "yesterday";
                    displayHandler(spinner.getSelectedItem().toString());
                }
            }
        });
    }

    //create and display the chart

    /**
     * displayGraph makes the call to the relevant display method of the DailyReview to get the
     * GraphicalView representation. It then adds this view to the GUIs to be displayed.
     * @param current the DailyReview object to be used - today or yesterday
     * @param typeOfChart which graph to display
     */
    public void displayGraph(DailyReview current, String typeOfChart) {
        //GraphicalView view = (GraphicalView) (findViewById(R.id.chartView));
        LinearLayout view = (LinearLayout)findViewById(R.id.chartView);

        switch (typeOfChart) {
            case "State Graph":
                //view.addView(current.displayStateGraph(this), 0);
                break;
            case "State Pie Chart":
                //view.addView(current.displayStateChart(this), 0);
                break;
            case "Volume Graph":
                //view.addView(current.displayVolumeGraph(this), 0);
                break;
            case "Output Graph":
                //view.addView(current.displayBagGraph(this), 0);
                break;
            case "Wellbeing Pie Chart":
                //view.addView(current.displayWellbeingChart(this), 0);
                break;
            default:
        }
    }

    /**
     * The action listener for the graph name spinner. This determines which graph to display.
     * @param adapterView the spinner itself.
     * @param view the parent view where the spinner is located.
     * @param position which number in the spinner is selected.
     * @param id the id of the selected entry.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String selected = adapterView.getItemAtPosition(position).toString();
        displayHandler(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //auto generated inherited method
    }

    /**
     * displayHandler is an intermediary between the event handlers and the graph display. This
     * dictates which DailyReview to use - today or yesterday.
     * @param graph the flag to determine which days graph to use.
     */
    public void displayHandler(String graph) {
        if (day != null) {
            if (day.equals("today")) {
                displayGraph(today, graph);
            } else if (day.equals("yesterday")) {
                displayGraph(yesterday, graph);
            }
        }
    }
}
