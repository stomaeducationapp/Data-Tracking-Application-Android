package capstonegroup2.dataapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

import MedicalReview.DailyReview;

import MedicalReview.ReviewHandler.TYPE;

public class DailyReviewGraph extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    DailyReview today;
    DailyReview yesterday;
    RadioGroup dayGroup;
    String day;

    //MAYBE LINK DATASETS HERE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_review_graph);

        day = "today";

        Intent i = getIntent();

        //Gets the data sets to be used in making the graphs
        today = (DailyReview) i.getParcelableExtra("today");
        yesterday = (DailyReview) i.getParcelableExtra("yesterday");

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
                }
                else if (R.id.yesterdayButton == checkID) {
                    day = "yesterday";
                }
            }
        });
    }

    //create and display the chart
    public void displayGraph(DailyReview current, String typeOfChart) {
        //GraphicalView view = (GraphicalView) (findViewById(R.id.chartView));
        LinearLayout view = (LinearLayout)findViewById(R.id.chartView);

        switch (typeOfChart) {
            case "State Graph":
                view.addView(current.displayStateGraph(this), 0);
                break;
            case "State Pie Chart":
                view.addView(current.displayStateChart(this), 0);
                break;
            case "Volume Graph":
                view.addView(current.displayVolumeGraph(this), 0);
                break;
            case "Output Graph":
                view.addView(current.displayBagGraph(this), 0);
                break;
            case "Wellbeing Pie Chart":
                view.addView(current.displayWellbeingChart(this), 0);
                break;
            default:
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String selected = adapterView.getItemAtPosition(position).toString();
        if (day != null) {
            if (day.equals("today")) {
                displayGraph(today, selected);
            } else if (day.equals("yesterday")) {
                displayGraph(yesterday, selected);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //auto generated inherited method
    }
}
